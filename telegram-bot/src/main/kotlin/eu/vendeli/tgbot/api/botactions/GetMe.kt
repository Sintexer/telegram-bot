@file:Suppress("MatchingDeclarationName")

package eu.vendeli.tgbot.api.botactions

import eu.vendeli.tgbot.interfaces.ActionState
import eu.vendeli.tgbot.interfaces.SimpleAction
import eu.vendeli.tgbot.interfaces.TgAction
import eu.vendeli.tgbot.types.User
import eu.vendeli.tgbot.types.internal.TgMethod
import eu.vendeli.tgbot.utils.getReturnType

/**
 * A simple method for testing your bot's authentication token.
 * Requires no parameters. Returns basic information about the bot in form of a [User](User) object.
 *
 */
class GetMeAction : SimpleAction<User>, ActionState() {
    override val TgAction<User>.method: TgMethod
        get() = TgMethod("getMe")
    override val TgAction<User>.returnType: Class<User>
        get() = getReturnType()
}

/**
 * A simple method for testing your bot's authentication token. Requires no parameters.
 * Returns basic information about the bot in form of a [User](User) object.
 *
 */
fun getMe() = GetMeAction()
