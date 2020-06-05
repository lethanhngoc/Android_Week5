package com.example.android_week4.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android_week4.Movie
import com.example.android_week4.R
import java.lang.Exception

class GridMovieAdapter (val ctx : Context, var movies : ArrayList<Movie>, val listener : MovieListener) : RecyclerView.Adapter<GridMovieAdapter.movieVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): movieVH {
        val view : View = LayoutInflater.from(ctx).inflate(R.layout.grid_movie_item,parent,false)
        return movieVH(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: movieVH, position: Int) {
        val movie : Movie = movies[position]

        if(movie.getposter().isNotBlank()){
            try {
                println("PATH ${movie.getposter()} "+"PATH")
                Glide.with(ctx)
                    .load(movie.getposter())
                    .centerCrop()
                    .into(holder.ivPoster)
            }catch (e: Exception){

            }

        }
        holder.tvTitle.text         = movie.title
        holder.tvDescription.text   = movie.overview
        holder.ratingbar.rating     = movie.vote_average/2;
        holder.rating.text          = movie.vote_average.toString()
        holder.itemView.setOnClickListener{
            listener?.onClick(position,movie)
        }
        holder.starIcon.setOnClickListener{
            listener?.addFavoriteMovie(position,movie)
        }
        holder.itemView.setOnLongClickListener {
            listener?.removeFavoriteMovie(position,movie)
            true
        }
    }

    fun setData(items: java.util.ArrayList<Movie>) {
        this.movies = items
    }

    fun updateMovie(movie: ArrayList<Movie>){
        this.movies.addAll(movie)
        var set = this.movies.toSet()
        set.distinct()
        this.movies = set.toList() as ArrayList<Movie>
        notifyDataSetChanged()
    }

    interface MovieListener{
        fun onClick(pos : Int, movie : Movie)
        fun addFavoriteMovie(pos : Int, movie : Movie)
        fun removeFavoriteMovie(pos: Int, movie : Movie)
    }
    class movieVH(itemView : View) : RecyclerView.ViewHolder(itemView){
        val ivPoster        = itemView.findViewById<ImageView>(R.id.imageView)
        val tvTitle         = itemView.findViewById<TextView>(R.id.tvTitle)
        val tvDescription   = itemView.findViewById<TextView>(R.id.tvOverview)
        val starIcon        = itemView.findViewById<ImageView>(R.id.imStarIcon)
        val ratingbar        = itemView.findViewById<RatingBar>(R.id.ratingBar)
        val rating         = itemView.findViewById<TextView>(R.id.rating)
    }
}