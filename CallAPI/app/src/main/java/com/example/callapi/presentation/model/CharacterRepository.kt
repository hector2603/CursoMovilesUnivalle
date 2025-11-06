package com.example.callapi.presentation.model

interface CharacterRepository {
    suspend fun getCharacters(): Result<List<Character>>
}