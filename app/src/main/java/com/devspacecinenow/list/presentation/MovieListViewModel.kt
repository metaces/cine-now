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
import com.devspacecinenow.list.data.MovieListRepository
import com.devspacecinenow.list.presentation.ui.MovieListUiState
import com.devspacecinenow.list.presentation.ui.MovieUiData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.UnknownHostException

class MovieListViewModel(
    private val repository: MovieListRepository,
) : ViewModel() {
    private val _uiNowPlaying = MutableStateFlow<MovieListUiState>(MovieListUiState())
    val uiNowPlaying: StateFlow<MovieListUiState> = _uiNowPlaying

    private val _uiTopRatedMovies = MutableStateFlow<MovieListUiState>(MovieListUiState())
    val uiTopRatedMovies: StateFlow<MovieListUiState> = _uiTopRatedMovies

    private val _uiPopularMovies = MutableStateFlow<MovieListUiState>(MovieListUiState())
    val uiPopularMovies: StateFlow<MovieListUiState> = _uiPopularMovies

    private val _uiUpComingMovies = MutableStateFlow<MovieListUiState>(MovieListUiState())
    val uiUpComingMovies: StateFlow<MovieListUiState> = _uiUpComingMovies

    init {
        fetchNowPlayingMovies()
        fetchTopRatedMovies()
        fetchPopularMovies()
        fetchUpComingMovies()
    }

    private fun fetchNowPlayingMovies() {
        _uiNowPlaying.value = MovieListUiState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getNowPlaying()
            if (result.isSuccess) {
                val movies = result.getOrNull()?.results
                if (movies != null) {
                    val movieUiDataList = movies.map {
                        MovieUiData(
                            id = it.id,
                            title = it.title,
                            overview = it.overview,
                            image = it.posterFullPath
                        )
                    }
                    _uiNowPlaying.value = MovieListUiState(movies = movieUiDataList)
                }
            } else {
                val ex = result.exceptionOrNull()
                if (ex is UnknownHostException) {
                    _uiNowPlaying.value =
                        MovieListUiState(error = true, isError = "No internet connection")
                } else {
                    _uiNowPlaying.value = MovieListUiState(error = true)
                }
            }
        }
    }

    private fun fetchTopRatedMovies() {
        _uiTopRatedMovies.value = MovieListUiState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getTopRated()
            if (result.isSuccess) {
                val movies = result.getOrNull()?.results
                if (movies != null) {
                    val movieUiDataList = movies.map {
                        MovieUiData(
                            id = it.id,
                            title = it.title,
                            overview = it.overview,
                            image = it.posterFullPath
                        )
                    }
                    _uiTopRatedMovies.value = MovieListUiState(movies = movieUiDataList)
                }
            } else {
                val ex = result.exceptionOrNull()
                if (ex is UnknownHostException) {
                    _uiTopRatedMovies.value =
                        MovieListUiState(error = true, isError = "No internet connection")
                } else {
                    _uiTopRatedMovies.value = MovieListUiState(error = true)
                }
            }
        }
    }

    private fun fetchPopularMovies() {
        _uiPopularMovies.value = MovieListUiState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getPopular()
            if (result.isSuccess) {
                val movies = result.getOrNull()?.results
                if (movies != null) {
                    val movieUiDataList = movies.map {
                        MovieUiData(
                            id = it.id,
                            title = it.title,
                            overview = it.overview,
                            image = it.posterFullPath
                        )
                    }
                    _uiPopularMovies.value = MovieListUiState(movies = movieUiDataList)
                }
            } else {
                val ex = result.exceptionOrNull()
                if (ex is UnknownHostException) {
                    _uiPopularMovies.value =
                        MovieListUiState(error = true, isError = "No internet connection")
                } else {
                    _uiPopularMovies.value = MovieListUiState(error = true)
                }

            }
        }
    }

    private fun fetchUpComingMovies() {
        _uiUpComingMovies.value = MovieListUiState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getUpComing()
            if (result.isSuccess) {
                val movies = result.getOrNull()?.results
                if (movies != null) {
                    val movieUiDataList = movies.map {
                        MovieUiData(
                            id = it.id,
                            title = it.title,
                            overview = it.overview,
                            image = it.posterFullPath
                        )
                    }
                    _uiUpComingMovies.value = MovieListUiState(movies = movieUiDataList)
                }
            } else {
                val ex = result.exceptionOrNull()
                if (ex is UnknownHostException) {
                    _uiUpComingMovies.value =
                        MovieListUiState(error = true, isError = "No internet connection")
                } else {
                    _uiUpComingMovies.value = MovieListUiState(error = true)
                }
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
                    repository = MovieListRepository(listService)
                ) as T
            }
        }
    }
}