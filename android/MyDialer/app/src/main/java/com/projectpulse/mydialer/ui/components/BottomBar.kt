package com.projectpulse.mydialer.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projectpulse.mydialer.core.theme.PrimaryBlue
import com.projectpulse.mydialer.core.theme.SurfaceGlass
import com.projectpulse.mydialer.ui.navigation.bottomNavItems
import kotlinx.coroutines.launch

@Composable
fun BottomBar(pagerState: PagerState) {
    val scope = rememberCoroutineScope()

    Surface(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
            .height(72.dp)
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(36.dp),
                spotColor = PrimaryBlue.copy(alpha = 0.2f)
            )
            .border(0.5.dp, Color.White.copy(alpha = 0.12f), RoundedCornerShape(36.dp)),
        shape = RoundedCornerShape(36.dp),
        color = SurfaceGlass.copy(alpha = 0.15f),
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            bottomNavItems.forEachIndexed { index, screen ->
                val isSelected = pagerState.currentPage == index
                
                BottomNavItem(
                    screen = screen,
                    isSelected = isSelected,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun BottomNavItem(
    screen: com.projectpulse.mydialer.ui.navigation.Screen,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val contentColor by animateColorAsState(
        targetValue = if (isSelected) PrimaryBlue else Color.White.copy(alpha = 0.5f),
        animationSpec = spring(),
        label = "color"
    )

    val backgroundAlpha by animateFloatAsState(
        targetValue = if (isSelected) 0.12f else 0f,
        animationSpec = spring(),
        label = "bgAlpha"
    )

    Box(
        modifier = Modifier
            .height(48.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Color.White.copy(alpha = backgroundAlpha))
            .then(
                if (isSelected) {
                    Modifier.border(0.5.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(24.dp))
                } else Modifier
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            screen.icon?.let { icon ->
                Icon(
                    imageVector = icon,
                    contentDescription = screen.title,
                    tint = contentColor,
                    modifier = Modifier.size(24.dp)
                )
            }
            if (isSelected) {
                screen.title?.let { title ->
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = title,
                        color = contentColor,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 0.5.sp
                        )
                    )
                }
            }
        }
    }
}
