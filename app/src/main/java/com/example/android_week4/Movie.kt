package com.example.android_week4

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Movie (
//    @PrimaryKey(autoGenerate = true) var id_Movie :Int?= null,
    @PrimaryKey
    var id: Double,
    val popularity: Float,
    @SerializedName("vote_count") val vote_count: Int,
    val video: Boolean,
    @SerializedName("poster_path") val poster_path: String,
    val adult: Boolean,
    @SerializedName("backdrop_path") val backdrop_path: String?,
    @SerializedName("original_language") val original_language: String,
    @SerializedName("original_title")  val original_title: String,
//    val genre_ids: List<Int>?,
    val title: String,
    @SerializedName("vote_average") val vote_average: Float,
    val overview: String,
    @SerializedName("release_date") val release_date: String):Parcelable{
//    constructor() : this(null, -1.0,-1,false,"", -1,false,"",
//        "","",-1,"",-1,"", -1)

    fun getposter(): String {
        var url:String = "https://image.tmdb.org/t/p/w500"
//            url = url?.plus(poster_path)
        if(poster_path != null) {
            return url.plus(poster_path)
        }
        if(backdrop_path!=null){
            return url.plus(backdrop_path)
        }
        return ""
    }
    fun getbanner(): String{
        var url: String = "https://image.tmdb.org/t/p/w500"
        if(backdrop_path != null) {
            return url.plus(backdrop_path)
        }
        if(backdrop_path!=null){
            return url.plus(backdrop_path)
        }
        return ""
    }
}