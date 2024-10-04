package com.devspacecinenow.list

import com.devspacecinenow.common.data.local.MovieCategory
import com.devspacecinenow.common.data.model.Movie
import com.devspacecinenow.list.data.local.LocalDataSource

class FakeMovieListLocalDataSource: LocalDataSource {

    var nowPlayingMovies: List<Movie> = emptyList()
    var topRatedMovies: List<Movie> = emptyList()
    var popularMovies: List<Movie> = emptyList()
    var upComingMovies: List<Movie> = emptyList()
    var updateLocalItemsCalled: List<Movie> = listOf(
        Movie(
            id = 1,
            title = "Movie 1",
            overview = "Overview 1",
            image = "image 1",
            category = MovieCategory.NowPlaying.name
        )
    )

    override suspend fun getNowPlayingMovies(): List<Movie> {
        return nowPlayingMovies
    }

    override suspend fun getTopRatedMovies(): List<Movie> {
        return topRatedMovies
    }

    override suspend fun getPopularMovies(): List<Movie> {
        return popularMovies
    }

    override suspend fun getUpComingMovies(): List<Movie> {
        return upComingMovies
    }

    override suspend fun updateLocalItems(movies: List<Movie>): Unit {
        updateLocalItemsCalled = movies
    }
}