package com.projectpulse.mydialer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.projectpulse.mydialer.ui.screens.contacts.ContactsScreen
import com.projectpulse.mydialer.ui.screens.dialpad.DialPadScreen
import com.projectpulse.mydialer.ui.screens.favorites.FavoritesScreen
import com.projectpulse.mydialer.ui.screens.recents.RecentsScreen
import com.projectpulse.mydialer.ui.screens.settings.SettingsScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.DialPad.route,
        modifier = modifier
    ) {
        composable(Screen.Recents.route) {
            RecentsScreen()
        }
        composable(Screen.Contacts.route) {
            ContactsScreen()
        }
        composable(Screen.DialPad.route) {
            DialPadScreen()
        }
        composable(Screen.Favorites.route) {
            FavoritesScreen()
        }
        composable(Screen.Settings.route) {
            SettingsScreen()
        }
    }
}
