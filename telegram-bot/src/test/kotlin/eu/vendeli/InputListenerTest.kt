package eu.vendeli

import BotTestContext
import eu.vendeli.fixtures.Conversation
import eu.vendeli.tgbot.annotations.internal.ExperimentalFeature
import eu.vendeli.tgbot.core.InputListenerMapImpl
import eu.vendeli.tgbot.utils.registerInputChain
import eu.vendeli.tgbot.utils.setChain
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe

class InputListenerTest : BotTestContext() {
    private val mapImpl = InputListenerMapImpl()

    @Test
    fun `sync crud test`() {
        mapImpl.set(1, "test")
        mapImpl.get(1) shouldBe "test"
        mapImpl.del(1)
        mapImpl.get(1).shouldBeNull()

        mapImpl.set(DUMB_USER) { "test2" }
        mapImpl[DUMB_USER] shouldBe "test2"

        mapImpl[DUMB_USER] = "test3"
        mapImpl[DUMB_USER] shouldBe "test3"
    }

    @Test
    suspend fun `async crud test`() {
        mapImpl.setAsync(1, "test").await().shouldBeTrue()
        mapImpl.getAsync(1).await() shouldBe "test"
        mapImpl.delAsync(1).await().shouldBeTrue()
        mapImpl.getAsync(1).await().shouldBeNull()

        mapImpl.setAsync(DUMB_USER) { "test2" }.await().shouldBeTrue()
        mapImpl.getAsync(DUMB_USER.id).await() shouldBe "test2"
    }

    @Test
    @OptIn(ExperimentalFeature::class)
    fun `chain registering test`() {
        bot.registerInputChain(Conversation::class)
        bot.update.actions?.inputs?.also {
            println(it)
        }
        bot.update.actions!!.inputs.keys shouldContainInOrder listOf(
            "eu.vendeli.fixtures.Conversation.Name",
            "eu.vendeli.fixtures.Conversation.Age",
        )
    }

    @Test
    @OptIn(ExperimentalFeature::class)
    fun `set chain test`() {
        bot.registerInputChain(Conversation::class)

        bot.inputListener.setChain(DUMB_USER, Conversation)
        bot.inputListener[DUMB_USER] shouldBe "eu.vendeli.fixtures.Conversation.Name"
    }
}
