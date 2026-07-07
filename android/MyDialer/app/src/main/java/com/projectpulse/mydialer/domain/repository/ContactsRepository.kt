package com.projectpulse.mydialer.domain.repository

import com.projectpulse.mydialer.domain.model.SuggestedContact

interface ContactsRepository {
    suspend fun getContacts(): List<SuggestedContact>
}
