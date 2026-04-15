package com.aureum.ticker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.aureum.ticker.ui.navigation.AureumNavGraph
import com.aureum.ticker.ui.theme.AureumBlack
import com.aureum.ticker.ui.theme.AureumTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            AureumTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = AureumBlack,
                ) {
                    val navController = rememberNavController()
                    AureumNavGraph(navController = navController)
                }
            }
        }
    }
}
