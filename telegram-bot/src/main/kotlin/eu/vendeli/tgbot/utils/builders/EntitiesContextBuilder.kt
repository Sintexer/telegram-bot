package eu.vendeli.tgbot.utils.builders

import eu.vendeli.tgbot.interfaces.IActionState
import eu.vendeli.tgbot.types.EntityType
import eu.vendeli.tgbot.types.EntityType.Bold
import eu.vendeli.tgbot.types.EntityType.BotCommand
import eu.vendeli.tgbot.types.EntityType.Cashtag
import eu.vendeli.tgbot.types.EntityType.Code
import eu.vendeli.tgbot.types.EntityType.CustomEmoji
import eu.vendeli.tgbot.types.EntityType.Email
import eu.vendeli.tgbot.types.EntityType.Hashtag
import eu.vendeli.tgbot.types.EntityType.Italic
import eu.vendeli.tgbot.types.EntityType.Mention
import eu.vendeli.tgbot.types.EntityType.PhoneNumber
import eu.vendeli.tgbot.types.EntityType.Pre
import eu.vendeli.tgbot.types.EntityType.Spoiler
import eu.vendeli.tgbot.types.EntityType.Strikethrough
import eu.vendeli.tgbot.types.EntityType.TextLink
import eu.vendeli.tgbot.types.EntityType.TextMention
import eu.vendeli.tgbot.types.EntityType.Underline
import eu.vendeli.tgbot.types.EntityType.Url
import eu.vendeli.tgbot.types.MessageEntity
import eu.vendeli.tgbot.types.User

@Suppress("TooManyFunctions")
interface EntitiesContextBuilder : IActionState {
    val EntitiesContextBuilder.entitiesField: String get() = "entities"

    @Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST", "unused")
    private inline fun addEntity(entity: MessageEntity) {
        parameters[entitiesField] = (
            (parameters[entitiesField] ?: mutableListOf<MessageEntity>()) as MutableList<MessageEntity>
        ).also { it.add(entity) }
    }

    operator fun String.minus(other: String): String = this + other

    // operators
    operator fun String.minus(other: Pair<EntityType, String>): String {
        addEntity(MessageEntity(other.first, length, other.second.length))
        return this + other.second
    }

    operator fun Pair<EntityType, String>.minus(other: String): String {
        addEntity(MessageEntity(first, 0, second.length))
        return second + other
    }

    operator fun <T> String.minus(other: Triple<EntityType, String, T?>): String {
        val entity = when (other.first) {
            Pre -> MessageEntity(
                Pre,
                length,
                other.second.length,
                language = other.third?.toString(),
            )

            TextLink -> MessageEntity(
                TextLink,
                length,
                other.second.length,
                url = other.third?.toString(),
            )

            CustomEmoji -> MessageEntity(
                CustomEmoji,
                length,
                other.second.length,
                customEmojiId = other.third?.toString(),
            )

            TextMention -> MessageEntity(
                TextMention,
                length,
                other.second.length,
                user = other.third as? User,
            )

            else -> throw IllegalArgumentException("An unexpected EntityType - ${other.first}.")
        }
        addEntity(entity)
        return this + other.second
    }

    operator fun <T> Triple<EntityType, String, T?>.minus(other: String): String {
        val entity = when (first) {
            Pre -> MessageEntity(Pre, 0, second.length, language = third?.toString())
            TextLink -> MessageEntity(TextLink, 0, second.length, url = third?.toString())
            CustomEmoji -> MessageEntity(CustomEmoji, 0, second.length, customEmojiId = third?.toString())
            TextMention -> MessageEntity(TextMention, 0, second.length, user = third as? User)
            else -> throw IllegalArgumentException("An unexpected EntityType - $first.")
        }
        addEntity(entity)
        return this.second + other
    }

    // functions

    fun EntitiesContextBuilder.mention(block: () -> String) = Mention to block()
    fun EntitiesContextBuilder.hashtag(block: () -> String) = Hashtag to block()
    fun EntitiesContextBuilder.cashtag(block: () -> String) = Cashtag to block()
    fun EntitiesContextBuilder.botCommand(block: () -> String) = BotCommand to block()
    fun EntitiesContextBuilder.url(block: () -> String) = Url to block()
    fun EntitiesContextBuilder.email(block: () -> String) = Email to block()
    fun EntitiesContextBuilder.phoneNumber(block: () -> String) = PhoneNumber to block()
    fun EntitiesContextBuilder.bold(block: () -> String) = Bold to block()
    fun EntitiesContextBuilder.italic(block: () -> String) = Italic to block()
    fun EntitiesContextBuilder.underline(block: () -> String) = Underline to block()
    fun EntitiesContextBuilder.strikethrough(block: () -> String) = Strikethrough to block()
    fun EntitiesContextBuilder.spoiler(block: () -> String) = Spoiler to block()
    fun EntitiesContextBuilder.code(block: () -> String) = Code to block()

    fun EntitiesContextBuilder.customEmoji(
        customEmojiId: String? = null,
        block: () -> String,
    ) = Triple(CustomEmoji, block(), customEmojiId)

    @Suppress("INAPPLICABLE_JVM_NAME")
    @JvmName("pre")
    fun EntitiesContextBuilder.pre(
        language: String? = null,
        block: () -> String,
    ) = Triple(Pre, block(), language)

    @Suppress("INAPPLICABLE_JVM_NAME")
    @JvmName("textLink")
    fun EntitiesContextBuilder.textLink(
        url: String? = null,
        block: () -> String,
    ) = Triple(TextLink, block(), url)

    @Suppress("INAPPLICABLE_JVM_NAME")
    @JvmName("textMention")
    fun EntitiesContextBuilder.textMention(
        user: User? = null,
        block: () -> String,
    ) = Triple(TextMention, block(), user)
}
