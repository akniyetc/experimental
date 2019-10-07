package com.silence.experimental.movies.data.remote

import com.silence.experimental.common.domain.entity.Either
import com.silence.experimental.common.domain.entity.Failure
import com.silence.experimental.movies.data.entity.MovieRemoteModel

interface MoviesRemote {
    suspend fun loadPopularMovies(): Either<Failure, List<MovieRemoteModel>>
    suspend fun loadMovieDetails(id: Long): Either<Failure, MovieRemoteModel>
}