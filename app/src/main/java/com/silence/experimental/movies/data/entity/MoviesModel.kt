package com.silence.experimental.movies.data.entity

import com.google.gson.annotations.SerializedName
import com.silence.experimental.movies.domain.entity.MovieDomainModel

data class MovieRemoteModel(
    @SerializedName("overview")
    val overview: String = "",
    @SerializedName("video")
    val video: Boolean = false,
    @SerializedName("title")
    val title: String = "",
    @SerializedName("poster_path")
    val posterPath: String = "",
    @SerializedName("backdrop_path")
    val backdropPath: String = "",
    @SerializedName("release_date")
    val releaseDate: String = "",
    @SerializedName("popularity")
    val popularity: Double = 0.0,
    @SerializedName("vote_average")
    val voteAverage: Double = 0.0,
    @SerializedName("id")
    val id: Long = 0,
    @SerializedName("adult")
    val adult: Boolean = false,
    @SerializedName("vote_count")
    val voteCount: Int = 0,
    @SerializedName("tagline")
    val tagLine: String = "",
    @SerializedName("budget")
    val budget: Long = 0L,
    @SerializedName("status")
    val status: String = ""
)


data class MoviesRemoteData(
    @SerializedName("page")
    val page: Int = 0,
    @SerializedName("total_pages")
    val totalPages: Int = 0,
    @SerializedName("results")
    val results: List<MovieRemoteModel> = emptyList(),
    @SerializedName("total_results")
    val totalResults: Int = 0
)

fun MovieRemoteModel.toDomainModel(): MovieDomainModel =
    this.let {
        MovieDomainModel(
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

fun MovieRemoteModel.toDBModel(): MovieDBModel =
    this.let {
        MovieDBModel(
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


