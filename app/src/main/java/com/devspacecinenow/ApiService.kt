package com.devspacecinenow

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("now_playing?language=en-US&page=1")
    fun getNowPlayingMovies(): Call<MovieResponse>

    @GET("top_rated?language=en-US&page=1")
    fun getTopRatedMovies(): Call<MovieResponse>
}