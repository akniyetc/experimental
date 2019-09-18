package com.silence.experimental.movies.domain.entity

import com.silence.experimental.movies.presentation.entity.MoviePresentationModel

data class MovieDomainModel(
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
    val voteCount: Int = 0
)

fun MovieDomainModel.toPresentationModel(): MoviePresentationModel =
    this.let {
        MoviePresentationModel(
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
            it.voteCount
        )
    }

