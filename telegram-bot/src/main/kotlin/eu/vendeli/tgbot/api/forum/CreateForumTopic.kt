@file:Suppress("MatchingDeclarationName")

package eu.vendeli.tgbot.api.forum

import eu.vendeli.tgbot.interfaces.SimpleAction
import eu.vendeli.tgbot.types.ForumTopic
import eu.vendeli.tgbot.types.internal.TgMethod

/**
 * Use this method to create a topic in a forum supergroup chat.
 * The bot must be an administrator in the chat for this to work and must have the canManageTopics administrator rights.
 * Returns information about the created topic as a [ForumTopic] object.
 *
 */
class CreateForumTopicAction(
    name: String,
    iconColor: Int? = null,
    iconCustomEmojiId: String? = null,
) : SimpleAction<ForumTopic> {
    override val method: TgMethod = TgMethod("createForumTopic")
    override val parameters: MutableMap<String, Any?> = mutableMapOf()

    init {
        parameters["name"] = name
        if (iconColor != null) parameters["icon_color"] = iconColor
        if (iconCustomEmojiId != null) parameters["icon_custom_emoji_id"] = iconCustomEmojiId
    }
}

/**
 * Use this method to create a topic in a forum supergroup chat.
 * The bot must be an administrator in the chat for this to work and must have the canManageTopics administrator rights.
 * Returns information about the created topic as a [ForumTopic] object.
 *
 */
fun createForumTopic(name: String, iconColor: Int? = null, iconCustomEmojiId: String? = null) =
    CreateForumTopicAction(name, iconColor, iconCustomEmojiId)