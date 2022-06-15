package com.github.vendelieu.tgbot.api.stickerset

import com.github.vendelieu.tgbot.interfaces.MediaAction
import com.github.vendelieu.tgbot.types.MaskPosition
import com.github.vendelieu.tgbot.types.internal.StickerMediaType
import com.github.vendelieu.tgbot.types.internal.TgMethod
import io.ktor.http.*

@Suppress("UNUSED_PARAMETER")
class CreateNewStickerSetAction : MediaAction<Boolean> {
    override val method: TgMethod = TgMethod("createNewStickerSet")

    constructor(
        name: String,
        title: String,
        emojis: String,
        sticker: ByteArray,
        containsMasks: Boolean? = null,
        maskPosition: MaskPosition? = null,
        type: StickerMediaType.Png = StickerMediaType.Png,
    ) {
        parameters["title"] = title
        parameters["name"] = name
        parameters["emojis"] = emojis
        if (containsMasks != null) parameters["contains_masks"] = containsMasks
        if (maskPosition != null) parameters["mask_position"] = maskPosition

        setDataField("png_sticker")
        setDefaultType(ContentType.Image.PNG)
        setMedia(sticker)
    }

    constructor(
        name: String,
        title: String,
        emojis: String,
        sticker: ByteArray,
        containsMasks: Boolean? = null,
        maskPosition: MaskPosition? = null,
        type: StickerMediaType.Tgs = StickerMediaType.Tgs,
    ) {
        parameters["title"] = title
        parameters["name"] = name
        parameters["emojis"] = emojis
        if (containsMasks != null) parameters["contains_masks"] = containsMasks
        if (maskPosition != null) parameters["mask_position"] = maskPosition

        setDataField("tgs_sticker")
        setDefaultType(ContentType.MultiPart.FormData)
        setMedia(sticker)
    }

    constructor(
        name: String,
        title: String,
        emojis: String,
        sticker: ByteArray,
        containsMasks: Boolean? = null,
        maskPosition: MaskPosition? = null,
        type: StickerMediaType.Webm = StickerMediaType.Webm,
    ) {
        parameters["title"] = title
        parameters["name"] = name
        parameters["emojis"] = emojis
        if (containsMasks != null) parameters["contains_masks"] = containsMasks
        if (maskPosition != null) parameters["mask_position"] = maskPosition

        setDataField("webm_sticker")
        setDefaultType(ContentType.MultiPart.FormData)
        setMedia(sticker)
    }

    override val parameters: MutableMap<String, Any?> = mutableMapOf()
}

fun createNewStickerSet(
    name: String,
    title: String,
    emojis: String,
    sticker: ByteArray,
    type: StickerMediaType.Png,
    containsMasks: Boolean? = null,
    maskPosition: MaskPosition? = null,
) = CreateNewStickerSetAction(name, title, emojis, sticker, containsMasks, maskPosition, type)

fun createNewStickerSet(
    name: String,
    title: String,
    emojis: String,
    sticker: ByteArray,
    type: StickerMediaType.Tgs,
    containsMasks: Boolean? = null,
    maskPosition: MaskPosition? = null,
) = CreateNewStickerSetAction(name, title, emojis, sticker, containsMasks, maskPosition, type)

fun createNewStickerSet(
    name: String,
    title: String,
    emojis: String,
    sticker: ByteArray,
    type: StickerMediaType.Webm,
    containsMasks: Boolean? = null,
    maskPosition: MaskPosition? = null,
) = CreateNewStickerSetAction(name, title, emojis, sticker, containsMasks, maskPosition, type)