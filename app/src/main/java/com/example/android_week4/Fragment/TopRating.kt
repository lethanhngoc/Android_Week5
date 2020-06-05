package com.example.android_week4.Fragment

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_week4.*
import com.example.android_week4.Adapter.GridMovieAdapter
import com.example.android_week4.Adapter.MovieAdapter
import com.example.android_week4.RoomPersistence.AppDatabase
import com.example.android_week4.RoomPersistence.MovieDAO
import com.example.android_week4.movieapi.MovieModel
import com.example.android_week4.movieapi.VideoResponse

class TopRating : Fragment() {
    lateinit var btnList: ImageButton
    lateinit var btnGrid: ImageButton
    var FavoriteMovies = ArrayList<Movie>()
    lateinit var mListener: ListenerFromTopRatingFragment
    private lateinit var db: AppDatabase
    lateinit var dao: MovieDAO
    //private lateinit var movieAdapter: MovieAdapter
    //private lateinit var movieGridAdapter: GridMovieAdapter
    lateinit var recyclerView: RecyclerView

    companion object{
        var Instance = TopRating()
        var listTopRatingMovie = ArrayList<Movie>()
        var CURRENT_TOPRATING_PAGE =1
        var movieAdapter:MovieAdapter? =null
        var movieGridAdapter: GridMovieAdapter? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activity: MainActivity = activity as MainActivity
        db = AppDatabase.invoke(activity)
        dao = db.movieDAO()
        val movies = dao.getAll()
        this.FavoriteMovies.addAll(movies)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_top_rating, container, false)
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnList = view?.findViewById<ImageButton>(R.id.now_btn_list)
        btnGrid = view?.findViewById<ImageButton>(R.id.now_btn_grid)
        recyclerView = view?.findViewById<RecyclerView>(R.id.rvTopRating)

        var layoutmanager = LinearLayoutManager(context)
        MovieModel.Instance.getTopRatingMovie(CURRENT_TOPRATING_PAGE)

        movieAdapter = context?.let {
            MovieAdapter(it, listTopRatingMovie, listener)
        }!!

        movieGridAdapter = context?.let {
            GridMovieAdapter(it, listTopRatingMovie, listener1)
        }!!

        recyclerView.layoutManager = layoutmanager
        recyclerView.adapter = movieAdapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                var visableItemCount = layoutmanager.childCount
                var totalItemCount = layoutmanager.itemCount
                if (!recyclerView.canScrollVertically(1)) {
                    CURRENT_TOPRATING_PAGE++
                    Log.d("CURR: ", "${CURRENT_TOPRATING_PAGE}")
                    MovieModel.Instance.getTopRatingMovie(CURRENT_TOPRATING_PAGE)
                }
            }
        })

        btnList.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //MovieModel.Instance.getTopRatingMovie(CURRENT_TOPRATING_PAGE)
                var layoutmanager: LinearLayoutManager = LinearLayoutManager(context)
                recyclerView.layoutManager = layoutmanager
                recyclerView.adapter = movieAdapter

            }
        })
        btnGrid.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //MovieModel.Instance.getTopRatingMovie(CURRENT_TOPRATING_PAGE)
                var layoutmanager: GridLayoutManager = GridLayoutManager(context, 2)
                recyclerView.layoutManager = layoutmanager
                recyclerView.adapter = movieGridAdapter

            }
        })
    }

    fun setDataOnChanges(listMovies: ArrayList<Movie>){
//        if(listTopRatingMovie.size<=0){
//            listTopRatingMovie = listMovies
//        }
        Log.e("ERROR","${listMovies.size}")
        listTopRatingMovie = listMovies
    }



    private var listener = object :
        MovieAdapter.MovieListener {
        override fun onClick(pos: Int, movie: Movie) {
            startProfileActivity(movie)
        }

        override fun addFavoriteMovie(pos: Int, movie: Movie) {
            val builder = AlertDialog.Builder(activity)
                .setTitle("Alert")
                .setMessage("Ban co muon them phim vao danh sach yeu thich?")
                .setPositiveButton("YES") { _, _ ->
                    if (FavoriteMovies.contains(movie)) {
                        Toast.makeText(context, "Phim da co trong danh sach", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        addFvMovie(movie)
                        Toast.makeText(
                            context,
                            "Them phim vao danh sach thanh cong",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }.setNegativeButton("NO") { dialog, _ -> dialog.dismiss() }
            val dialog = builder.create()
            dialog.show()
        }

        override fun removeFavoriteMovie(pos: Int, movie: Movie) {

        }
    }
    private var listener1 = object :
        GridMovieAdapter.MovieListener {
        override fun onClick(pos: Int, movie: Movie) {
            startProfileActivity(movie)
        }

        override fun addFavoriteMovie(pos: Int, movie: Movie) {
            val builder = AlertDialog.Builder(activity)
                .setTitle("Alert")
                .setMessage("Ban co muon them phim vao danh sach yeu thich?")
                .setPositiveButton("YES") { _, _ ->
                    if (FavoriteMovies.contains(movie)) {
                        Toast.makeText(context, "Phim da co trong danh sach", Toast.LENGTH_SHORT)
                    } else {
                        addFvMovie(movie)
                        Toast.makeText(
                            context,
                            "Them phim vao danh sach thanh cong",
                            Toast.LENGTH_SHORT
                        )
                    }
                }.setNegativeButton("NO") { dialog, _ -> dialog.dismiss() }
            val dialog = builder.create()
            dialog.show()
        }

        override fun removeFavoriteMovie(pos: Int, movie: Movie) {

        }
    }

    private fun addFvMovie(movie: Movie) {
        mListener.onFragmentListener(movie)
    }

    interface ListenerFromTopRatingFragment {
        fun onFragmentListener(movie: Movie)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ListenerFromTopRatingFragment) {
            mListener = context
        }
    }

    private fun startProfileActivity(movie: Movie) {
        val intent = Intent(
            activity,
            ProfileActivity::class.java
        )
        intent.putExtra("crrmovie", movie)
        activity?.startActivity(intent)
    }

//    private fun converJsonToData(): ArrayList<Movie> {
//        val data = Gson().fromJson(
//            DataCenter_1.getTopRateMovieJson(),
//            data::class.java
//        )
//        val result = data.results
//
//        val arrayTutorialType = object : TypeToken<ArrayList<Movie>>() {}.type;
//        var movies: ArrayList<Movie> = Gson().fromJson(Gson().toJson(result), arrayTutorialType)
//        return movies
//    }
}