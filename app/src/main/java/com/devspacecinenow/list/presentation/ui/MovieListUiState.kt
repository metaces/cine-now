package com.devspacecinenow.list.presentation.ui

import com.google.gson.annotations.SerializedName

data class MovieListUiState(
    val movies: List<MovieUiData> = emptyList(),
    val isLoading: Boolean = false,
    val error: Boolean = false,
    val isError: String? = "Something went wrong"
)

data class MovieUiData(
    val id: Int,
    val title: String,
    val overview: String,
    val image: String,
)
