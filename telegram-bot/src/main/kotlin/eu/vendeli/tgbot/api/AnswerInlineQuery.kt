@file:Suppress("MatchingDeclarationName")

package eu.vendeli.tgbot.api

import eu.vendeli.tgbot.interfaces.ActionState
import eu.vendeli.tgbot.interfaces.SimpleAction
import eu.vendeli.tgbot.interfaces.TgAction
import eu.vendeli.tgbot.interfaces.features.OptionsFeature
import eu.vendeli.tgbot.types.inline.InlineQueryResult
import eu.vendeli.tgbot.types.internal.TgMethod
import eu.vendeli.tgbot.types.internal.options.AnswerInlineQueryOptions
import eu.vendeli.tgbot.utils.getReturnType

class AnswerInlineQueryAction(inlineQueryId: String, results: List<InlineQueryResult>) :
    SimpleAction<Boolean>,
    ActionState(),
    OptionsFeature<AnswerInlineQueryAction, AnswerInlineQueryOptions> {
    override val TgAction<Boolean>.method: TgMethod
        get() = TgMethod("answerInlineQuery")
    override val TgAction<Boolean>.returnType: Class<Boolean>
        get() = getReturnType()
    override val OptionsFeature<AnswerInlineQueryAction, AnswerInlineQueryOptions>.options: AnswerInlineQueryOptions
        get() = AnswerInlineQueryOptions()

    init {
        parameters["inline_query_id"] = inlineQueryId
        parameters["results"] = results
    }
}

fun answerInlineQuery(inlineQueryId: String, results: MutableList<InlineQueryResult>.() -> Unit) =
    AnswerInlineQueryAction(inlineQueryId, mutableListOf<InlineQueryResult>().apply(results))

fun answerInlineQuery(inlineQueryId: String, vararg result: InlineQueryResult) =
    AnswerInlineQueryAction(inlineQueryId, listOf(*result))

fun answerInlineQuery(inlineQueryId: String, results: List<InlineQueryResult>) =
    AnswerInlineQueryAction(inlineQueryId, results)
