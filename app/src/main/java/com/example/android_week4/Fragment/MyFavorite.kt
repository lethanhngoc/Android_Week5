package com.example.android_week4.Fragment

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_week4.*
import com.example.android_week4.Adapter.GridMovieAdapter
import com.example.android_week4.Adapter.MovieAdapter
import com.example.android_week4.RoomPersistence.AppDatabase
import com.example.android_week4.RoomPersistence.MovieDAO
import kotlinx.android.synthetic.main.fragment_my_favorite.*
import kotlinx.android.synthetic.main.fragment_now_playing.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule

class MyFavorite : Fragment() {
    lateinit var btnList : ImageButton
    lateinit var btnGrid : ImageButton

    var FavoriteMovies = ArrayList<Movie>()

    private lateinit var db: AppDatabase
    lateinit var dao : MovieDAO

    private lateinit var movieAdapter: MovieAdapter
    private lateinit var movieGridAdapter: GridMovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activity : MainActivity = activity as MainActivity

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
        return inflater.inflate(R.layout.fragment_my_favorite,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnList = view?.findViewById<ImageButton>(R.id.now_btn_list)
        btnGrid = view?.findViewById<ImageButton>(R.id.now_btn_grid)


        var layoutmanager : LinearLayoutManager = LinearLayoutManager(context)

        movieAdapter = context?.let {
            MovieAdapter(it, FavoriteMovies, listener)
        }!!
        movieGridAdapter = context?.let {
            GridMovieAdapter(it, FavoriteMovies, listener1)
        }!!

        rv.layoutManager = layoutmanager
        rv.adapter       = movieAdapter

        btnList.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                var layoutmanager: LinearLayoutManager = LinearLayoutManager(context)
                    rv.layoutManager = layoutmanager
                    rv.adapter       = movieAdapter
                }
            }
        )
        btnGrid.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                var layoutmanager: GridLayoutManager = GridLayoutManager(context, 2)
                rv.layoutManager = layoutmanager
                rv.adapter       = movieGridAdapter
            }
        })
    }
    private var listener = object :
        MovieAdapter.MovieListener {
        override fun onClick(pos: Int, movie: Movie) {
            startProfileActivity(movie)
        }

        override fun addFavoriteMovie(pos: Int, movie: Movie) {

        }

        override fun removeFavoriteMovie(pos: Int, movie: Movie) {
            val builder = AlertDialog.Builder(context)
                .setTitle("Alert")
                .setMessage("Ban co muon xoa phim khoi danh sach yeu thich?")
                .setPositiveButton("YES"){_,_->
                    dao.delete(movie)
                    FavoriteMovies.removeAt(pos)
                    movieAdapter.notifyItemRemoved(pos)
                    Timer(false).schedule(500) {
                        activity?.runOnUiThread {
                            movieAdapter.setData(FavoriteMovies)
                            movieAdapter.notifyDataSetChanged()
                        }
                    }
                    Toast.makeText(context,"Xoa phim thanh cong", Toast.LENGTH_SHORT).show()
                }.setNegativeButton("NO"){dialog,_ ->dialog.dismiss() }
            val dialog = builder.create()
            dialog.show()
        }
    }
    private var listener1 = object :
        GridMovieAdapter.MovieListener {
        override fun onClick(pos: Int, movie: Movie) {
            startProfileActivity(movie)
        }

        override fun addFavoriteMovie(pos: Int, movie: Movie) {

        }

        override fun removeFavoriteMovie(pos: Int, movie: Movie) {
            val builder = AlertDialog.Builder(context)
                .setTitle("Alert")
                .setMessage("Ban co muon xoa phim khoi danh sach yeu thich?")
                .setPositiveButton("YES"){_,_->
                    dao.delete(movie)
                    FavoriteMovies.removeAt(pos)
                    movieGridAdapter.notifyItemRemoved(pos)
                    Timer(false).schedule(500) {
                        activity?.runOnUiThread {
                            movieGridAdapter.setData(FavoriteMovies)
                            movieGridAdapter.notifyDataSetChanged()
                        }
                    }
                    Toast.makeText(context,"Xoa phim thanh cong", Toast.LENGTH_SHORT).show()
                }.setNegativeButton("NO"){dialog,_ ->dialog.dismiss() }
            val dialog = builder.create()
            dialog.show()
        }
    }
    private fun startProfileActivity(movie: Movie) {
        val intent = Intent (activity,
            ProfileActivity::class.java)
        intent.putExtra("crrmovie",movie)
        activity?.startActivity(intent)
    }
}