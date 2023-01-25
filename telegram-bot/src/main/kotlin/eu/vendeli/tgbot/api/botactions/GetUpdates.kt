@file:Suppress("MatchingDeclarationName")

package eu.vendeli.tgbot.api.botactions

import eu.vendeli.tgbot.interfaces.SimpleAction
import eu.vendeli.tgbot.interfaces.WrappedTypeOf
import eu.vendeli.tgbot.interfaces.features.OptionAble
import eu.vendeli.tgbot.interfaces.features.OptionsFeature
import eu.vendeli.tgbot.interfaces.getInnerType
import eu.vendeli.tgbot.types.Update
import eu.vendeli.tgbot.types.internal.TgMethod
import eu.vendeli.tgbot.types.internal.options.GetUpdatesOptions

class GetUpdatesAction :
    SimpleAction<List<Update>>,
    OptionAble,
    OptionsFeature<GetUpdatesAction, GetUpdatesOptions>,
    WrappedTypeOf<Update> {
    override val method: TgMethod = TgMethod("getUpdates")
    override var options = GetUpdatesOptions()
    override val parameters: MutableMap<String, Any?> = mutableMapOf()

    override val wrappedDataType = getInnerType()
}

fun getUpdates() = GetUpdatesAction()
