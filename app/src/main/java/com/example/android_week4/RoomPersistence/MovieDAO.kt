package com.example.android_week4.RoomPersistence

import androidx.room.*
import com.example.android_week4.Movie

@Dao
interface MovieDAO {
    @Query("SELECT * FROM movie")
    fun getAll(): List<Movie>


    @Query("SELECT * FROM movie WHERE poster_path LIKE :poster_path")
    fun findByName(poster_path: String): Movie

    @Query("SELECT * FROM Movie WHERE id =:id")
    fun findById(id: Int): Movie

    @Insert
    fun insertAll(vararg movie: Movie) : List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: Movie): Long

    @Delete
    fun delete(account: Movie)

    @Update
    fun update(account: Movie)

    @Query("DELETE FROM Movie")
    fun deleteAll()
}