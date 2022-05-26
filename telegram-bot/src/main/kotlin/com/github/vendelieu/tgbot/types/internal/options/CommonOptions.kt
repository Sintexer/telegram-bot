package com.github.vendelieu.tgbot.types.internal.options

class CommonOptions(
    override var disableNotification: Boolean? = null,
    override var replyToMessageId: Long? = null,
    override var allowSendingWithoutReply: Boolean? = null,
    override var protectContent: Boolean? = null,
) : IOptionsCommon, OptionsInterface<CommonOptions>
