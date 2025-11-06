package com.example.callapi.presentation.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.callapi.presentation.model.Character
import com.example.callapi.presentation.model.CharacterRepository
import com.example.callapi.presentation.model.CharacterRepositoryImpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class CharactersUiState(
    val isLoading: Boolean = false,
    val characters: List<Character> = emptyList(),
    val errorMessage: String? = null
)

class CharactersViewModel(
    private val repository: CharacterRepository = CharacterRepositoryImpl()
) : ViewModel() {

    private val _uiState = MutableStateFlow(CharactersUiState())
    val uiState: StateFlow<CharactersUiState> = _uiState.asStateFlow()

    init{
        fetchCharacters()
    }

    /*private fun loadMockCharacters(){
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            delay(1500)

            _uiState.value = _uiState.value.copy(
                isLoading = false,
                characters = MockData.characters
            )
        }
    }*/

    private fun fetchCharacters(){
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            repository.getCharacters()
                .onSuccess { characters ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        characters = characters
                    )
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = exception.message ?: "Error desconocido"
                    )
                }
        }
    }

    fun retryLoad() {
        fetchCharacters()
    }
}