package com.github.vendelieu.tgbot.interfaces

interface BotChatData {
    fun set(telegramId: Long, key: String, value: Any?): Boolean
    fun get(telegramId: Long, key: String): Any?
    fun del(telegramId: Long, key: String): Boolean
    fun delPrevChatSection(telegramId: Long): Boolean
}
