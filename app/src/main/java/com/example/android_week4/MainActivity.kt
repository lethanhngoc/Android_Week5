package com.example.android_week4

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment

import com.example.android_week4.Fragment.MyFavorite
import com.example.android_week4.Fragment.NowPlaying
import com.example.android_week4.Fragment.TopRating
import com.example.android_week4.RoomPersistence.AppDatabase
import com.example.android_week4.RoomPersistence.MovieDAO
import com.example.android_week4.movieapi.MovieModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), NowPlaying.ListenerFromNowPlaingFragment , TopRating.ListenerFromTopRatingFragment{
    var favoriteMoviesList = ArrayList<Movie>()
    lateinit var dao :MovieDAO

    companion object{
        val TAG = MainActivity::class.java.simpleName
        const val URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "7519cb3f829ecd53bd9b7007076dbe23"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRoomDatabase()
        init()
        getMovies()
    }
    private fun initRoomDatabase() {
        val db = AppDatabase.invoke(this)
        dao = db.movieDAO()
    }
    private fun getMovies() {
        val movies = dao.getAll() // get Students from ROOM database
        this.favoriteMoviesList.addAll(movies) // add to student list
    }
    private  fun init(){

        val preference = getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

        val bottomNavigation : BottomNavigationView = findViewById(R.id.navigationView)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        supportActionBar?.hide()


        val tab : Int = preference.getInt("KEY-TAB",0)
        when(tab){
            0 ->{
                openFragment(NowPlaying())
            }
            1 ->{
                openFragment(TopRating())
            }
            2 ->{
                openFragment(MyFavorite())
            }
        }

    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){
            R.id.item_nowPlaying ->{
//                viewPager.currentItem = 0
                val preference = getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                val prefEditor = preference.edit()
                prefEditor.putInt("KEY-TAB", 0)
                prefEditor.apply()
                openFragment(NowPlaying())
                return@OnNavigationItemSelectedListener true;
            }
            R.id.item_topRating ->{
//                viewPager.currentItem = 1
                val preference = getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                val prefEditor = preference.edit()
                prefEditor.putInt("KEY-TAB", 1)
                prefEditor.apply()
                openFragment(TopRating())
                return@OnNavigationItemSelectedListener true;
            }
            R.id.item_myFavorite ->{
//                viewPager.currentItem = 2
                val preference = getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                val prefEditor = preference.edit()
                prefEditor.putInt("KEY-TAB", 2)
                prefEditor.apply()
                openFragment(MyFavorite())
                return@OnNavigationItemSelectedListener true;
            }
        }
        false
    }
    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    public fun getFavoriteMovies() : ArrayList<Movie>{
        return favoriteMoviesList
    }

    override fun onFragmentListener(movie: Movie) {
        dao.insert(movie)
    }

}
