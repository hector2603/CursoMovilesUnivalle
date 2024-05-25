package com.example.arquitecturamvvm.httprequest.model


data class MovieDataResponse(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)

