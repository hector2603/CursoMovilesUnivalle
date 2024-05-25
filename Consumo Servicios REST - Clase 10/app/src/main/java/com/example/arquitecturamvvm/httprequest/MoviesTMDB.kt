package com.example.arquitecturamvvm.httprequest
import com.example.arquitecturamvvm.httprequest.model.MovieDataResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesTMDB {

    @GET("/3/search/movie")
    suspend fun getMovies(
        @Query("query") movieQuery: String,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "es"
    ): Response<MovieDataResponse>

}
