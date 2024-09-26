package com.devspacecinenow.list.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.devspacecinenow.common.data.remote.model.MovieDto
import com.devspacecinenow.list.presentation.MovieListViewModel

@Composable
fun MovieListScreen(
    navController: NavHostController,
    viewModel: MovieListViewModel
) {

    val nowPlayingMovies by viewModel.uiNowPlaying.collectAsState()

    val topRatedMovies by viewModel.uiTopRatedMovies.collectAsState()

    val popularMovies by viewModel.uiPopularMovies.collectAsState()

    val upComingMovies by viewModel.uiUpComingMovies.collectAsState()

    MovieListContent(
        topRatedMovies = topRatedMovies,
        nowPlayingMovies = nowPlayingMovies,
        popularMovies = popularMovies,
        upComingMovies = upComingMovies
    ) { itemClicked ->
        navController.navigate(route = "movieDetail" + "/${itemClicked.id}")
    }
}

@Composable
private fun MovieListContent(
    topRatedMovies: MovieListUiState,
    nowPlayingMovies: MovieListUiState,
    upComingMovies: MovieListUiState,
    popularMovies: MovieListUiState,
    onClick: (MovieUiData) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            fontSize = 40.sp,
            fontWeight = FontWeight.SemiBold,
            text = "CineNow"
        )

        MovieSessions(
            label = "Top Rated",
            movieUiState = topRatedMovies,
            onClick = onClick
        )

        MovieSessions(
            label = "Now Playing",
            movieUiState = nowPlayingMovies,
            onClick = onClick
        )

        MovieSessions(
            label = "Popular",
            movieUiState = popularMovies,
            onClick = onClick
        )

        MovieSessions(
            label = "UpComing",
            movieUiState = upComingMovies,
            onClick = onClick
        )
    }
}

@Composable
private fun MoviesUpComing(
    label: String,
    movieList: List<MovieUiData>,
    onClick: (MovieUiData) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            text = label
        )
        Spacer(
            modifier = Modifier
                .size(8.dp)
        )
        MovieList(movies = movieList, onClick = onClick)
    }
}

@Composable
private fun MoviesPopular(
    label: String,
    movieList: List<MovieUiData>,
    onClick: (MovieUiData) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            text = label
        )
        Spacer(
            modifier = Modifier
                .size(8.dp)
        )
        MovieList(movies = movieList, onClick = onClick)
    }
}

@Composable
private fun MovieSessions(
    label: String,
    movieUiState: MovieListUiState,
    onClick: (MovieUiData) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            text = label
        )
        Spacer(
            modifier = Modifier
                .size(8.dp)
        )
        if (movieUiState.isLoading) {
            Text(text = "Loading")
        } else if (movieUiState.error) {
            Text(
                text = movieUiState.isError ?: "Something went wrong",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyLarge
            )
        } else {
            MovieList(movies = movieUiState.movies, onClick = onClick)
        }
    }
}

@Composable
private fun MovieList(
    movies: List<MovieUiData>,
    onClick: (MovieUiData) -> Unit
) {
    LazyRow {
        items(movies) {
            MovieItem(movieUiData = it, onClick = onClick)
        }
    }
}

@Composable
private fun MovieItem(
    movieUiData: MovieUiData,
    onClick: (MovieUiData) -> Unit
) {

    Column(
        modifier = Modifier
            .width(IntrinsicSize.Min)
            .clickable {
                onClick.invoke(movieUiData)
            }
    ) {
        AsyncImage(
            modifier = Modifier
                .padding(end = 4.dp)
                .width(120.dp)
                .height(150.dp),
            contentScale = ContentScale.Crop,
            model = movieUiData.image,
            contentDescription = "${movieUiData.title} Poster image"
        )
        Spacer(
            modifier = Modifier
                .size(4.dp)
        )
        Text(
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            text = movieUiData.title
        )
        Text(
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            text = movieUiData.overview
        )
    }

}