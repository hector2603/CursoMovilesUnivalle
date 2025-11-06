package com.example.callapi.presentation.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CharacterRepositoryImpl : CharacterRepository {

    private val api: RickAndMortyApi = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RickAndMortyApi::class.java)

    override suspend fun getCharacters(): Result<List<Character>> {
        return try {
            val response = api.getCharacters()
            Result.success(response.results)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}