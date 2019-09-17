package com.silence.experimental.movies.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.silence.experimental.movies.data.entity.MovieDBModel

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieDBModel>)

    @Query("SELECT * FROM MovieDBModel")
    fun getAllMovies(): List<MovieDBModel>
}