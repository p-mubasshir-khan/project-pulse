package com.projectpulse.mydialer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.projectpulse.mydialer.core.theme.MyDialerTheme
import com.projectpulse.mydialer.ui.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyDialerTheme {
                MainScreen()
            }
        }
    }
}
