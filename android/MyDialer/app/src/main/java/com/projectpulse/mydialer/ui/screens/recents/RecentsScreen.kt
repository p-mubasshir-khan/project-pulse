package com.projectpulse.mydialer.ui.screens.recents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.projectpulse.mydialer.ui.components.EmptyState
import com.projectpulse.mydialer.ui.components.ScreenHeader

@Composable
fun RecentsScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScreenHeader(
            title = "Recents",
            subtitle = "Your recent call history will appear here."
        )
        EmptyState(message = "No recent calls")
    }
}
