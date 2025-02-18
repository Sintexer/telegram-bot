@file:Suppress("MatchingDeclarationName")

package eu.vendeli.tgbot.api.stickerset

import eu.vendeli.tgbot.interfaces.ActionState
import eu.vendeli.tgbot.interfaces.SimpleAction
import eu.vendeli.tgbot.interfaces.TgAction
import eu.vendeli.tgbot.types.internal.TgMethod
import eu.vendeli.tgbot.types.media.StickerSet
import eu.vendeli.tgbot.utils.getReturnType

class GetStickerSetAction(name: String) : SimpleAction<StickerSet>, ActionState() {
    override val TgAction<StickerSet>.method: TgMethod
        get() = TgMethod("getStickerSet")
    override val TgAction<StickerSet>.returnType: Class<StickerSet>
        get() = getReturnType()

    init {
        parameters["name"] = name
    }
}

fun getStickerSet(name: String) = GetStickerSetAction(name)
