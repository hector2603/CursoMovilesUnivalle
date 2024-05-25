package com.example.arquitecturamvvm.searchmovie

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.arquitecturamvvm.ui.theme.Purple80
import androidx.compose.runtime.livedata.observeAsState
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.arquitecturamvvm.httprequest.model.Movie

@Composable
fun SearchMovie(navController: NavHostController, searchMovieViewModel: SearchMovieViewModel) {

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp), color = Purple80
    ) {
        Column {
            var searchText by remember { mutableStateOf("") }

            TextField(
                value = searchText,
                onValueChange = { newValue ->
                    searchText = newValue
                    searchMovieViewModel.searchMovies(newValue)
                },
                label = { Text("Search Movie") },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(0.8f)
            )

            val searchResults by searchMovieViewModel.searchResults.observeAsState(null)
            searchResults?.let { movieDataResponse ->
                LazyColumn {
                    items(movieDataResponse.results.size) { indexMovie ->
                        val movie = movieDataResponse.results[indexMovie]
                        MovieItem(movie)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieItem(movie: Movie) {
    Row(modifier = Modifier.padding(16.dp)){
        GlideImage(
            model ="https://image.tmdb.org/t/p/w500${movie.poster_path}",
            contentDescription = movie.title,
            modifier = Modifier.width(100.dp)
        )
        Text(text = movie.title, modifier = Modifier.padding(16.dp))
    }
}