package com.devspacecinenow.list.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspacecinenow.common.data.RetrofitClient
import com.devspacecinenow.common.model.MovieDto
import com.devspacecinenow.common.model.MovieResponse
import com.devspacecinenow.list.data.ListService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
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
        viewModelScope.launch(Dispatchers.IO) {
            val response = listService.getNowPlayingMovies()
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
    }

    private fun fetchTopRatedMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = listService.getTopRatedMovies()
            if (response.isSuccessful) {
                val movies = response.body()?.results ?: emptyList()
                if (movies.isNotEmpty()) {
                    _uiTopRatedMovies.value = movies
                }
            } else {
                Log.d("MainActivity callTopRatedMovies", "Error onResponse: ${response.errorBody()}")
            }
        }
    }

    private fun fetchPopularMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = listService.getPopularMovies()
            if (response.isSuccessful) {
                val movies = response.body()?.results ?: emptyList()
                if (movies.isNotEmpty()) {
                    _uiPopularMovies.value = movies
                }
            } else {
                Log.d("MainActivity callPopularMovies", "Error onResponse: ${response.errorBody()}")
            }
        }
    }

    private fun fetchUpComingMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = listService.getUpComingMovies()
            if (response.isSuccessful) {
                val movies = response.body()?.results ?: emptyList()
                if (movies.isNotEmpty()) {
                    _uiUpComingMovies.value = movies
                }
            } else {
                Log.d("MainActivity callUpComingMovies", "Error onResponse: ${response.errorBody()}")
            }
        }
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