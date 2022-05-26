package com.github.vendelieu.tgbot.types

data class Invoice(
    val title: String,
    val description: String,
    val startParameter: String,
    val currency: Currency,
    val totalAmount: Int,
)
