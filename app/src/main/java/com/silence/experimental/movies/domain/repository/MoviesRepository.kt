package com.silence.experimental.movies.domain.repository

import com.silence.experimental.common.domain.entity.Either
import com.silence.experimental.common.domain.entity.Failure
import com.silence.experimental.movies.domain.entity.MovieDomainModel
import kotlinx.coroutines.channels.ReceiveChannel

interface MoviesRepository {
    fun popularMovies(): ReceiveChannel<Either<Failure, List<MovieDomainModel>>>
}