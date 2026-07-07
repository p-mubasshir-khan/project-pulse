package com.projectpulse.mydialer.ui.screens.dialpad.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.shadow
import com.projectpulse.mydialer.core.theme.DialPadGreen
import com.projectpulse.mydialer.core.theme.PrimaryBlue

@Composable
fun CallButton(
    isEnabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val haptic = LocalHapticFeedback.current
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1.0f,
        animationSpec = spring(),
        label = "scale"
    )

    val containerColor by animateColorAsState(
        targetValue = if (isEnabled) DialPadGreen else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
        label = "color"
    )
    
    val iconColor by animateColorAsState(
        targetValue = if (isEnabled) Color.White else Color.White.copy(alpha = 0.5f),
        label = "iconColor"
    )

    Surface(
        modifier = modifier
            .size(72.dp)
            .scale(scale)
            .clip(CircleShape)
            .clickable(
                interactionSource = interactionSource,
                indication = androidx.compose.foundation.LocalIndication.current,
                enabled = isEnabled,
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    onClick()
                }
            ),
        shape = CircleShape,
        color = containerColor,
        shadowElevation = if (isEnabled) 12.dp else 0.dp
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = Icons.Default.Call,
                contentDescription = "Call",
                tint = iconColor,
                modifier = Modifier.size(36.dp)
            )
        }
    }
}
