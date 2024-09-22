package com.devspacecinenow.list.data

import android.accounts.NetworkErrorException
import android.util.Log
import com.devspacecinenow.common.model.MovieResponse
import com.devspacecinenow.list.presentation.ui.MovieListUiState
import com.devspacecinenow.list.presentation.ui.MovieUiData
import java.net.UnknownHostException

class MovieListRepository(
    private val listService: ListService
) {
    suspend fun getNowPlaying(): Result<MovieResponse?> {
        return try {
            val response = listService.getNowPlayingMovies()
            if (response.isSuccessful) {
                Result.success(response.body())
            } else {
                Result.failure(NetworkErrorException(response.message()))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun getTopRated(): Result<MovieResponse?> {
        return try {
            val response = listService.getTopRatedMovies()
            if (response.isSuccessful) {
                Result.success(response.body())
            } else {
                Result.failure(NetworkErrorException(response.message()))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun getPopular(): Result<MovieResponse?> {
        return try {
            val response = listService.getPopularMovies()
            if (response.isSuccessful) {
                Result.success(response.body())
            } else {
                Result.failure(NetworkErrorException(response.message()))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun getUpComing(): Result<MovieResponse?> {
        return try {
            val response = listService.getUpComingMovies()
            if (response.isSuccessful) {
                Result.success(response.body())
            } else {
                Result.failure(NetworkErrorException(response.message()))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}