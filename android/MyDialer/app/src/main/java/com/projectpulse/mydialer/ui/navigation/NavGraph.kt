package com.projectpulse.mydialer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.projectpulse.mydialer.data.repository.DeviceContactsRepository
import com.projectpulse.mydialer.domain.usecase.GetAllContactsUseCase
import com.projectpulse.mydialer.ui.MainScreen
import com.projectpulse.mydialer.ui.screens.contacts.ContactsViewModel
import com.projectpulse.mydialer.ui.screens.contacts.components.ContactCardUi
import com.projectpulse.mydialer.ui.screens.contactdetails.ContactDetailsScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val repository = remember { DeviceContactsRepository(context.applicationContext) }
    val getAllContactsUseCase = remember { GetAllContactsUseCase(repository) }
    
    // Scoping ViewModel to NavGraph so both screens can potentially share it or just reuse logic
    val contactsViewModel: ContactsViewModel = viewModel(
        factory = remember {
            object : androidx.lifecycle.ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                    return ContactsViewModel(getAllContactsUseCase) as T
                }
            }
        }
    )

    NavHost(
        navController = navController,
        startDestination = Screen.Main.route,
        modifier = modifier
    ) {
        composable(Screen.Main.route) {
            MainScreen(navController = navController, contactsViewModel = contactsViewModel)
        }
        
        composable(
            route = Screen.ContactDetails.route,
            arguments = listOf(navArgument("contactId") { type = NavType.LongType })
        ) { backStackEntry ->
            val contactId = backStackEntry.arguments?.getLong("contactId") ?: 0L
            ContactDetailsScreen(
                contactId = contactId,
                viewModel = contactsViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
