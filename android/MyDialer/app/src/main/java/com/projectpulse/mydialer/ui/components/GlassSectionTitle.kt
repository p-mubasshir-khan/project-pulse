package com.projectpulse.mydialer.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projectpulse.mydialer.core.theme.PrimaryBlue

/**
 * A reusable section title component for MyDialer with a premium glass aesthetic.
 * 
 * @param title The text to display as the section header.
 * @param showDivider Whether to display a subtle divider below the title.
 * @param modifier Custom modifier for layout adjustments.
 */
@Composable
fun GlassSectionTitle(
    title: String,
    showDivider: Boolean = false,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = true,
        enter = fadeIn(),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = PrimaryBlue,
                    letterSpacing = 0.5.sp
                )
            )
            
            if (showDivider) {
                HorizontalDivider(
                    modifier = Modifier.padding(top = 8.dp),
                    thickness = 0.5.dp,
                    color = PrimaryBlue.copy(alpha = 0.2f)
                )
            }
        }
    }
}
