package com.example.android_week4.movieapi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPI {
    @GET("movie/top_rated")
    fun getTopRateMovie(
        @Query("page") page: Int
    ): Call<VideoResponse>


    @GET("movie/now_playing")
    fun getNowPlayingMovie(
        @Query("page") page: Int
    ): Call<VideoResponse>
}