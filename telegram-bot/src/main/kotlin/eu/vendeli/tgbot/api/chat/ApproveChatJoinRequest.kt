@file:Suppress("MatchingDeclarationName")

package eu.vendeli.tgbot.api.chat

import eu.vendeli.tgbot.interfaces.Action
import eu.vendeli.tgbot.interfaces.ActionState
import eu.vendeli.tgbot.interfaces.TgAction
import eu.vendeli.tgbot.types.User
import eu.vendeli.tgbot.types.internal.TgMethod
import eu.vendeli.tgbot.utils.getReturnType

class ApproveChatJoinRequestAction(userId: Long) : Action<Boolean>, ActionState() {
    override val TgAction<Boolean>.method: TgMethod
        get() = TgMethod("approveChatJoinRequest")
    override val TgAction<Boolean>.returnType: Class<Boolean>
        get() = getReturnType()

    init {
        parameters["user_id"] = userId
    }
}

fun approveChatJoinRequest(userId: Long) = ApproveChatJoinRequestAction(userId)
fun approveChatJoinRequest(user: User) = ApproveChatJoinRequestAction(user.id)
