package com.silence.experimental.movies.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.silence.experimental.movies.data.entity.MovieDBModel

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieDBModel>)

    @Query("SELECT * FROM MovieDBModel")
    suspend fun getAllMovies(): List<MovieDBModel>
}