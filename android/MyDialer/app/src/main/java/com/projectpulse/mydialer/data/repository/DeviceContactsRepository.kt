package com.projectpulse.mydialer.data.repository

import android.content.Context
import android.provider.ContactsContract
import com.projectpulse.mydialer.domain.model.SuggestedContact
import com.projectpulse.mydialer.domain.repository.ContactsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeviceContactsRepository(private val context: Context) : ContactsRepository {
    
    private var cachedContacts: List<SuggestedContact>? = null

    override suspend fun getContacts(): List<SuggestedContact> = withContext(Dispatchers.IO) {
        cachedContacts?.let { return@withContext it }

        val contactList = mutableListOf<SuggestedContact>()
        val contentResolver = context.contentResolver
        
        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.TYPE,
            ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI,
            ContactsContract.CommonDataKinds.Phone.STARRED
        )

        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            projection,
            null,
            null,
            null
        )

        cursor?.use {
            val idIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
            val nameIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val numberIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val typeIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE)
            val photoIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI)
            val starredIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.STARRED)

            while (it.moveToNext()) {
                val id = it.getString(idIndex)
                val name = it.getString(nameIndex) ?: "Unknown"
                val number = it.getString(numberIndex) ?: ""
                val type = it.getInt(typeIndex)
                val photoUri = it.getString(photoIndex)
                val isStarred = it.getInt(starredIndex) == 1

                val phoneLabel = when (type) {
                    ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE -> "Mobile"
                    ContactsContract.CommonDataKinds.Phone.TYPE_HOME -> "Home"
                    ContactsContract.CommonDataKinds.Phone.TYPE_WORK -> "Work"
                    else -> "Other"
                }

                contactList.add(
                    SuggestedContact(
                        id = id,
                        name = name,
                        phoneNumber = number,
                        phoneType = phoneLabel,
                        isFavorite = isStarred,
                        avatar = photoUri
                    )
                )
            }
        }
        
        cachedContacts = contactList
        contactList
    }
}
