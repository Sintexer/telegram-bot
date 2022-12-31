package eu.vendeli.tgbot.core

import eu.vendeli.tgbot.TelegramBot
import eu.vendeli.tgbot.TelegramBot.Companion.mapper
import eu.vendeli.tgbot.interfaces.BotInputListener
import eu.vendeli.tgbot.interfaces.ClassManager
import eu.vendeli.tgbot.interfaces.RateLimitMechanism
import eu.vendeli.tgbot.types.Update
import eu.vendeli.tgbot.types.internal.Actions
import eu.vendeli.tgbot.types.internal.Activity
import eu.vendeli.tgbot.types.internal.Invocation
import eu.vendeli.tgbot.types.internal.ProcessedUpdate
import eu.vendeli.tgbot.utils.*
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import kotlin.coroutines.coroutineContext

/**
 * A class that handles updates.
 *
 * @property actions The list of actions the handler will work with.
 * @property bot An instance of [TelegramBot]
 * @property classManager An instance of the class that will be used to call functions.
 * @property inputListener An instance of the class that stores the input waiting points.
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
class TelegramUpdateHandler internal constructor(
    private val actions: Actions? = null,
    internal val bot: TelegramBot,
    private val classManager: ClassManager,
    private val inputListener: BotInputListener,
    internal val rateLimiter: RateLimitMechanism
) {
    internal val logger = LoggerFactory.getLogger(javaClass)
    private lateinit var listener: InputListenerBlock

    @Volatile
    private var handlerActive: Boolean = false
    private val manualHandlingBehavior by lazy { ManualHandlingDsl(bot, inputListener) }

    /**
     * Function that starts the listening event.
     *
     * @param offset
     */
    private tailrec suspend fun runListener(offset: Int? = null): Int = with(bot.config.updatesListener) {
        logger.trace("Running listener with offset - $offset")
        if (!handlerActive) {
            coroutineContext.cancelChildren()
            return 0
        }
        var lastUpdateId: Int = offset ?: 0
        bot.pullUpdates(offset)?.forEach {
            CreateNewCoroutineContext(coroutineContext + dispatcher).launch {
                listener(this@TelegramUpdateHandler, it)
            }
            lastUpdateId = it.updateId + 1
        }
        delay(pullingDelay)
        return runListener(lastUpdateId)
    }

    /**
     * Function to define the actions that will be applied to updates when they are being processed.
     * When set, it starts an update processing cycle.
     *
     * @param block action that will be applied.
     */
    suspend fun setListener(block: InputListenerBlock) {
        if (handlerActive) stopListener()
        logger.trace("The listener is set.")
        listener = block
        handlerActive = true
        runListener()
    }

    /**
     * Stops listening of new updates.
     *
     */
    fun stopListener() {
        logger.trace("The listener is stopped.")
        handlerActive = false
    }

    /**
     * Function for mapping text with a specific command or input.
     *
     * @param text
     * @param command true to search in commands or false to search among inputs. Default - true.
     * @return [Activity] if actions was found or null.
     */
    private fun findAction(text: String, command: Boolean = true): Activity? {
        val message = text.parseQuery()
        val invocation = if (command) actions?.commands else {
            actions?.inputs
        }?.get(message.command)
        return if (invocation != null) Activity(
            id = message.command,
            invocation = invocation,
            parameters = message.params,
            rateLimits = invocation.rateLimits
        ) else null
    }

    /**
     * Updates parsing method
     *
     * @param update
     * @return [Update] or null
     */
    fun parseUpdate(update: String): Update? {
        logger.trace("Trying to parse update from string - $update")
        return mapper.runCatching {
            readValue(update, Update::class.java)
        }.onFailure {
            logger.debug("error during the update parsing process.", it)
        }.onSuccess { logger.trace("Successfully parsed update to $it") }.getOrNull()
    }

    /**
     * Updates parsing method
     *
     * @param updates
     * @return [Update] or null
     */
    fun parseUpdates(updates: String): List<Update>? {
        logger.trace("Trying to parse bunch of updates from string - $updates")
        return mapper.runCatching {
            readValue(updates, jacksonTypeRef<Response<List<Update>>>()).getOrNull()
        }.onFailure {
            logger.debug("error during the bunch updates parsing process.", it)
        }.onSuccess { logger.trace("Successfully parsed updates to $it") }.getOrNull()
    }

    /**
     * [Update] extension function that helps to handle the update (annotations mode)
     *
     */
    @JvmName("handleIt")
    suspend fun Update.handle() = handle(this)

    /**
     * [Update] extension function that helps to handle the update (manual mode)
     *
     */
    @JvmName("handleItManually")
    suspend fun Update.handle(block: ManualHandlingBlock) = handle(this, block)

    /**
     * Function used to call functions with certain parameters processed after receiving update.
     *
     * @param update
     * @param invocation
     * @param parameters
     * @return null on success or [Throwable].
     */
    private suspend fun invokeMethod(
        update: ProcessedUpdate,
        invocation: Invocation,
        parameters: Map<String, String>,
    ): Throwable? {
        var isSuspend = false
        logger.trace("Parsing arguments for Update#${update.fullUpdate.updateId}")
        val processedParameters = buildList {
            invocation.method.parameters.forEach { p ->
                if (p.type.name == "kotlin.coroutines.Continuation") {
                    isSuspend = true
                    return@forEach
                }
                val parameterName = invocation.namedParameters.getOrDefault(p.name, p.name)
                val typeName = p.parameterizedType.typeName
                if (parameters.keys.contains(parameterName)) when (p.parameterizedType.typeName) {
                    "java.lang.String" -> add(parameters[parameterName].toString())
                    "java.lang.Integer", "int" -> add(parameters[parameterName]?.toIntOrNull())
                    "java.lang.Long", "long" -> add(parameters[parameterName]?.toLongOrNull())
                    "java.lang.Short", "short" -> add(parameters[parameterName]?.toShortOrNull())
                    "java.lang.Float", "float" -> add(parameters[parameterName]?.toFloatOrNull())
                    "java.lang.Double", "double" -> add(parameters[parameterName]?.toDoubleOrNull())
                    else -> add(null)
                } else when {
                    typeName == "eu.vendeli.tgbot.types.User" -> add(update.user)
                    typeName == "eu.vendeli.tgbot.TelegramBot" -> add(bot)
                    typeName == "eu.vendeli.tgbot.types.internal.ProcessedUpdate" -> add(update)
                    bot.magicObjects.contains(p.type) -> add(bot.magicObjects[p.type]?.get(update, bot))
                    else -> add(null)
                }
            }
        }.toTypedArray()

        bot.config.context._chatData?.run {
            if (!update.user.isPresent()) return@run
            logger.trace("Handling BotContext for Update#${update.fullUpdate.updateId}")
            val prevClassName = getAsync(update.user.id, "PrevInvokedClass").await()?.toString()
            if (prevClassName != invocation.clazz.name) delPrevChatSectionAsync(update.user.id).await()

            setAsync(update.user.id, "PrevInvokedClass", invocation.clazz.name).await()
        }

        logger.trace("Invoking function for Update#${update.fullUpdate.updateId}")
        invocation.runCatching {
            if (isSuspend) method.invokeSuspend(classManager.getInstance(clazz), *processedParameters)
            else method.invoke(classManager.getInstance(clazz), *processedParameters)
        }.onFailure {
            logger.debug("Method {$invocation} invocation error at handling update: $update", it)
            return it
        }.onSuccess { logger.debug("Handled update#${update.fullUpdate.updateId} to method $invocation") }
        return null
    }

    /**
     * Handle the update.
     *
     * @param update
     * @return null on success or [Throwable].
     */
    suspend fun handle(update: Update): Throwable? = processUpdate(update).run {
        logger.trace("Handling update: $update")
        val telegramId = update.message?.from?.id
        if (checkIsLimited(bot.config.rateLimits, telegramId)) return@run null

        val commandAction = if (text != null) findAction(text.substringBefore('@')) else null
        val inputAction = if (commandAction == null) inputListener.getAsync(user.id).await()?.let {
            findAction(it, false)
        } else null
        logger.trace("Result of finding action - command: $commandAction, input: $inputAction")
        inputListener.delAsync(user.id).await()

        val action = commandAction ?: inputAction
        if (action != null && checkIsLimited(action.rateLimits, telegramId, action.id)) return@run null

        return when {
            commandAction != null -> invokeMethod(this, commandAction.invocation, commandAction.parameters)
            inputAction != null && update.message?.from?.isBot == false -> invokeMethod(
                this, inputAction.invocation, inputAction.parameters
            )

            actions?.unhandled != null -> invokeMethod(this, actions.unhandled, emptyMap())
            else -> {
                logger.info("update: $update not handled.")
                null
            }
        }
    }

    /**
     * Manual handling dsl
     *
     * @param update
     * @param block
     */
    suspend fun handle(update: Update, block: ManualHandlingBlock) {
        logger.trace("Manually handling update: $update")
        manualHandlingBehavior.apply {
            block()
            process(update)
        }
    }
}
