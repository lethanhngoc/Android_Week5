package com.example.android_week4

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.example.android_week4.Movie
import kotlinx.android.synthetic.main.activity_profile.*
import java.lang.Exception

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        init()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun init() {
        val toolbar1 = findViewById(R.id.toolbar1) as Toolbar?
        setSupportActionBar(toolbar1)

        val actionbar = supportActionBar
        actionbar?.title = "Movies"
        actionbar?.elevation = 4.0F
        actionbar?.setDisplayHomeAsUpEnabled(true)
        getAndDisplayData()
    }

    private fun getAndDisplayData() {
        val data = intent.extras?.getParcelable<Movie>("crrmovie")

        if (data != null) {
            val title = data.title
            val overview = data.overview
            val poster = data.getposter()
            val banner = data.getbanner()
            val rating = data.vote_average
            val releaseDate = data.release_date

            textviewtitlemovie.text = title
            textviewoverview.text = overview
            tvName.text = title
            tvdescription.text = overview
            rBar.rating = rating / 2
            rating3.text = rating.toString()
            releasedate.text = releaseDate
            if (poster != "") {
                try {
                    println("BANNER: $poster")
                    Glide.with(this)
                        .load(poster)
                        .centerCrop()
                        .into(imageviewposter)
                }catch(e:Exception){
                }
            }
            if (banner != "") {
                println("BANNER: $banner")
                try {
                    Glide.with(this)
                        .load(banner)
                        .centerCrop()
                        .into(imageviewbackgroundmovie)
                    Glide.with(this)
                        .load(banner)
                        .centerCrop()
                        .into(ivbanner)
                }catch (e:Exception){

                }
            }
        }
    }
}