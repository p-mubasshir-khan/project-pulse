package com.projectpulse.mydialer.ui.screens.contacts.components

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.projectpulse.mydialer.core.theme.SurfaceGlass
import com.projectpulse.mydialer.ui.components.GlassSectionTitle

data class ContactListItemUi(
    val id: Long,
    val name: String,
    val phoneNumber: String,
    val phoneType: String,
    val photoUri: String?,
    val isFavorite: Boolean
)

@Composable
fun ContactsList(
    contacts: List<ContactListItemUi>,
    onContactClick: (Long) -> Unit,
    onFavoriteToggle: (Long) -> Unit,
    modifier: Modifier = Modifier,
    searchBar: @Composable () -> Unit = {},
    favorites: @Composable () -> Unit = {}
) {
    val listState = rememberLazyListState()
    
    // Scroll direction detection logic
    var previousIndex by remember { mutableIntStateOf(0) }
    var previousScrollOffset by remember { mutableIntStateOf(0) }
    var isScrollingUp by remember { mutableStateOf(true) }

    LaunchedEffect(listState.firstVisibleItemIndex, listState.firstVisibleItemScrollOffset) {
        val currentIndex = listState.firstVisibleItemIndex
        val currentOffset = listState.firstVisibleItemScrollOffset
        
        isScrollingUp = if (currentIndex < previousIndex) {
            true
        } else if (currentIndex > previousIndex) {
            false
        } else {
            currentOffset <= previousScrollOffset
        }
        
        previousIndex = currentIndex
        previousScrollOffset = currentOffset
    }

    // Always show at the very top, otherwise follow scroll direction
    val showSearchBar = isScrollingUp || (listState.firstVisibleItemIndex == 0 && listState.firstVisibleItemScrollOffset == 0)

    // Group and sort contacts by the first letter of their name
    val groupedContacts by remember(contacts) {
        derivedStateOf {
            contacts
                .sortedBy { it.name.lowercase() }
                .groupBy { it.name.firstOrNull()?.uppercaseChar() ?: '#' }
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = 80.dp, bottom = 120.dp), // Fixed top padding for SearchBar
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            // 2. Favorites item
            item(key = "favorites_row") {
                favorites()
            }

            if (contacts.isEmpty()) {
                item(key = "empty_state") {
                    EmptyContactsState(modifier = Modifier.fillParentMaxHeight(0.6f))
                }
            } else {
                groupedContacts.forEach { (initial, contactsInGroup) ->
                    // Alphabet Section Header
                    item(key = "header_$initial") {
                        GlassSectionTitle(
                            title = initial.toString(),
                            showDivider = true,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }

                    // Contacts in this section
                    items(
                        items = contactsInGroup,
                        key = { it.id }
                    ) { contact ->
                        ContactCard(
                            contact = ContactCardUi(
                                id = contact.id,
                                name = contact.name,
                                phoneNumber = contact.phoneNumber,
                                phoneType = contact.phoneType,
                                photoUri = contact.photoUri,
                                isFavorite = contact.isFavorite
                            ),
                            onClick = onContactClick,
                            onFavoriteToggle = onFavoriteToggle,
                            modifier = Modifier
                                .padding(vertical = 4.dp) // Spacing between ContactCards
                                .animateItem()
                        )
                    }
                }
            }
        }

        // Floating Scroll-Aware Search Bar
        AnimatedVisibility(
            visible = showSearchBar,
            enter = slideInVertically(animationSpec = tween(200)) { -it } + fadeIn(animationSpec = tween(200)),
            exit = slideOutVertically(animationSpec = tween(200)) { -it } + fadeOut(animationSpec = tween(200)),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
        ) {
            Box(modifier = Modifier.background(Color.Transparent)) {
                searchBar()
            }
        }
    }
}

@Composable
private fun EmptyContactsState(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .border(0.5.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(24.dp)),
            shape = RoundedCornerShape(24.dp),
            color = SurfaceGlass.copy(alpha = 0.1f)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.3f),
                    modifier = Modifier.size(64.dp)
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "No Contacts Available",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Medium,
                        color = Color.White.copy(alpha = 0.5f)
                    )
                )
            }
        }
    }
}
