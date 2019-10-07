package com.silence.experimental.movies.data.cache

import com.silence.experimental.common.domain.entity.Either
import com.silence.experimental.common.domain.entity.Failure
import com.silence.experimental.movies.data.entity.MovieDBModel

interface MoviesCache {
    suspend fun cachedMovies(): Either<Failure, List<MovieDBModel>>
    suspend fun insertMovies(movies: List<MovieDBModel>)
    suspend fun isCached(): Boolean
    fun isExpired(): Boolean
}