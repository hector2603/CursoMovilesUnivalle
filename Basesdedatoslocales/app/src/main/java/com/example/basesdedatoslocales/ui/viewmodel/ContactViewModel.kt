package com.example.basesdedatoslocales.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.basesdedatoslocales.data.local.AppDatabase
import com.example.basesdedatoslocales.data.local.Contact
import com.example.basesdedatoslocales.data.local.ContactDao
import com.example.basesdedatoslocales.data.local.SharedPreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

data class ContactUiState(
    val contacts: List<Contact> = emptyList(),
    val favorites: Map<Int, Boolean> = emptyMap()
)

class ContactViewModel(application: Application) : AndroidViewModel(application) {

    private val contactDao: ContactDao
    private val sharedPreferencesManager: SharedPreferencesManager

    private val _uiState = MutableStateFlow(ContactUiState())
    val uiState: StateFlow<ContactUiState> = _uiState.asStateFlow()

    init {
        val context = application.applicationContext
        contactDao = AppDatabase.getDatabase(context).contactDao()
        sharedPreferencesManager = SharedPreferencesManager(context)

        viewModelScope.launch {
            contactDao.getAllContacts().map { contacts ->
                val favorites = contacts.associate { it.id to sharedPreferencesManager.isFavorite(it.id) }
                ContactUiState(contacts, favorites)
            }.collect { state ->
                _uiState.value = state
            }
        }
    }

    fun addContact(name: String, phone: String) {
        viewModelScope.launch {
            contactDao.insertContact(Contact(name = name, phone = phone))
        }
    }

    fun deleteContact(contactId: Int) {
        viewModelScope.launch {
            contactDao.deleteContact(contactId)
        }
    }

    fun toggleFavorite(contactId: Int) {
        val isCurrentlyFavorite = sharedPreferencesManager.isFavorite(contactId)
        sharedPreferencesManager.setFavorite(contactId, !isCurrentlyFavorite)
        // Refresh the UI state to reflect the change
        viewModelScope.launch {
            val contacts = _uiState.value.contacts
            val favorites = contacts.associate { it.id to sharedPreferencesManager.isFavorite(it.id) }
            _uiState.value = _uiState.value.copy(favorites = favorites)
        }
    }
}
