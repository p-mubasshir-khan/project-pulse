package com.projectpulse.mydialer.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.Dialpad
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Recents : Screen("recents", "Recents", Icons.Default.Call)
    object Contacts : Screen("contacts", "Contacts", Icons.Default.Contacts)
    object DialPad : Screen("dialpad", "Dial Pad", Icons.Default.Dialpad)
    object Favorites : Screen("favorites", "Favorites", Icons.Default.Favorite)
    object Settings : Screen("settings", "Settings", Icons.Default.Settings)
}

val bottomNavItems = listOf(
    Screen.Recents,
    Screen.DialPad,
    Screen.Contacts,
    Screen.Favorites
)
