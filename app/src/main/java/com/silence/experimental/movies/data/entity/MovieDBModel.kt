package com.silence.experimental.movies.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["query"], unique = true)])
data class MovieDBModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val value: String
)