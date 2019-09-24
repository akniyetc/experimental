package com.silence.experimental.movies.presentation.entity

data class MoviePresentationModel(
    val overview: String = "",
    val video: Boolean = false,
    val title: String = "",
    val posterPath: String = "",
    val backdropPath: String = "",
    val releaseDate: String = "",
    val popularity: Double = 0.0,
    val voteAverage: Double = 0.0,
    val id: Long = 0,
    val adult: Boolean = false,
    val voteCount: Int = 0,
    val tagLine: String = "",
    val budget: Long = 0L,
    val status: String = ""
)