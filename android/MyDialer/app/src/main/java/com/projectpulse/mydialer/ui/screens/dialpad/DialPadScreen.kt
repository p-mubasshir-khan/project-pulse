package com.projectpulse.mydialer.ui.screens.dialpad

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.projectpulse.mydialer.ui.components.EmptyState
import com.projectpulse.mydialer.ui.components.ScreenHeader

@Composable
fun DialPadScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScreenHeader(
            title = "Dial Pad",
            subtitle = "Enter a number to start a call."
        )
        EmptyState(message = "Dial pad placeholder")
    }
}
