package com.projectpulse.mydialer.ui.screens.dialpad

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectpulse.mydialer.domain.model.SuggestedContact
import com.projectpulse.mydialer.domain.usecase.GetSuggestedContactUseCase
import kotlinx.coroutines.launch

class DialPadViewModel(
    private val getSuggestedContactUseCase: GetSuggestedContactUseCase
) : ViewModel() {

    private val _phoneNumber = mutableStateOf("")
    val phoneNumber: State<String> = _phoneNumber

    private val _suggestedContact = mutableStateOf<SuggestedContact?>(null)
    val suggestedContact: State<SuggestedContact?> = _suggestedContact

    private val _hasPermission = mutableStateOf(false)
    val hasPermission: State<Boolean> = _hasPermission

    fun onPhoneNumberChange(newNumber: String) {
        _phoneNumber.value = newNumber
        updateSuggestion()
    }

    fun onPermissionResult(isGranted: Boolean) {
        _hasPermission.value = isGranted
        if (isGranted) {
            updateSuggestion()
        }
    }

    private fun updateSuggestion() {
        // Requirement: Do NOT search contacts until at least 5 digits have been entered.
        if (!_hasPermission.value || _phoneNumber.value.length < 5) {
            _suggestedContact.value = null
            return
        }

        viewModelScope.launch {
            _suggestedContact.value = getSuggestedContactUseCase(_phoneNumber.value)
        }
    }

    fun appendDigit(digit: String) {
        onPhoneNumberChange(_phoneNumber.value + digit)
    }

    fun deleteDigit() {
        if (_phoneNumber.value.isNotEmpty()) {
            onPhoneNumberChange(_phoneNumber.value.dropLast(1))
        }
    }

    fun clearNumber() {
        onPhoneNumberChange("")
    }
}
