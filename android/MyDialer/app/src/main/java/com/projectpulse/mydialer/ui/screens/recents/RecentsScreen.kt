package com.projectpulse.mydialer.ui.screens.recents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.projectpulse.mydialer.ui.components.EmptyState

@Composable
fun RecentsScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmptyState(message = "No recent calls")
    }
}
