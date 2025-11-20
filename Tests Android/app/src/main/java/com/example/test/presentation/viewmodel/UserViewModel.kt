package com.example.test.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.data.model.User
import com.example.test.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class UserUiState(
    val users: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isAddingUser: Boolean = false
)

class UserViewModel(
    private val userRepository: UserRepository = UserRepository()
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(UserUiState())
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()
    
    init {
        loadUsers()
    }
    
    fun loadUsers() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            try {
                userRepository.getUsers().collect { users ->
                    _uiState.value = _uiState.value.copy(
                        users = users,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message
                )
            }
        }
    }
    
    fun addUser(name: String, email: String) {
        if (name.isBlank() || email.isBlank()) {
            _uiState.value = _uiState.value.copy(errorMessage = "Name and email cannot be empty")
            return
        }
        
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isAddingUser = true, errorMessage = null)
            try {
                val user = User(name = name, email = email)
                val result = userRepository.addUser(user)
                if (result.isSuccess) {
                    loadUsers()
                } else {
                    _uiState.value = _uiState.value.copy(
                        isAddingUser = false,
                        errorMessage = "Failed to add user"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isAddingUser = false,
                    errorMessage = e.message
                )
            }
        }
    }
    
    fun deleteUser(userId: Int) {
        viewModelScope.launch {
            try {
                val success = userRepository.deleteUser(userId)
                if (success) {
                    loadUsers()
                } else {
                    _uiState.value = _uiState.value.copy(errorMessage = "Failed to delete user")
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        }
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}
