package com.projectpulse.mydialer.ui.screens.contacts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.projectpulse.mydialer.ui.components.EmptyState
import com.projectpulse.mydialer.ui.components.ScreenHeader

@Composable
fun ContactsScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScreenHeader(
            title = "Contacts",
            subtitle = "View and manage your contact list."
        )
        EmptyState(message = "No contacts found")
    }
}
