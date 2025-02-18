@file:Suppress("MatchingDeclarationName")

package eu.vendeli.tgbot.api.botactions

import eu.vendeli.tgbot.interfaces.ActionState
import eu.vendeli.tgbot.interfaces.SimpleAction
import eu.vendeli.tgbot.interfaces.TgAction
import eu.vendeli.tgbot.types.internal.TgMethod
import eu.vendeli.tgbot.utils.getReturnType

/**
 * Use this method to close the bot instance before moving it from one local server to another.
 * You need to delete the webhook before calling this method to ensure
 * that the bot isn't launched again after server restart.
 * The method will return error 429 in the first 10 minutes after the bot is launched. Returns True on success.
 *
 */
class CloseAction : SimpleAction<Boolean>, ActionState() {
    override val TgAction<Boolean>.method: TgMethod
        get() = TgMethod("close")
    override val TgAction<Boolean>.returnType: Class<Boolean>
        get() = getReturnType()
}

/**
 * Use this method to close the bot instance before moving it from one local server to another.
 * You need to delete the webhook before calling this method to ensure
 * that the bot isn't launched again after server restart.
 * The method will return error 429 in the first 10 minutes after the bot is launched. Returns True on success.
 *
 */
fun close() = CloseAction()
