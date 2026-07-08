package com.projectpulse.mydialer.ui.screens.contactdetails

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.projectpulse.mydialer.core.theme.PrimaryBlue
import com.projectpulse.mydialer.ui.components.GlassCard
import com.projectpulse.mydialer.ui.components.GlassSectionTitle
import com.projectpulse.mydialer.ui.screens.contactdetails.components.ContactActionButtons
import com.projectpulse.mydialer.ui.screens.contactdetails.components.ContactDetailsHeader
import com.projectpulse.mydialer.ui.screens.contacts.ContactsViewModel
import com.projectpulse.mydialer.ui.screens.contacts.components.ContactCardUi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDetailsScreen(
    contactId: Long,
    viewModel: ContactsViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val contact = remember(uiState.contacts, contactId) {
        uiState.contacts.find { it.id.toLongOrNull() == contactId }
    }

    if (contact == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = PrimaryBlue)
        }
        return
    }

    val contactUi = ContactCardUi(
        id = contact.id.toLongOrNull() ?: 0L,
        name = contact.name,
        phoneNumber = contact.phoneNumber,
        phoneType = contact.phoneType,
        photoUri = contact.avatar,
        isFavorite = contact.isFavorite
    )

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            // 1. Contact Details Header
            ContactDetailsHeader(
                name = contactUi.name,
                phoneNumber = contactUi.phoneNumber,
                photoUri = contactUi.photoUri
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 2. Contact Action Buttons
            ContactActionButtons(
                isFavorite = contactUi.isFavorite,
                onCallClick = { /* Handle Call */ },
                onMessageClick = { /* Handle Message */ },
                onFavoriteClick = { viewModel.toggleFavorite(contactId) },
                onShareClick = { /* Handle Share */ }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // 3. Recent Calls Section
            GlassSectionTitle(title = "Recent Calls")
            
            GlassCard(modifier = Modifier.padding(horizontal = 20.dp)) {
                Text(
                    text = "Recent call history will appear here",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.5f)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 4. Additional Information Section
            GlassSectionTitle(title = "Additional Information")

            GlassCard(modifier = Modifier.padding(horizontal = 20.dp)) {
                InfoRow(label = "Phone Number", value = contactUi.phoneNumber)
                Spacer(modifier = Modifier.height(16.dp))
                InfoRow(label = "Phone Type", value = contactUi.phoneType)
                Spacer(modifier = Modifier.height(16.dp))
                InfoRow(label = "Favorite Status", value = if (contactUi.isFavorite) "Favorite" else "Not Favorite")
            }

            Spacer(modifier = Modifier.height(120.dp)) // Bottom Padding
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = PrimaryBlue.copy(alpha = 0.7f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        )
    }
}
