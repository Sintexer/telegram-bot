package com.github.vendelieu.tgbot.api.media

import com.github.vendelieu.tgbot.interfaces.MediaAction
import com.github.vendelieu.tgbot.interfaces.features.*
import com.github.vendelieu.tgbot.types.Message
import com.github.vendelieu.tgbot.types.internal.TgMethod
import com.github.vendelieu.tgbot.types.internal.options.PhotoOptions
import io.ktor.http.*

class SendPhotoAction :
    MediaAction<Message>,
    OptionAble,
    MarkupAble,
    CaptionAble,
    OptionsFeature<SendPhotoAction, PhotoOptions>,
    MarkupFeature<SendPhotoAction>,
    CaptionFeature<SendPhotoAction> {
    override val method: TgMethod = TgMethod("sendPhoto")

    init {
        setDataField("photo")
        setDefaultType(ContentType.Image.JPEG)
    }

    constructor(photoId: String) {
        setId(photoId)
    }

    constructor(photo: ByteArray) {
        setMedia(photo)
    }

    override var options = PhotoOptions()
    override val parameters: MutableMap<String, Any?> = mutableMapOf()
}

fun photo(block: () -> String) = SendPhotoAction(block())

fun photo(ba: ByteArray) = SendPhotoAction(ba)