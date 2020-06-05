package com.example.android_week4.movieapi

import android.util.Log
import com.example.android_week4.Adapter.MovieAdapter
import com.example.android_week4.Fragment.NowPlaying
import com.example.android_week4.Fragment.TopRating
import com.example.android_week4.MainActivity
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieModel {

    companion object {
        var Instance = MovieModel()
    }

    fun getTopRatingMovie(page: Int = 1) {
        var respTopRating = VideoResponse()
        MovieService.getInstance().getApi().getTopRateMovie(page = page).enqueue(object :
            Callback<VideoResponse> {
            override fun onFailure(call: Call<VideoResponse>?, t: Throwable?) {
                //todo something
                Log.e("MainActivity", "Problem calling Github API {${t?.message}}")
            }

            override fun onResponse(
                call: Call<VideoResponse>?,
                response: Response<VideoResponse>?
            ) {
                response?.let {
                    respTopRating = it.body()!!
                    //TopRating.Instance.setDataOnChanges(it.body()!!.result!!)
                    TopRating.movieAdapter?.updateMovie(respTopRating.result!!)
                    TopRating.movieGridAdapter?.updateMovie(respTopRating.result!!)
                    Log.e(MainActivity.TAG, "ok ok ok {${respTopRating.result}}")
                }
            }
        })
        Log.e(MainActivity.TAG, "get top rate end{${respTopRating.page}}")
    }

    fun getNowPlayingMovie(page: Int = 1) {
        var respNowPlaying = VideoResponse()
        MovieService.getInstance().getApi().getNowPlayingMovie(page = page).enqueue(object :
            Callback<VideoResponse> {
            override fun onFailure(call: Call<VideoResponse>?, t: Throwable?) {
                //todo something
                Log.e("MainActivity", "Problem calling Github API {${t?.message}}")
            }

            override fun onResponse(
                call: Call<VideoResponse>?,
                response: Response<VideoResponse>?
            ) {
                response?.let {
                    respNowPlaying = it.body()!!
//                    NowPlaying.Instance.setDataOnChanges(it.body()!!.result!!)
//                    NowPlaying.movieAdapter?.notifyDataSetChanged()

                    NowPlaying.movieAdapter?.updateMovie(respNowPlaying.result!!)
                    NowPlaying.movieGridAdapter?.updateMovie(respNowPlaying.result!!)
                    Log.e(MainActivity.TAG, "ok ok ok ok ok {${respNowPlaying.result}}")
                }
            }
        })
        Log.e(MainActivity.TAG, "ok end{${respNowPlaying.page}}")
    }

}
