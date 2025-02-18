@file:Suppress("MatchingDeclarationName")

package eu.vendeli.tgbot.api

import eu.vendeli.tgbot.interfaces.Action
import eu.vendeli.tgbot.interfaces.ActionState
import eu.vendeli.tgbot.interfaces.InlineMode
import eu.vendeli.tgbot.interfaces.TgAction
import eu.vendeli.tgbot.interfaces.features.MarkupFeature
import eu.vendeli.tgbot.types.Message
import eu.vendeli.tgbot.types.internal.TgMethod
import eu.vendeli.tgbot.utils.getReturnType

class StopMessageLiveLocationAction() :
    Action<Message>,
    ActionState(),
    InlineMode<Message>,
    MarkupFeature<StopMessageLiveLocationAction> {
    override val TgAction<Message>.method: TgMethod
        get() = TgMethod("stopMessageLiveLocation")
    override val TgAction<Message>.returnType: Class<Message>
        get() = getReturnType()

    constructor(messageId: Long) : this() {
        parameters["message_id"] = messageId
    }
}

fun stopMessageLiveLocation(messageId: Long) = StopMessageLiveLocationAction(messageId)
fun stopMessageLiveLocation() = StopMessageLiveLocationAction()
