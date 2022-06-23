package eu.vendeli.tgbot.api.media

import eu.vendeli.tgbot.interfaces.MediaAction
import eu.vendeli.tgbot.interfaces.features.*
import eu.vendeli.tgbot.types.Message
import eu.vendeli.tgbot.types.internal.MediaContentType
import eu.vendeli.tgbot.types.internal.TgMethod
import eu.vendeli.tgbot.types.internal.options.VideoOptions

class SendVideoAction :
    MediaAction<Message>,
    OptionAble,
    MarkupAble,
    CaptionAble,
    OptionsFeature<SendVideoAction, VideoOptions>,
    MarkupFeature<SendVideoAction>,
    CaptionFeature<SendVideoAction> {
    override val method: TgMethod = TgMethod("sendVideo")

    init {
        setDataField("video")
        setDefaultType(MediaContentType.VideoMp4)
    }

    constructor(videoId: String) {
        setId(videoId)
    }

    constructor(video: ByteArray) {
        setMedia(video)
    }

    override var options = VideoOptions()
    override val parameters: MutableMap<String, Any?> = mutableMapOf()
}

fun video(block: () -> String) = SendVideoAction(block())

fun video(ba: ByteArray) = SendVideoAction(ba)
