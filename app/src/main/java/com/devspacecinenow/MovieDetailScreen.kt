package com.devspacecinenow

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.devspacecinenow.ui.theme.CineNowTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun MovieDetailSceen(
    movieId: String,
    navHostController: NavHostController
) {
    var movieDto by remember { mutableStateOf<MovieDto?>(null) }
    val apiService = RetrofitClient.retrofitInstance.create(ApiService::class.java)

    apiService.getMovieById(movieId).enqueue(
        object: Callback<MovieDto> {
            override fun onResponse(call: Call<MovieDto>, response: Response<MovieDto>) {
                if (response.isSuccessful) {
                    movieDto = response.body()
                } else {
                    Log.d("MovieDetailScreen", "Error: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<MovieDto>, t: Throwable) {
                Log.d("MovieDetailScreen", "Error: ${t.message}")
            }

        }
    )

    movieDto?.let {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    navHostController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back button")
                }

                Text(
                    modifier = Modifier.padding(start =  4.dp),
                    text = it.title
                )
            }
            MovieDetailContent(it)
        }
    }

}

@Composable
private fun MovieDetailContent(movieDto: MovieDto) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AsyncImage(
            modifier = Modifier.height(200.dp),
            contentScale = ContentScale.Crop,
            model = movieDto.posterFullPath,
            contentDescription = "${movieDto.title} Poster image"
        )
        Text(
            modifier = Modifier.padding(16.dp),
            fontSize = 16.sp,
            text = movieDto.overview)
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieDetailPreview(modifier: Modifier = Modifier) {
   CineNowTheme {
        val movieDto = MovieDto(
            id = 1,
            title = "Movie Title",
            overview = "Movie Overview",
            postPath = "fsdsdfsfsdf",
        )
        MovieDetailContent(movieDto = movieDto)
   }
}