package com.devspacecinenow.list

import com.devspacecinenow.common.data.local.MovieCategory
import com.devspacecinenow.common.data.model.Movie
import com.devspacecinenow.list.data.MovieListRepository
import com.devspacecinenow.list.data.local.MovieListLocalDataSource
import com.devspacecinenow.list.data.remote.MovieListRemoteDataSource
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.net.UnknownHostException

class MovieListRepositoryTest {

    private val localDataSource = FakeMovieListLocalDataSource()
    private val remoteDataSource: MovieListRemoteDataSource = mock()

    private val underTest by lazy {
        MovieListRepository(
            localDataSource = localDataSource,
            remoteDataSource = remoteDataSource
        )
    }

    @Test
    fun `Given no internet connection When getting now playing movies Then return local data`() {
        runTest {
            // Given
            val localData = listOf(Movie(
                id = 1,
                title = "Movie 1",
                overview = "Overview 1",
                image = "image 1",
                category = MovieCategory.NowPlaying.name
            ))
            whenever(remoteDataSource.getNowPlaying()).thenReturn(Result.failure(
                UnknownHostException("No internet")
            ))
            localDataSource.nowPlayingMovies = localData
//            whenever(localDataSource.getNowPlayingMovies()).thenReturn(localData)

            // When
            val result = underTest.getNowPlaying()

            // Then
            val expected = Result.success(localData)
            assertEquals(expected, result)

        }
    }

    @Test
    fun `Given no internet connection and no local data When getting now playing movies Then return remote result`() {
        runTest {
            // Given
            val remoteResult = Result.failure<List<Movie>>(UnknownHostException("No internet"))
            whenever(remoteDataSource.getNowPlaying()).thenReturn(remoteResult)
            localDataSource.nowPlayingMovies = emptyList()
//            whenever(localDataSource.getNowPlayingMovies()).thenReturn(emptyList())

            // When
            val result = underTest.getNowPlaying()

            // Then
            val expected = remoteResult
            assertEquals(expected, result)

        }
    }

    @Test
    fun `Given remote connection When getting now playing movies Then update local data`() {
        runTest {
            // Given
            val list = listOf(Movie(
                id = 1,
                title = "Movie 1",
                overview = "Overview 1",
                image = "image 1",
                category = MovieCategory.NowPlaying.name
            ))

            val remoteResult = Result.success(list)
            whenever(remoteDataSource.getNowPlaying()).thenReturn(remoteResult)
            localDataSource.nowPlayingMovies = list
//            whenever(localDataSource.getNowPlayingMovies()).thenReturn(list)

            // When
            val result = underTest.getNowPlaying()

            // Then
            val expected = Result.success(list)
            assertEquals(expected, result)
            assertEquals(list, localDataSource.updateLocalItemsCalled)
        }
    }

    @Test
    fun `Given no internet connection and no local throw exception When getting now playing movies Then return failure`() {
        runTest {
            // Given
            val remoteResult = Result.failure<List<Movie>>(RuntimeException("Network error"))
            whenever(remoteDataSource.getNowPlaying()).thenReturn(remoteResult)
            localDataSource.nowPlayingMovies = emptyList()
//            whenever(localDataSource.getNowPlayingMovies()).thenReturn(emptyList())

            // When
            val result = underTest.getNowPlaying()

            // Then
            val expected = remoteResult
            assertEquals(expected, result)

        }
    }
}