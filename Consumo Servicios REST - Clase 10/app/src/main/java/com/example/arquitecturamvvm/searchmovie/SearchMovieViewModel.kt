package com.example.arquitecturamvvm.searchmovie

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arquitecturamvvm.httprequest.MoviesTMDB
import com.example.arquitecturamvvm.httprequest.RetroFitHelper
import com.example.arquitecturamvvm.httprequest.model.MovieDataResponse
import kotlinx.coroutines.launch
class SearchMovieViewModel : ViewModel() {

    private val moviesTMDB: MoviesTMDB = RetroFitHelper.getInstance()

    private var _searchResults = MutableLiveData<MovieDataResponse?>(null)
    val searchResults : LiveData<MovieDataResponse?> = _searchResults
    fun searchMovies(query: String) {
        viewModelScope.launch {
            val result = moviesTMDB.getMovies(
                movieQuery = query,
                apiKey = "c2f562e62d9cdd77bc8bc3325761193f"
            )
            if (result.isSuccessful) {
                _searchResults.value = result.body()
            } else {
                Log.e("SearchMovieViewModel", "Error: ${result.errorBody()?.string()}")
            }
        }
    }
}