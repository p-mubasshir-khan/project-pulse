package com.projectpulse.mydialer.ui.screens.contactdetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.projectpulse.mydialer.core.theme.PrimaryBlue
import com.projectpulse.mydialer.core.theme.SurfaceGlass

@Composable
fun ContactDetailsHeader(
    name: String,
    phoneNumber: String,
    photoUri: String?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Large Circular Avatar with Glass Styling
        Box(
            modifier = Modifier
                .size(96.dp)
                .clip(CircleShape)
                .background(SurfaceGlass.copy(alpha = 0.15f))
                .border(0.5.dp, Color.White.copy(alpha = 0.12f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (photoUri != null) {
                AsyncImage(
                    model = photoUri,
                    contentDescription = name,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Text(
                    text = name.take(1).uppercase(),
                    style = MaterialTheme.typography.displaySmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = PrimaryBlue
                    )
                )
            }
            
            // Subtle blue glow accent
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .border(2.dp, PrimaryBlue.copy(alpha = 0.08f), CircleShape)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Contact Name
        Text(
            text = name,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                letterSpacing = 0.5.sp
            ),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Phone Number
        Text(
            text = phoneNumber,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 18.sp,
                color = Color.White.copy(alpha = 0.6f),
                letterSpacing = 0.5.sp
            ),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}
