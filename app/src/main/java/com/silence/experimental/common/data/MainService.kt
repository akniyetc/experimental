package com.silence.experimental.common.data

import com.silence.experimental.movies.data.entity.MovieRemoteModel
import com.silence.experimental.movies.data.entity.MoviesRemoteData
import retrofit2.http.GET
import retrofit2.http.Path

interface MainService {

    companion object {
        private const val BASE = "3/movie/"
        private const val MOVIES_POPULAR = "${BASE}popular"
        private const val MOVIE_DETAILS = "${BASE}{id}"
    }

    @GET(MOVIES_POPULAR)
    suspend fun loadPopularMovies(): MoviesRemoteData

    @GET(MOVIE_DETAILS)
    suspend fun loadMovieDetails(@Path("id") movieId: Long): MovieRemoteModel
}