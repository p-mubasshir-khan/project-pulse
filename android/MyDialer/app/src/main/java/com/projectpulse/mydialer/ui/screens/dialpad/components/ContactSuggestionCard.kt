package com.projectpulse.mydialer.ui.screens.dialpad.components

import androidx.compose.animation.core.spring
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.projectpulse.mydialer.core.theme.BorderGlass
import com.projectpulse.mydialer.core.theme.PrimaryBlue
import com.projectpulse.mydialer.core.theme.SurfaceGlass
import com.projectpulse.mydialer.core.theme.TextSecondary
import com.projectpulse.mydialer.domain.model.SuggestedContact

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ContactSuggestionCard(
    contact: SuggestedContact?,
    modifier: Modifier = Modifier
) {
    if (contact != null) {
        Surface(
            modifier = modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .height(65.dp) // Reduced by 7dp (from 72dp)
                .border(1.dp, BorderGlass.copy(alpha = 0.2f), RoundedCornerShape(20.dp)),
            shape = RoundedCornerShape(20.dp),
            color = SurfaceGlass.copy(alpha = 0.1f),
            tonalElevation = 4.dp
        ) {
            AnimatedContent(
                targetState = contact,
                transitionSpec = {
                    (fadeIn() + scaleIn(initialScale = 0.95f))
                        .togetherWith(fadeOut())
                        .using(SizeTransform(clip = false))
                },
                label = "ContactCardContent"
            ) { targetContact ->
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Avatar
                    Box(
                        modifier = Modifier
                            .size(42.dp) // Reduced from 48dp to fit smaller card
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)),
                        contentAlignment = Alignment.Center
                    ) {
                        if (targetContact.avatar != null) {
                            AsyncImage(
                                model = targetContact.avatar,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            Text(
                                text = targetContact.name.take(1).uppercase(),
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    // Contact Info
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = targetContact.name.uppercase(),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 15.sp // Slightly smaller
                            ),
                            color = Color.White,
                            maxLines = 1
                        )
                        Text(
                            text = targetContact.phoneNumber,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 12.sp, // Slightly smaller
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                            ),
                            maxLines = 1
                        )
                    }

                    // Right Side: Chip and Country
                    Column(
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color.Transparent,
                            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
                        ) {
                            Text(
                                text = targetContact.phoneType,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 8.sp),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(3.dp)
                                    .clip(CircleShape)
                                    .background(TextSecondary)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "India",
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                                color = TextSecondary
                            )
                        }
                    }
                }
            }
        }
    }
}
