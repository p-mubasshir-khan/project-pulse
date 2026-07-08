package com.projectpulse.mydialer.ui.screens.contacts.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.projectpulse.mydialer.core.theme.PrimaryBlue
import com.projectpulse.mydialer.core.theme.SurfaceGlass

data class ContactCardUi(
    val id: Long,
    val name: String,
    val phoneNumber: String,
    val phoneType: String,
    val photoUri: String?,
    val isFavorite: Boolean
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContactCard(
    contact: ContactCardUi,
    onClick: (Long) -> Unit,
    onFavoriteToggle: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.97f else 1.0f,
        animationSpec = spring(dampingRatio = 0.75f, stiffness = 300f),
        label = "ScaleAnimation"
    )

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(88.dp)
            .scale(scale)
            .padding(horizontal = 20.dp, vertical = 6.dp)
            .border(0.5.dp, Color.White.copy(alpha = 0.12f), RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        color = SurfaceGlass.copy(alpha = 0.15f),
        tonalElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .combinedClickable(
                    interactionSource = interactionSource,
                    indication = androidx.compose.foundation.LocalIndication.current,
                    onClick = { onClick(contact.id) },
                    onLongClick = { /* Reserved for future context menu */ }
                )
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left Section: Avatar
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(SurfaceGlass.copy(alpha = 0.1f))
                    .border(1.dp, Color.White.copy(alpha = 0.1f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                if (contact.photoUri != null) {
                    AsyncImage(
                        model = contact.photoUri,
                        contentDescription = contact.name,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Text(
                        text = contact.name.take(1).uppercase(),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = PrimaryBlue
                        )
                    )
                }
                
                // Subtle blue glow accent
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .border(1.dp, PrimaryBlue.copy(alpha = 0.05f), CircleShape)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Center Section: Contact Details
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = contact.name,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Text(
                    text = contact.phoneType,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.5f)
                    ),
                    maxLines = 1
                )
                
                Text(
                    text = contact.phoneNumber,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 13.sp,
                        color = PrimaryBlue.copy(alpha = 0.8f)
                    ),
                    maxLines = 1
                )
            }

            // Right Section: Favorite Icon Toggle
            IconButton(
                onClick = { onFavoriteToggle(contact.id) },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = if (contact.isFavorite) Icons.Default.Star else Icons.Outlined.StarBorder,
                    contentDescription = if (contact.isFavorite) "Remove from favorites" else "Add to favorites",
                    tint = if (contact.isFavorite) Color(0xFFFFD700) else Color.White.copy(alpha = 0.4f),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
