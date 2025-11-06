package com.example.callapi.presentation.model

import retrofit2.http.GET

interface RickAndMortyApi {

    @GET("character")
    suspend fun getCharacters(): CharacterResponse

}