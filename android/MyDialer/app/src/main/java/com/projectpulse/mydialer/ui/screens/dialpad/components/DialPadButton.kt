package com.projectpulse.mydialer.ui.screens.dialpad.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projectpulse.mydialer.core.theme.BorderGlass
import com.projectpulse.mydialer.core.theme.SurfaceGlass

@OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun DialPadButton(
    number: String,
    letters: String? = null,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onLongClick: (() -> Unit)? = null
) {
    val haptic = LocalHapticFeedback.current
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1.0f,
        animationSpec = spring(),
        label = "scale"
    )

    Surface(
        modifier = modifier
            .size(72.dp)
            .scale(scale)
            .clip(CircleShape)
            .border(1.dp, BorderGlass.copy(alpha = 0.3f), CircleShape),
        color = SurfaceGlass.copy(alpha = 0.12f),
        shape = CircleShape,
        tonalElevation = 2.dp
    ) {
        Box(
            modifier = Modifier
                .combinedClickable(
                    interactionSource = interactionSource,
                    indication = androidx.compose.foundation.LocalIndication.current,
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        onClick()
                    },
                    onLongClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        onLongClick?.invoke()
                    }
                )
                .focusable(false),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = number,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    color = Color.White
                )
                if (letters != null) {
                    Text(
                        text = letters,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Normal,
                            letterSpacing = 1.sp
                        ),
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                    )
                }
            }
        }
    }
}
