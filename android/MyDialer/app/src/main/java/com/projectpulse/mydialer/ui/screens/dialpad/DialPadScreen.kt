package com.projectpulse.mydialer.ui.screens.dialpad

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.projectpulse.mydialer.data.repository.DeviceContactsRepository
import com.projectpulse.mydialer.domain.usecase.GetSuggestedContactUseCase
import com.projectpulse.mydialer.ui.screens.dialpad.components.ContactSuggestionCard
import com.projectpulse.mydialer.ui.screens.dialpad.components.DialPadActionRow
import com.projectpulse.mydialer.ui.screens.dialpad.components.DialPadGrid
import com.projectpulse.mydialer.ui.screens.dialpad.components.PhoneNumberDisplay

@Composable
fun DialPadScreen() {
    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current
    
    val repository = remember { DeviceContactsRepository(context.applicationContext) }
    val getSuggestedContactUseCase = remember { GetSuggestedContactUseCase(repository) }
    
    val viewModel: DialPadViewModel = viewModel(
        factory = remember {
            object : androidx.lifecycle.ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                    return DialPadViewModel(getSuggestedContactUseCase) as T
                }
            }
        }
    )

    val phoneNumberState by viewModel.phoneNumber
    val suggestedContact by viewModel.suggestedContact

    // Use TextFieldValue to track cursor position
    var textFieldValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(text = phoneNumberState, selection = TextRange(phoneNumberState.length)))
    }

    // Sync external changes (like clearNumber) to our local TextFieldValue
    LaunchedEffect(phoneNumberState) {
        // Only update if the text is actually different
        if (phoneNumberState != textFieldValue.text) {
            textFieldValue = textFieldValue.copy(
                text = phoneNumberState,
                // If the number was cleared, reset selection to 0, otherwise keep it at the end
                selection = if (phoneNumberState.isEmpty()) TextRange(0) else TextRange(phoneNumberState.length)
            )
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        viewModel.onPermissionResult(isGranted)
    }

    LaunchedEffect(Unit) {
        val permission = Manifest.permission.READ_CONTACTS
        if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
            viewModel.onPermissionResult(true)
        } else {
            permissionLauncher.launch(permission)
        }
    }

    // Helper functions for cursor-aware editing
    fun onDigitClick(digit: String) {
        val currentText = textFieldValue.text
        val selection = textFieldValue.selection
        val newText = currentText.substring(0, selection.start) + digit + currentText.substring(selection.end)
        val newSelection = TextRange(selection.start + digit.length)
        
        textFieldValue = TextFieldValue(newText, newSelection)
        viewModel.onPhoneNumberChange(newText)
    }

    fun onDeleteClick() {
        val currentText = textFieldValue.text
        val selection = textFieldValue.selection
        
        if (selection.start > 0 || selection.start != selection.end) {
            val newText = if (selection.start != selection.end) {
                currentText.substring(0, selection.start) + currentText.substring(selection.end)
            } else {
                currentText.substring(0, selection.start - 1) + currentText.substring(selection.start)
            }
            val newSelection = if (selection.start != selection.end) {
                TextRange(selection.start)
            } else {
                TextRange(selection.start - 1)
            }
            
            textFieldValue = TextFieldValue(newText, newSelection)
            viewModel.onPhoneNumberChange(newText)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.15f))

        // 1. Phone Number Display
        PhoneNumberDisplay(
            textFieldValue = textFieldValue,
            onValueChange = { textFieldValue = it },
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // 2. Contact Card Area
        AnimatedVisibility(
            visible = phoneNumberState.length >= 5 && suggestedContact != null,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(65.dp),
                contentAlignment = Alignment.Center
            ) {
                ContactSuggestionCard(contact = suggestedContact)
            }
        }

        Spacer(modifier = Modifier.weight(0.05f))

        // 3. Dial Pad Grid
        DialPadGrid(
            onNumberClick = { onDigitClick(it) },
            onLongClickZero = { onDigitClick("+") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 4. Action Row (SIM - CALL - DELETE)
        DialPadActionRow(
            phoneNumber = phoneNumberState,
            onCallClick = {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            },
            onDeleteClick = { onDeleteClick() },
            onDeleteLongClick = { viewModel.clearNumber() },
            onSimClick = { /* SIM Selection */ }
        )
        
        Spacer(modifier = Modifier.weight(0.1f))
    }
}
