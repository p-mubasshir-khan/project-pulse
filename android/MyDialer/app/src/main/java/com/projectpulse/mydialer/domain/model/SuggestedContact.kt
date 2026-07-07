package com.projectpulse.mydialer.domain.model

data class SuggestedContact(
    val id: String,
    val name: String,
    val phoneNumber: String,
    val phoneType: String,
    val isFavorite: Boolean,
    val avatar: String? = null
)
