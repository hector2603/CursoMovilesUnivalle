package com.example.test.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val userRepository: UserRepository = UserRepository()
) : ViewModel() {
    
    private val _counter = MutableStateFlow(0)
    val counter: StateFlow<Int> = _counter.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    fun incrementCounter() {
        _counter.value += 1
    }
    
    fun decrementCounter() {
        _counter.value -= 1
    }
    
    fun resetCounter() {
        _counter.value = 0
    }
    
    fun simulateLoading() {
        viewModelScope.launch {
            _isLoading.value = true
            kotlinx.coroutines.delay(2000)
            _isLoading.value = false
        }
    }
}
