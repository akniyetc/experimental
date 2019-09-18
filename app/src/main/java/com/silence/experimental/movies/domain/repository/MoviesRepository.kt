package com.silence.experimental.movies.domain.repository

import com.silence.experimental.common.domain.entity.Either
import com.silence.experimental.common.domain.entity.Failure
import com.silence.experimental.movies.data.entity.MovieRemoteModel

interface MoviesRepository {
    suspend fun popularMovies(): Either<Failure, List<MovieRemoteModel>>
}