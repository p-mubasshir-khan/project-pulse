package com.projectpulse.mydialer.ui.screens.contacts.components

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

@Composable
fun AlphabetHeader(
    letter: Char,
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
                text = letter.toString(),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = PrimaryBlue
                )
            )
            
            HorizontalDivider(
                modifier = Modifier.padding(top = 4.dp),
                thickness = 0.5.dp,
                color = PrimaryBlue.copy(alpha = 0.2f)
            )
        }
    }
}
