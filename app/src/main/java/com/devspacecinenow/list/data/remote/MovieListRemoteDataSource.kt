package com.devspacecinenow.list.data.remote

import android.accounts.NetworkErrorException
import com.devspacecinenow.common.data.local.MovieCategory
import com.devspacecinenow.common.data.model.Movie
import com.devspacecinenow.common.data.remote.model.MovieResponse
import retrofit2.Response

class MovieListRemoteDataSource(
    private val listService: ListService
) {

    suspend fun getNowPlaying(): Result<List<Movie>?> {
        return try {
            val response = listService.getNowPlayingMovies()
            if (response.isSuccessful) {
                val movies = moviesMapped(response, MovieCategory.NowPlaying)
                Result.success(movies)
            } else {
                Result.failure(NetworkErrorException(response.message()))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun getTopRated(): Result<List<Movie>?> {
        return try {
            val response = listService.getTopRatedMovies()
            if (response.isSuccessful) {
                val movies = moviesMapped(response, MovieCategory.TopRated)
                Result.success(movies)
            } else {
                Result.failure(NetworkErrorException(response.message()))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun getPopular(): Result<List<Movie>?> {
        return try {
            val response = listService.getPopularMovies()
            if (response.isSuccessful) {
                val movies = moviesMapped(response, MovieCategory.Popular)
                Result.success(movies)
            } else {
                Result.failure(NetworkErrorException(response.message()))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun getUpComing(): Result<List<Movie>?> {
        return try {
            val response = listService.getUpComingMovies()
            if (response.isSuccessful) {
                val movies = moviesMapped(response, MovieCategory.UpComing)
                Result.success(movies)
            } else {
                Result.failure(NetworkErrorException(response.message()))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    private fun moviesMapped(response: Response<MovieResponse>, category: MovieCategory): List<Movie>? {
        val movies = response.body()?.results?.map {
            Movie(
                id = it.id,
                title = it.title,
                overview = it.overview,
                image = it.posterFullPath,
                category = category.name
            )
        }
        return movies
    }
}