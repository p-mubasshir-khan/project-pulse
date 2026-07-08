package com.projectpulse.mydialer.domain.usecase

import com.projectpulse.mydialer.domain.model.SuggestedContact
import com.projectpulse.mydialer.domain.repository.ContactsRepository

class GetAllContactsUseCase(private val repository: ContactsRepository) {
    
    suspend operator fun invoke(): List<SuggestedContact> {
        return repository.getContacts()
    }
}
