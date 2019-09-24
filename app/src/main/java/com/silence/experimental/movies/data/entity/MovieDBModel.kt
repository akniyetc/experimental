package com.silence.experimental.movies.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["title"], unique = true)])
data class MovieDBModel(
    val overview: String = "",
    val video: Boolean = false,
    val title: String = "",
    val posterPath: String = "",
    val backdropPath: String = "",
    val releaseDate: String = "",
    val popularity: Double = 0.0,
    val voteAverage: Double = 0.0,

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val adult: Boolean = false,
    val voteCount: Int = 0,
    val tagLine: String = "",
    val budget: Long = 0L,
    val status: String = ""
)

fun MovieDBModel.toRepositoryModel(): MovieRemoteModel =
    this.let {
        MovieRemoteModel(
            it.overview,
            it.video,
            it.title,
            it.posterPath,
            it.backdropPath,
            it.releaseDate,
            it.popularity,
            it.voteAverage,
            it.id,
            it.adult,
            it.voteCount,
            it.tagLine,
            it.budget,
            it.status
        )
    }

