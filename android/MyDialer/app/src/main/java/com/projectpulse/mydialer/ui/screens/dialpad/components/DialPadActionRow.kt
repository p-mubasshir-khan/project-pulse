package com.projectpulse.mydialer.ui.screens.dialpad.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.core.spring
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Backspace
import androidx.compose.material.icons.filled.SimCard
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projectpulse.mydialer.core.theme.BorderGlass
import com.projectpulse.mydialer.core.theme.SurfaceGlass

@OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun DialPadActionRow(
    phoneNumber: String,
    onCallClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onDeleteLongClick: () -> Unit,
    onSimClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
            .height(76.dp) // Refined height
            .border(1.dp, BorderGlass.copy(alpha = 0.3f), RoundedCornerShape(38.dp)),
        shape = RoundedCornerShape(38.dp),
        color = SurfaceGlass.copy(alpha = 0.15f), // Enhanced glass finishing
        tonalElevation = 6.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // SIM Selector
            Column(
                modifier = Modifier
                    .width(60.dp)
                    .clip(CircleShape)
                    .combinedClickable(onClick = onSimClick),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.SimCard,
                    contentDescription = "SIM",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(26.dp)
                )
                Text(
                    text = "SIM 1",
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.9f)
                )
            }

            // Call Button
            CallButton(
                isEnabled = phoneNumber.isNotEmpty(),
                onClick = onCallClick,
                modifier = Modifier.size(64.dp)
            )

            // Delete Button Area
            Box(
                modifier = Modifier.size(60.dp),
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.animation.AnimatedVisibility(
                    visible = phoneNumber.isNotEmpty(),
                    enter = fadeIn() + scaleIn(),
                    exit = fadeOut() + scaleOut()
                ) {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .combinedClickable(
                                onClick = onDeleteClick,
                                onLongClick = onDeleteLongClick
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Backspace,
                            contentDescription = "Delete",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }
        }
    }
}
