package com.projectpulse.mydialer.ui.screens.dialpad.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projectpulse.mydialer.core.theme.PrimaryBlue

@Composable
fun PhoneNumberDisplay(
    textFieldValue: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
) {
    val phoneNumber = textFieldValue.text
    val focusRequester = remember { FocusRequester() }
    val interactionSource = remember { MutableInteractionSource() }
    
    val targetFontSize = when {
        phoneNumber.length <= 5 -> 52f
        phoneNumber.length <= 10 -> 44f
        phoneNumber.length <= 13 -> 38f
        phoneNumber.length <= 16 -> 32f
        else -> 28f
    }

    val animatedFontSize by animateFloatAsState(
        targetValue = targetFontSize,
        label = "PhoneNumberFontSize"
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .heightIn(min = 80.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight()
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    focusRequester.requestFocus()
                },
            contentAlignment = Alignment.Center
        ) {
            BasicTextField(
                value = textFieldValue,
                onValueChange = onValueChange,
                readOnly = true,
                textStyle = MaterialTheme.typography.displayMedium.copy(
                    fontSize = animatedFontSize.sp,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                cursorBrush = SolidColor(PrimaryBlue),
                interactionSource = interactionSource,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                singleLine = true
            )
        }
    }
}
