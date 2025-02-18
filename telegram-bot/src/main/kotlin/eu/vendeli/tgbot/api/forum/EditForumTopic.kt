@file:Suppress("MatchingDeclarationName")

package eu.vendeli.tgbot.api.forum

import eu.vendeli.tgbot.interfaces.Action
import eu.vendeli.tgbot.interfaces.ActionState
import eu.vendeli.tgbot.interfaces.TgAction
import eu.vendeli.tgbot.types.internal.TgMethod
import eu.vendeli.tgbot.utils.getReturnType

/**
 * Use this method to edit name and icon of a topic in a forum supergroup chat.
 * The bot must be an administrator in the chat for this to work and must have canManageTopics administrator rights,
 * unless it is the creator of the topic. Returns True on success.
 */
class EditForumTopicAction(
    messageThreadId: Int,
    name: String? = null,
    iconCustomEmojiId: String? = null,
) : Action<Boolean>, ActionState() {
    override val TgAction<Boolean>.method: TgMethod
        get() = TgMethod("editForumTopic")
    override val TgAction<Boolean>.returnType: Class<Boolean>
        get() = getReturnType()

    init {
        parameters["message_thread_id"] = messageThreadId
        if (name != null) parameters["name"] = name
        if (iconCustomEmojiId != null) parameters["icon_custom_emoji_id"] = iconCustomEmojiId
    }
}

/**
 * Use this method to edit name and icon of a topic in a forum supergroup chat.
 * The bot must be an administrator in the chat for this to work and must have canManageTopics administrator rights,
 * unless it is the creator of the topic. Returns True on success.
 */
fun editForumTopic(messageThreadId: Int, name: String? = null, iconCustomEmojiId: String? = null) =
    EditForumTopicAction(messageThreadId, name, iconCustomEmojiId)
