package com.devspacecinenow.list

import app.cash.turbine.test
import com.devspacecinenow.common.data.local.MovieCategory
import com.devspacecinenow.common.data.model.Movie
import com.devspacecinenow.list.data.MovieListRepository
import com.devspacecinenow.list.presentation.MovieListViewModel
import com.devspacecinenow.list.presentation.ui.MovieListUiState
import com.devspacecinenow.list.presentation.ui.MovieUiData
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

class MovieListViewModelTest {

    private val repository: MovieListRepository = mock()
    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler())

    private val underTest by lazy {
        MovieListViewModel(repository, testDispatcher)
    }

    @Test
    fun `Given fresh view model When collecting nowPlaying Then assert expected value`() {
        runTest {
            // Given
            val movies = listOf<Movie>(
                Movie(
                    id = 1,
                    title = "Movie 1",
                    overview = "Overview 1",
                    image = "image 1",
                    category = MovieCategory.NowPlaying.name
                )
            )
            whenever(repository.getNowPlaying()).thenReturn(Result.success(movies))

            // When
            underTest.uiNowPlaying.test {
                // Then
                val expected = MovieListUiState(
                    movies = listOf(
                        MovieUiData(
                            id = 1,
                            title = "Movie 1",
                            overview = "Overview 1",
                            image = "image 1"
                        )
                    )
                )
                assertEquals(expected, awaitItem())
            }
        }
    }

    @Test
    fun `Given fresh view model When collecting topRated Then assert expected value`() {
        runTest {
            // Given
            val movies = listOf<Movie>(
                Movie(
                    id = 1,
                    title = "Movie 1",
                    overview = "Overview 1",
                    image = "image 1",
                    category = MovieCategory.TopRated.name
                )
            )
            whenever(repository.getTopRated()).thenReturn(Result.success(movies))

            // When
            underTest.uiTopRatedMovies.test {
                // Then
                val expected = MovieListUiState(
                    movies = listOf(
                        MovieUiData(
                            id = 1,
                            title = "Movie 1",
                            overview = "Overview 1",
                            image = "image 1"
                        )
                    )
                )
                assertEquals(expected, awaitItem())
            }
        }
    }

    @Test
    fun `Given fresh view model When collecting topRated Then assert loading state`() {
        runTest {
            // Given
            // When
            val result = MovieListUiState(isLoading = true)
            // Then
            val expected = MovieListUiState(
                isLoading = true
                )
            assertEquals(expected, result)

        }
    }

}