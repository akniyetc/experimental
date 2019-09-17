package com.silence.experimental.common.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.silence.experimental.movies.data.cache.MoviesDao
import com.silence.experimental.movies.data.entity.MovieDBModel

@Database(entities = [MovieDBModel::class], version = 1)
abstract class DataBase: RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}