package com.aureum.ticker.ui.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Detail : Screen("detail/{symbol}") {
        fun createRoute(symbol: String) = "detail/$symbol"
    }
}
