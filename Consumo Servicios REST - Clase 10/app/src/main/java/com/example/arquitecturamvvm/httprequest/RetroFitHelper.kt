package com.example.arquitecturamvvm.httprequest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroFitHelper {

    private const val URL = "https://api.themoviedb.org/"

    private val retrofit =
        Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build()

    fun getInstance():MoviesTMDB{
        return retrofit.create(MoviesTMDB::class.java)
    }

}