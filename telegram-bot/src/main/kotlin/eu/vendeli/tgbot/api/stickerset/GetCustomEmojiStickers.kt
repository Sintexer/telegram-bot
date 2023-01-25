@file:Suppress("MatchingDeclarationName")

package eu.vendeli.tgbot.api.stickerset

import eu.vendeli.tgbot.interfaces.SimpleAction
import eu.vendeli.tgbot.interfaces.WrappedTypeOf
import eu.vendeli.tgbot.interfaces.getInnerType
import eu.vendeli.tgbot.types.Sticker
import eu.vendeli.tgbot.types.internal.TgMethod

/**
 * Use this method to get information about custom emoji stickers by their identifiers
 *
 * @param customEmojiIds List of custom emoji identifiers. At most 200 custom emoji identifiers can be specified.
 */
class GetCustomEmojiStickersAction(customEmojiIds: List<String>) :
    SimpleAction<List<Sticker>>,
    WrappedTypeOf<Sticker> {
    override val method: TgMethod = TgMethod("getCustomEmojiStickers")
    override val parameters: MutableMap<String, Any?> = mutableMapOf()

    init {
        parameters["custom_emoji_ids"] = customEmojiIds
    }

    override val wrappedDataType = getInnerType()
}

/**
 * Use this method to get information about custom emoji stickers by their identifiers
 *
 * @param customEmojiIds List of custom emoji identifiers. At most 200 custom emoji identifiers can be specified.
 */
fun getCustomEmojiStickers(customEmojiIds: List<String>) = GetCustomEmojiStickersAction(customEmojiIds)

/**
 * Use this method to get information about custom emoji stickers by their identifiers
 *
 * @param customEmojiId Custom emoji identifier. At most 200 custom emoji identifiers can be specified.
 */
@JvmName("getCustomEmojiStickersWithVararg")
fun getCustomEmojiStickers(vararg customEmojiId: String) = GetCustomEmojiStickersAction(customEmojiId.asList())
