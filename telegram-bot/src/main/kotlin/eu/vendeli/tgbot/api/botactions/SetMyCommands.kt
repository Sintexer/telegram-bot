@file:Suppress("MatchingDeclarationName")

package eu.vendeli.tgbot.api.botactions

import eu.vendeli.tgbot.interfaces.ActionState
import eu.vendeli.tgbot.interfaces.SimpleAction
import eu.vendeli.tgbot.interfaces.TgAction
import eu.vendeli.tgbot.types.bot.BotCommand
import eu.vendeli.tgbot.types.bot.BotCommandScope
import eu.vendeli.tgbot.types.internal.TgMethod
import eu.vendeli.tgbot.utils.builders.BotCommandsBuilder
import eu.vendeli.tgbot.utils.getReturnType

class SetMyCommandsAction(
    commands: List<BotCommand>,
    scope: BotCommandScope? = null,
    languageCode: String? = null,
) : SimpleAction<Boolean>, ActionState() {
    override val TgAction<Boolean>.method: TgMethod
        get() = TgMethod("setMyCommands")
    override val TgAction<Boolean>.returnType: Class<Boolean>
        get() = getReturnType()

    init {
        parameters["commands"] = commands
        if (scope != null) parameters["scope"] = scope
        if (languageCode != null) parameters["language_code"] = languageCode
    }
}

fun setMyCommands(languageCode: String? = null, scope: BotCommandScope? = null, vararg command: BotCommand) =
    SetMyCommandsAction(listOf(*command), scope, languageCode)

fun setMyCommands(languageCode: String? = null, scope: BotCommandScope? = null, block: BotCommandsBuilder.() -> Unit) =
    SetMyCommandsAction(BotCommandsBuilder().apply(block).commandsList, scope, languageCode)
