package com.projectpulse.mydialer.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.shadow
import androidx.navigation.NavController
import com.projectpulse.mydialer.ui.screens.contacts.ContactsViewModel
import com.projectpulse.mydialer.core.theme.BorderGlass
import com.projectpulse.mydialer.core.theme.PrimaryBlue
import com.projectpulse.mydialer.core.theme.SurfaceGlass
import com.projectpulse.mydialer.ui.components.BottomBar
import com.projectpulse.mydialer.ui.navigation.Screen
import com.projectpulse.mydialer.ui.navigation.bottomNavItems
import com.projectpulse.mydialer.ui.screens.contacts.ContactsScreen
import com.projectpulse.mydialer.ui.screens.dialpad.DialPadScreen
import com.projectpulse.mydialer.ui.screens.favorites.FavoritesScreen
import com.projectpulse.mydialer.ui.screens.recents.RecentsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController, contactsViewModel: ContactsViewModel) {
    val pagerState = rememberPagerState(initialPage = 1) { bottomNavItems.size }
    var showMenu by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            if (bottomNavItems[pagerState.currentPage] != Screen.Contacts) {
                TopAppBarPill(
                    onMenuClick = { showMenu = true },
                    showMenu = showMenu,
                    onDismissMenu = { showMenu = false }
                )
            }
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                BottomBar(pagerState = pagerState)
            }
        }
    ) { innerPadding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                when (bottomNavItems[page]) {
                    Screen.Recents -> RecentsScreen()
                    Screen.DialPad -> DialPadScreen()
                    Screen.Contacts -> ContactsScreen(
                        navController = navController,
                        viewModel = contactsViewModel
                    )
                    Screen.Favorites -> FavoritesScreen()
                    else -> {}
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarPill(
    onMenuClick: () -> Unit,
    showMenu: Boolean,
    onDismissMenu: () -> Unit
) {
    Surface(
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 24.dp, vertical = 8.dp)
            .fillMaxWidth()
            .height(72.dp)
            .border(1.dp, BorderGlass, RoundedCornerShape(28.dp)),
        shape = RoundedCornerShape(28.dp),
        color = SurfaceGlass,
        tonalElevation = 8.dp
    ) {
        TopAppBar(
            title = {
                Text(
                    "MyDialer",
                    modifier = Modifier.padding(start = 8.dp),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                )
            },
            actions = {
                IconButton(onClick = onMenuClick) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More",
                        tint = Color.White
                    )
                }
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = onDismissMenu,
                    modifier = Modifier.background(MaterialTheme.colorScheme.surface.copy(alpha = 0.95f))
                ) {
                    DropdownMenuItem(text = { Text("Settings") }, onClick = onDismissMenu)
                    DropdownMenuItem(text = { Text("Theme") }, onClick = onDismissMenu)
                    DropdownMenuItem(text = { Text("Speed Dial") }, onClick = onDismissMenu)
                    DropdownMenuItem(text = { Text("About") }, onClick = onDismissMenu)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            )
        )
    }
}
