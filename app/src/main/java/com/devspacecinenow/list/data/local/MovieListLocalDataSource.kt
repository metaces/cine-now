package com.devspacecinenow.list.data.local

import com.devspacecinenow.common.data.local.MovieCategory
import com.devspacecinenow.common.data.local.MovieDao
import com.devspacecinenow.common.data.local.MovieEntity
import com.devspacecinenow.common.data.model.Movie

class MovieListLocalDataSource(
    private val dao: MovieDao
): LocalDataSource {

    override suspend fun getNowPlayingMovies(): List<Movie> {
        return getMoviesByCategory(MovieCategory.NowPlaying)
    }

    override suspend fun getTopRatedMovies(): List<Movie> {
        return getMoviesByCategory(MovieCategory.TopRated)
    }

    override suspend fun getPopularMovies(): List<Movie> {
        return getMoviesByCategory(MovieCategory.Popular)
    }

    override suspend fun getUpComingMovies(): List<Movie> {
        return getMoviesByCategory(MovieCategory.UpComing)
    }

    private suspend fun getMoviesByCategory(category: MovieCategory): List<Movie> {
        val entities = dao.getMoviesByCategory(category.name)
        return entities.map {
            Movie(
                id = it.id,
                title = it.title,
                overview = it.overview,
                image = it.image,
                category = it.category
            )
        }
    }

    override suspend fun updateLocalItems(movies: List<Movie>) {
        val entities = movies.map {
            MovieEntity(
                id = it.id,
                title = it.title,
                overview = it.overview,
                category = it.category,
                image = it.image
            )
        }
        dao.insertAll(entities)
    }
}