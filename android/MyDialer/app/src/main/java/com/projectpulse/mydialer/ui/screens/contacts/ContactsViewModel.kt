package com.projectpulse.mydialer.ui.screens.contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectpulse.mydialer.domain.model.SuggestedContact
import com.projectpulse.mydialer.domain.usecase.GetAllContactsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ContactsUiState(
    val contacts: List<SuggestedContact> = emptyList(),
    val filteredContacts: List<SuggestedContact> = emptyList(),
    val favoriteContacts: List<SuggestedContact> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

class ContactsViewModel(
    private val getAllContactsUseCase: GetAllContactsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ContactsUiState())
    val uiState: StateFlow<ContactsUiState> = _uiState.asStateFlow()

    init {
        loadContacts()
    }

    fun refresh() {
        loadContacts()
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { state ->
            state.copy(
                searchQuery = query,
                filteredContacts = filterContacts(state.contacts, query)
            )
        }
    }

    fun toggleFavorite(contactId: Long) {
        _uiState.update { state ->
            val updatedContacts = state.contacts.map { contact ->
                val idLong = contact.id.toLongOrNull()
                if (idLong != null && idLong == contactId) {
                    contact.copy(isFavorite = !contact.isFavorite)
                } else {
                    contact
                }
            }
            state.copy(
                contacts = updatedContacts,
                filteredContacts = filterContacts(updatedContacts, state.searchQuery),
                favoriteContacts = updatedContacts.filter { it.isFavorite }
            )
        }
    }

    private fun loadContacts() {
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            try {
                val contactsList = getAllContactsUseCase()
                _uiState.update { state ->
                    state.copy(
                        contacts = contactsList,
                        filteredContacts = filterContacts(contactsList, state.searchQuery),
                        favoriteContacts = contactsList.filter { it.isFavorite },
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false, 
                        error = e.localizedMessage ?: "An unexpected error occurred"
                    ) 
                }
            }
        }
    }

    private fun filterContacts(contacts: List<SuggestedContact>, query: String): List<SuggestedContact> {
        if (query.isBlank()) return contacts
        
        val normalizedQuery = query.trim().lowercase()
        return contacts.filter { contact ->
            val nameMatch = contact.name.lowercase().contains(normalizedQuery)
            val phoneMatch = contact.phoneNumber.lowercase().contains(normalizedQuery)
            nameMatch || phoneMatch
        }
    }
}
