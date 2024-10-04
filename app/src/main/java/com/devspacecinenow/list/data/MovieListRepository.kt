package com.devspacecinenow.list.data

import android.accounts.NetworkErrorException
import com.devspacecinenow.common.data.model.Movie
import com.devspacecinenow.list.data.local.LocalDataSource
import com.devspacecinenow.list.data.remote.MovieListRemoteDataSource

class MovieListRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: MovieListRemoteDataSource
) {
    suspend fun getNowPlaying(): Result<List<Movie>?> {
        return try {
            val remoteResult = remoteDataSource.getNowPlaying()
            if (remoteResult.isSuccess) {
                val movies = remoteResult.getOrNull() ?: emptyList()
                if (movies.isNotEmpty()) {
                    localDataSource.updateLocalItems(movies)
                }
                // Source of truth
                Result.success(localDataSource.getNowPlayingMovies())
            } else {
                val localResult = localDataSource.getNowPlayingMovies()
                if (localResult.isEmpty()) {
                    return Result.failure(remoteResult.exceptionOrNull() ?: NetworkErrorException())
                } else {
                    Result.success(localResult)
                }
            }

        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    suspend fun getTopRated(): Result<List<Movie>?> {
        return try {
            val remoteResult = remoteDataSource.getTopRated()
            if (remoteResult.isSuccess) {
                val movies = remoteResult.getOrNull() ?: emptyList()
                if (movies.isNotEmpty()) {
                    localDataSource.updateLocalItems(movies)
                }
                Result.success(localDataSource.getTopRatedMovies())
            } else {
                val localResult = localDataSource.getTopRatedMovies()
                if (localResult.isEmpty()) {
                    return Result.failure(remoteResult.exceptionOrNull() ?: NetworkErrorException())
                } else {
                    Result.success(localResult)
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    suspend fun getPopular(): Result<List<Movie>?> {
        return try {
            val remoteResult = remoteDataSource.getPopular()
            if (remoteResult.isSuccess) {
                val movies = remoteResult.getOrNull() ?: emptyList()
                if (movies.isNotEmpty()) {
                    localDataSource.updateLocalItems(movies)
                }
                Result.success(localDataSource.getPopularMovies())
            } else {
                val localResult = localDataSource.getPopularMovies()
                if (localResult.isEmpty()) {
                    return Result.failure(remoteResult.exceptionOrNull() ?: NetworkErrorException())
                } else {
                    Result.success(localResult)
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    suspend fun getUpComing(): Result<List<Movie>?> {
        return try {
            val remoteResult = remoteDataSource.getUpComing()
            if (remoteResult.isSuccess) {
                val movies = remoteResult.getOrNull() ?: emptyList()
                if (movies.isNotEmpty()) {
                    localDataSource.updateLocalItems(movies)
                }
                Result.success(localDataSource.getUpComingMovies())
            } else {
                val localResult = localDataSource.getUpComingMovies()
                if (localResult.isEmpty()) {
                    return Result.failure(remoteResult.exceptionOrNull() ?: NetworkErrorException())
                } else {
                    Result.success(localResult)
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }
}