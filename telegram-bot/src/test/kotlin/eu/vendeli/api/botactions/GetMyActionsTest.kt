package eu.vendeli.api.botactions

import BotTestContext
import eu.vendeli.tgbot.api.botactions.getMyCommands
import eu.vendeli.tgbot.api.botactions.getMyDefaultAdministratorRights
import eu.vendeli.tgbot.api.botactions.getMyDescription
import eu.vendeli.tgbot.api.botactions.getMyName
import eu.vendeli.tgbot.api.botactions.getMyShortDescription
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.string.shouldBeEmpty
import io.kotest.matchers.string.shouldStartWith

class GetMyActionsTest : BotTestContext() {
    @Test
    suspend fun `get my default administrator rights method testing`() {
        val result = getMyDefaultAdministratorRights().sendAsync(bot).await().shouldSuccess()

        result.shouldNotBeNull()
    }

    @Test
    suspend fun `get description method testing`() {
        val result = getMyDescription().sendAsync(bot).await().shouldSuccess()

        result.shouldNotBeNull()
        result.description.shouldBeEmpty()
    }

    @Test
    suspend fun `get short description method testing`() {
        val result = getMyShortDescription().sendAsync(bot).await().shouldSuccess()

        result.shouldNotBeNull()
        result.shortDescription.shouldBeEmpty()
    }

    @Test
    suspend fun `get my commands method testing`() {
        val result = getMyCommands().sendAsync(bot).await().shouldSuccess()

        result.shouldNotBeNull()
        result.shouldBeEmpty()
    }

    @Test
    suspend fun `get my name method testing`() {
        val result = getMyName().sendAsync(bot).await().shouldSuccess()

        result.shouldNotBeNull()
        result.name shouldStartWith "testbot"
    }
}
