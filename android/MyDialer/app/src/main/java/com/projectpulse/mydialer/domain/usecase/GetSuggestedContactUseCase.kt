package com.projectpulse.mydialer.domain.usecase

import com.projectpulse.mydialer.domain.model.SuggestedContact
import com.projectpulse.mydialer.domain.repository.ContactsRepository

class GetSuggestedContactUseCase(private val repository: ContactsRepository) {
    
    suspend operator fun invoke(query: String): SuggestedContact? {
        if (query.isEmpty()) return null
        
        val normalizedQuery = normalize(query)
        val contacts = repository.getContacts()
        
        return contacts.firstOrNull { contact ->
            val normalizedContactNumber = normalize(contact.phoneNumber)
            // Stricter matching: Contact number must start with the query
            normalizedContactNumber.startsWith(normalizedQuery)
        }
    }

    private fun normalize(number: String): String {
        // Strip everything except digits
        var normalized = number.filter { it.isDigit() }
        
        // Remove common country prefixes if present (e.g., 91 for India)
        // A more robust way would be to check if it's longer than 10 digits
        if (normalized.length > 10) {
            if (normalized.startsWith("91")) {
                normalized = normalized.substring(2)
            } else if (normalized.startsWith("0")) {
                normalized = normalized.substring(1)
            }
        }
        
        return normalized
    }
}
