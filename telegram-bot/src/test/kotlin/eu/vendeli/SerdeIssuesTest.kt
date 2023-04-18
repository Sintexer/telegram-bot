package eu.vendeli

import BotTestContext
import eu.vendeli.tgbot.TelegramBot.Companion.mapper
import eu.vendeli.tgbot.types.keyboard.KeyboardButton
import eu.vendeli.tgbot.types.keyboard.ReplyKeyboardMarkup
import eu.vendeli.tgbot.types.media.Voice
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldContain

class SerdeIssuesTest : BotTestContext() {
    @Test
    fun `Voice type deserialization`() {
        val testData = """{"file_id": "test", "file_unique_id": "test1", "duration": 1, "mime_type": "audio/ogg"}"""
        val voice = mapper.runCatching { readValue(testData, Voice::class.java) }

        voice.isSuccess shouldBe true
        voice.getOrNull() shouldNotBe null

        voice.getOrNull()?.run {
            fileId shouldBe "test"
            fileUniqueId shouldBe "test1"
            duration shouldBe 1
            mimeType shouldNotBe null
            mimeType shouldBe "audio/ogg"
        }
    }

    @Test
    fun `reply markup parameter wrong serialization`() {
        val markup = ReplyKeyboardMarkup(listOf(listOf(KeyboardButton("test"))), inputFieldPlaceholder = "test")
        val serializedMarkup = mapper.writeValueAsString(markup)
        serializedMarkup shouldContain "input_field_placeholder"
    }
}