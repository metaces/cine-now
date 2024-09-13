package com.devspacecinenow.list.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspacecinenow.common.data.RetrofitClient
import com.devspacecinenow.common.model.MovieDto
import com.devspacecinenow.common.model.MovieResponse
import com.devspacecinenow.list.data.ListService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieListViewModel(
    private val listService: ListService,
) : ViewModel() {
    private val _uiNowPlaying = MutableStateFlow<List<MovieDto>>(emptyList())
    val uiNowPlaying: StateFlow<List<MovieDto>> = _uiNowPlaying
    private val _uiTopRatedMovies = MutableStateFlow<List<MovieDto>>(emptyList())
    val uiTopRatedMovies: StateFlow<List<MovieDto>> = _uiTopRatedMovies
    private val _uiPopularMovies = MutableStateFlow<List<MovieDto>>(emptyList())
    val uiPopularMovies: StateFlow<List<MovieDto>> = _uiPopularMovies
    private val _uiUpComingMovies = MutableStateFlow<List<MovieDto>>(emptyList())
    val uiUpComingMovies: StateFlow<List<MovieDto>> = _uiUpComingMovies

    init {
        fetchNowPlayingMovies()

        fetchTopRatedMovies()

        fetchPopularMovies()

        fetchUpComingMovies()
    }

    private fun fetchNowPlayingMovies() {
        listService.getNowPlayingMovies().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>,
                response: Response<MovieResponse>
            ) {
                if (response.isSuccessful) {
                    val movies = response.body()?.results ?: emptyList()
                    if (movies.isNotEmpty()) {
                        _uiNowPlaying.value = movies
                    }
                } else {
                    Log.d(
                        "MovieListViewModel callUpComingMovies",
                        "Error onResponse: ${response.errorBody()}"
                    )
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d("MovieListViewModel callUpComingMovies", "Error onFailure: ${t.message}")
            }

        })
    }

    private fun fetchTopRatedMovies() {
        listService.getTopRatedMovies().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>,
                response: Response<MovieResponse>
            ) {
                if (response.isSuccessful) {
                    val movies = response.body()?.results ?: emptyList()
                    if (movies.isNotEmpty()) {
                        _uiTopRatedMovies.value = movies
                    }
                } else {
                    Log.d("MovieListViewModel callUpcomingMovies", "Error onResponse: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d("MovieListViewModel callUpcomingMovies", "Error onFailure: ${t.message}")
            }

        })
    }

    private fun fetchPopularMovies() {
        listService.getPopularMovies().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>,
                response: Response<MovieResponse>
            ) {
                if (response.isSuccessful) {
                    val movies = response.body()?.results ?: emptyList()
                    if (movies.isNotEmpty()) {
                        _uiPopularMovies.value = movies
                    }
                } else {
                    Log.d("MainActivity callPopularMovies", "Error onResponse: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d("MainActivity callPopularMovies", "Error onFailure: ${t.message}")
            }

        })
    }

    private fun fetchUpComingMovies() {
        listService.getUpComingMovies().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>,
                response: Response<MovieResponse>
            ) {
                if (response.isSuccessful) {
                    val movies = response.body()?.results ?: emptyList()
                    if (movies.isNotEmpty()) {
                        _uiUpComingMovies.value = movies
                    }
                } else {
                    Log.d("MainActivity callUpComingMovies", "Error onResponse: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d("MainActivity callUpComingMovies", "Error onFailure: ${t.message}")
            }

        })
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val listService = RetrofitClient.retrofitInstance.create(ListService::class.java)
                return MovieListViewModel(
                    listService
                ) as T
            }
        }
    }
}