@file:Suppress("MatchingDeclarationName")

package eu.vendeli.tgbot.api.forum

import eu.vendeli.tgbot.interfaces.Action
import eu.vendeli.tgbot.interfaces.ActionState
import eu.vendeli.tgbot.interfaces.TgAction
import eu.vendeli.tgbot.types.internal.TgMethod
import eu.vendeli.tgbot.utils.getReturnType

/**
 * Use this method to reopen closed topic in a forum supergroup chat.
 * The bot must be an administrator in the chat for this to work and must have the canManageTopics administrator rights,
 * unless it is the creator of the topic. Returns True on success.
 */
class ReopenForumTopicAction(messageThreadId: Int) : Action<Boolean>, ActionState() {
    override val TgAction<Boolean>.method: TgMethod
        get() = TgMethod("reopenForumTopic")
    override val TgAction<Boolean>.returnType: Class<Boolean>
        get() = getReturnType()

    init {
        parameters["message_thread_id"] = messageThreadId
    }
}

/**
 * Use this method to reopen closed topic in a forum supergroup chat.
 * The bot must be an administrator in the chat for this to work and must have the canManageTopics administrator rights,
 * unless it is the creator of the topic. Returns True on success.
 */
fun reopenForumTopic(messageThreadId: Int) = ReopenForumTopicAction(messageThreadId)