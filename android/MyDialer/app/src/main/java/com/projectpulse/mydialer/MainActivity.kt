package com.projectpulse.mydialer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.projectpulse.mydialer.core.theme.MyDialerTheme
import com.projectpulse.mydialer.ui.navigation.NavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyDialerTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}
