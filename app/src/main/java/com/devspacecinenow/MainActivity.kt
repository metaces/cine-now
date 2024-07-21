package com.devspacecinenow

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.devspacecinenow.ui.theme.CineNowTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CineNowTheme {
                var nowPlayingMovies by remember {
                    mutableStateOf<List<MovieDto>>(emptyList())
                }
                var topRatedMovies by remember {
                    mutableStateOf<List<MovieDto>>(emptyList())
                }
                var popularMovies by remember {
                    mutableStateOf<List<MovieDto>>(emptyList())
                }
                var upComingMovies by remember {
                    mutableStateOf<List<MovieDto>>(emptyList())
                }

                val apiService = RetrofitClient.retrofitInstance.create(ApiService::class.java)
                val callNowPlayingMovies = apiService.getNowPlayingMovies()
                val callTopRatedMovies = apiService.getTopRatedMovies()
                val callPopularMovies = apiService.getPopularMovies()
                val callUpComingMovies = apiService.getUpComingMovies()

                callNowPlayingMovies.enqueue(object : Callback<MovieResponse> {
                    override fun onResponse(
                        call: Call<MovieResponse>,
                        response: Response<MovieResponse>
                    ) {
                        if (response.isSuccessful) {
                            val movies = response.body()?.results ?: emptyList()
                            if (movies.isNotEmpty()) {
                                nowPlayingMovies = movies
                            }
                        } else {
                            Log.d("MainActivity callNowPlayingMovies", "Error onResponse: ${response.errorBody()}")
                        }

                    }

                    override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                        Log.d("MainActivity callNowPlayingMovies", "Error onFailure: ${t.message}")
                    }

                })

                callTopRatedMovies.enqueue(object : Callback<MovieResponse>{
                    override fun onResponse(
                        call: Call<MovieResponse>,
                        response: Response<MovieResponse>
                    ) {
                        if (response.isSuccessful) {
                            val movies = response.body()?.results ?: emptyList()
                            if (movies.isNotEmpty()) {
                                topRatedMovies = movies
                            }
                        } else {
                            Log.d("MainActivity callUpcomingMovies", "Error onResponse: ${response.errorBody()}")
                        }
                    }

                    override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                        Log.d("MainActivity callUpcomingMovies", "Error onFailure: ${t.message}")
                    }

                })

                callPopularMovies.enqueue(object : Callback<MovieResponse>{
                    override fun onResponse(
                        call: Call<MovieResponse>,
                        response: Response<MovieResponse>
                    ) {
                        if (response.isSuccessful) {
                            val movies = response.body()?.results ?: emptyList()
                            if (movies.isNotEmpty()) {
                                popularMovies = movies
                            }
                        } else {
                            Log.d("MainActivity callPopularMovies", "Error onResponse: ${response.errorBody()}")
                        }
                    }

                    override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                        Log.d("MainActivity callPopularMovies", "Error onFailure: ${t.message}")
                    }

                })
                callUpComingMovies.enqueue(object : Callback<MovieResponse>{
                    override fun onResponse(
                        call: Call<MovieResponse>,
                        response: Response<MovieResponse>
                    ) {
                        if (response.isSuccessful) {
                            val movies = response.body()?.results ?: emptyList()
                            if (movies.isNotEmpty()) {
                                upComingMovies = movies
                            }
                        } else {
                            Log.d("MainActivity callUpComingMovies", "Error onResponse: ${response.errorBody()}")
                        }
                    }

                    override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                        Log.d("MainActivity callUpComingMovies", "Error onFailure: ${t.message}")
                    }

                })
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            fontSize = 40.sp,
                            fontWeight = FontWeight.SemiBold,
                            text = "CineNow"
                        )

                        MovieSessions(
                            label = "Top Rated",
                            movieList = topRatedMovies,
                            onClick = {
                                Log.d("MainActivity", "Movie clicked: ${it.title}")
                            }
                        )

                        MovieSessions(
                            label = "Now Playing",
                            movieList = nowPlayingMovies,
                            onClick = {
                                Log.d("MainActivity", "Movie clicked: ${it.title}")
                            }
                        )

                        MovieSessions(
                            label = "Popular",
                            movieList = popularMovies,
                            onClick = {
                                Log.d("MainActivity", "Movie clicked: ${it.title}")
                            }
                        )

                        MovieSessions(
                            label = "UpComing",
                            movieList = upComingMovies,
                            onClick = {
                                Log.d("MainActivity", "Movie clicked: ${it.title}")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MoviesUpComing(
    label: String,
    movieList: List<MovieDto>,
    onClick: (MovieDto) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            text = label
        )
        Spacer(
            modifier = Modifier
                .size(8.dp)
        )
        MovieList(movies = movieList, onClick = onClick)
    }
}

@Composable
fun MoviesPopular(
    label: String,
    movieList: List<MovieDto>,
    onClick: (MovieDto) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            text = label
        )
        Spacer(
            modifier = Modifier
                .size(8.dp)
        )
        MovieList(movies = movieList, onClick = onClick)
    }
}

@Composable
fun MovieSessions(
    label: String,
    movieList: List<MovieDto>,
    onClick: (MovieDto) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            text = label
        )
        Spacer(
            modifier = Modifier
                .size(8.dp)
        )
        MovieList(movies = movieList, onClick = onClick)
    }
}

@Composable
fun MovieList(
    movies: List<MovieDto>,
    onClick: (MovieDto) -> Unit
) {
    LazyRow {
        items(movies) {
            MovieItem(movieDto = it, onClick = onClick)
        }
    }
}

@Composable
fun MovieItem(
    movieDto: MovieDto,
    onClick: (MovieDto) -> Unit
) {

    Column(
        modifier = Modifier
            .width(IntrinsicSize.Min)
            .clickable {
                onClick.invoke(movieDto)
            }
    ) {
        AsyncImage(
            modifier = Modifier
                .padding(end = 4.dp)
                .width(120.dp)
                .height(150.dp),
            contentScale = ContentScale.Crop,
            model = movieDto.posterFullPath,
            contentDescription = "${movieDto.title} Poster image"
        )
        Spacer(
            modifier = Modifier
                .size(4.dp)
        )
        Text(
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            text = movieDto.title
        )
        Text(
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            text = movieDto.overview
        )
    }

}
