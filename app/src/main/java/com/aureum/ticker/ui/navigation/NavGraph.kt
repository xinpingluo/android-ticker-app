package com.aureum.ticker.ui.navigation

import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.aureum.ticker.ui.screens.detail.DetailScreen
import com.aureum.ticker.ui.screens.home.HomeScreen

@Composable
fun AureumNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        enterTransition = {
            fadeIn(tween(350)) + slideInVertically(
                animationSpec = tween(400, easing = EaseOutCubic),
                initialOffsetY = { it / 5 },
            )
        },
        exitTransition = { fadeOut(tween(200)) },
        popEnterTransition = { fadeIn(tween(200)) },
        popExitTransition = {
            fadeOut(tween(200)) + slideOutVertically(
                animationSpec = tween(300),
                targetOffsetY = { it / 5 },
            )
        },
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onTickerClick = { symbol ->
                    navController.navigate(Screen.Detail.createRoute(symbol))
                },
            )
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("symbol") { type = NavType.StringType }),
        ) { backStackEntry ->
            val symbol = backStackEntry.arguments?.getString("symbol") ?: return@composable
            DetailScreen(
                symbol = symbol,
                onBack = { navController.popBackStack() },
            )
        }
    }
}
