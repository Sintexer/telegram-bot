package eu.vendeli.tgbot.types.internal

import eu.vendeli.tgbot.types.internal.configuration.RateLimits
import java.lang.reflect.Method

internal data class Invocation(
    val clazz: Class<*>,
    val method: Method,
    val namedParameters: Map<String, String> = emptyMap(),
    val scope: Set<CommandScope> = emptySet(),
    val rateLimits: RateLimits,
    val type: ActionType,
)
