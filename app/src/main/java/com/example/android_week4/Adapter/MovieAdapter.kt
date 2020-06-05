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

class MovieAdapter(val ctx : Context, var movies : ArrayList<Movie>, val listener : MovieListener) : RecyclerView.Adapter<MovieAdapter.movieVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): movieVH {

        var view : View = LayoutInflater.from(ctx).inflate(R.layout.movie_item,parent,false)
        return movieVH(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }


    override fun onBindViewHolder(holder: movieVH, position: Int) {
        val movie : Movie = movies[position]
        if(movie.getposter()!="" || movie.getposter()!=null){
            Glide.with(ctx)
                .load(movie.getposter())
                .centerCrop()
                .into(holder.ivPoster)
        }
        holder.tvTitle.text         = movie.title
        holder.tvDescription.text   = movie.overview
        holder.ratingbar.rating     = movie.vote_average/2;
        holder.rating.text          = movie.vote_average.toString()
        holder.itemView.setOnClickListener{
            listener?.onClick(position,movie)
        }
        holder.itemView.setOnLongClickListener {
            listener?.removeFavoriteMovie(position,movie)
            true
        }
        holder.starIcon.setOnClickListener{
            listener?.addFavoriteMovie(position,movie)
        }

    }

    fun updateMovie(movie: ArrayList<Movie>){
        this.movies.addAll(movie)
        var set = this.movies.toSet()
        set.distinct()
        this.movies = set.toList() as ArrayList<Movie>
        notifyDataSetChanged()
    }

    fun setData(items: java.util.ArrayList<Movie>) {
        this.movies = items
    }
    interface MovieListener{
        fun onClick(pos : Int, movie : Movie)
        fun addFavoriteMovie(pos : Int, movie : Movie)
        fun removeFavoriteMovie(pos: Int, movie : Movie)
    }
    class movieVH(itemView : View) : RecyclerView.ViewHolder(itemView){
        val ivPoster        = itemView.findViewById<ImageView>(R.id.imageView)
        val tvTitle          = itemView.findViewById<TextView>(R.id.tvTitle)
        val tvDescription    = itemView.findViewById<TextView>(R.id.tvOverview)
        val starIcon        = itemView.findViewById<ImageView>(R.id.imStarIcon)
        val ratingbar        = itemView.findViewById<RatingBar>(R.id.ratingBar)
        val rating         = itemView.findViewById<TextView>(R.id.rating)
    }

    fun appendMovies(movies: List<Movie>) {
        this.movies.addAll(movies)
        notifyItemRangeInserted(
            this.movies.size,
            movies.size - 1
        )
    }

    fun updateMovies(movies: List<Movie>) {
        this.movies = movies as ArrayList<Movie>
        notifyDataSetChanged()
    }
}