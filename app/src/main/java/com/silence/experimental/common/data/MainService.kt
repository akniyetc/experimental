package com.silence.experimental.common.data

import com.silence.experimental.movies.data.entity.MoviesRemoteData
import retrofit2.http.GET

interface MainService {

    companion object {
        private const val BASE = "3/movie/"
        private const val MOVIES_POPULAR = "${BASE}popular"
    }

    @GET(MOVIES_POPULAR)
    suspend fun loadPopularMovies(): MoviesRemoteData
}