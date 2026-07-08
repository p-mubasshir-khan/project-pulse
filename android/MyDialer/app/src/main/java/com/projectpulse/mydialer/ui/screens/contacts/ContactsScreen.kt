package com.projectpulse.mydialer.ui.screens.contacts

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.projectpulse.mydialer.ui.navigation.Screen
import com.projectpulse.mydialer.ui.screens.contacts.components.ContactHeader
import com.projectpulse.mydialer.ui.screens.contacts.components.ContactListItemUi
import com.projectpulse.mydialer.ui.screens.contacts.components.ContactSearchBar
import com.projectpulse.mydialer.ui.screens.contacts.components.ContactsList
import com.projectpulse.mydialer.ui.screens.contacts.components.FavoriteContactUi
import com.projectpulse.mydialer.ui.screens.contacts.components.FavoriteContactsRow

@Composable
fun ContactsScreen(
    navController: NavController,
    viewModel: ContactsViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // 1. Fixed Glass Header (Only one header)
        ContactHeader(
            onMenuClick = { /* Handle Menu Click */ }
        )

        Box(modifier = Modifier.weight(1f)) {
            when {
                uiState.isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary,
                            strokeWidth = 3.dp
                        )
                    }
                }
                uiState.error != null -> {
                    ErrorState(
                        message = uiState.error!!,
                        onRetry = { viewModel.refresh() }
                    )
                }
                else -> {
                    // Map filtered contacts for the list
                    val mappedContacts = remember(uiState.filteredContacts) {
                        uiState.filteredContacts.map {
                            ContactListItemUi(
                                id = it.id.toLongOrNull() ?: 0L,
                                name = it.name.ifBlank { "Unknown" },
                                phoneNumber = it.phoneNumber,
                                phoneType = it.phoneType,
                                photoUri = it.avatar,
                                isFavorite = it.isFavorite
                            )
                        }
                    }

                    // Map favorites for the row
                    val favoriteContacts = remember(uiState.favoriteContacts) {
                        uiState.favoriteContacts.map {
                            FavoriteContactUi(
                                id = it.id.toLongOrNull() ?: 0L,
                                name = it.name.ifBlank { "Unknown" },
                                photoUri = it.avatar
                            )
                        }
                    }

                    // 2. Single LazyColumn implementation via ContactsList
                    ContactsList(
                        contacts = mappedContacts,
                        onContactClick = { contactId ->
                            navController.navigate(Screen.ContactDetails.createRoute(contactId))
                        },
                        onFavoriteToggle = { viewModel.toggleFavorite(it) },
                        searchBar = {
                            ContactSearchBar(
                                query = uiState.searchQuery,
                                onQueryChange = { viewModel.onSearchQueryChange(it) },
                                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                            )
                        },
                        favorites = {
                            // Only show favorites when not searching for a better UX
                            if (uiState.searchQuery.isEmpty()) {
                                FavoriteContactsRow(
                                    contacts = favoriteContacts,
                                    onContactClick = { contactId ->
                                        navController.navigate(Screen.ContactDetails.createRoute(contactId))
                                    }
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ErrorState(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White.copy(alpha = 0.6f),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        TextButton(onClick = onRetry) {
            Text(
                text = "Retry",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}
