package com.projectpulse.mydialer.ui.screens.favorites

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.projectpulse.mydialer.ui.components.EmptyState
import com.projectpulse.mydialer.ui.components.ScreenHeader

@Composable
fun FavoritesScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScreenHeader(
            title = "Favorites",
            subtitle = "Quickly access your favorite contacts."
        )
        EmptyState(message = "No favorite contacts")
    }
}
