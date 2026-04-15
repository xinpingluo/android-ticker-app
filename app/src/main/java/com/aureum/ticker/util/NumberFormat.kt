package com.aureum.ticker.util

import java.text.NumberFormat
import java.util.Locale

fun Double.formatPrice(): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale.US)
    formatter.minimumFractionDigits = 2
    formatter.maximumFractionDigits = 2
    return formatter.format(this)
}

fun Double.formatPercent(): String {
    val sign = if (this >= 0) "+" else ""
    return "$sign%.2f%%".format(this)
}

fun Double.formatChange(): String {
    val sign = if (this >= 0) "+" else ""
    return "$sign%.2f".format(this)
}

fun Long.formatCompact(): String = when {
    this >= 1_000_000_000_000 -> "%.2fT".format(this / 1_000_000_000_000.0)
    this >= 1_000_000_000 -> "%.2fB".format(this / 1_000_000_000.0)
    this >= 1_000_000 -> "%.1fM".format(this / 1_000_000.0)
    this >= 1_000 -> "%.1fK".format(this / 1_000.0)
    else -> this.toString()
}
