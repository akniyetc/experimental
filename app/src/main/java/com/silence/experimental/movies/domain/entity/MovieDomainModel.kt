package com.silence.experimental.movies.domain.entity

data class MovieDomainModel(
    val overview: String = "",
    val video: Boolean = false,
    val title: String = "",
    val posterPath: String = "",
    val backdropPath: String = "",
    val releaseDate: String = "",
    val popularity: Double = 0.0,
    val voteAverage: Double = 0.0,
    val id: Int = 0,
    val adult: Boolean = false,
    val voteCount: Int = 0
)