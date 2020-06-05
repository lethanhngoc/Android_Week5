package com.example.android_week4.movieapi

import com.example.android_week4.Movie
import com.google.gson.annotations.SerializedName

data class VideoResponse(
    val page: Int?,
    @SerializedName("total_results") val totalResults: Int?,
    @SerializedName("total_pages") val totalPages: Int?,
    @SerializedName("results") val result: ArrayList<Movie>?
){
    constructor() : this(null,null,null, ArrayList())
}