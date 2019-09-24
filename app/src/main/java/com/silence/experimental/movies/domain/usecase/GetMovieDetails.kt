package com.silence.experimental.movies.domain.usecase

import com.silence.experimental.common.domain.UseCase
import com.silence.experimental.common.domain.entity.Either
import com.silence.experimental.common.domain.entity.Failure
import com.silence.experimental.common.domain.entity.Failure.*
import com.silence.experimental.common.domain.entity.map
import com.silence.experimental.movies.data.entity.toDomainModel
import com.silence.experimental.movies.domain.entity.MovieDomainModel
import com.silence.experimental.movies.domain.repository.MoviesRepository
import com.silence.experimental.movies.domain.usecase.GetMovieDetails.*
import javax.inject.Inject

class GetMovieDetails @Inject constructor(private val moviesRepository: MoviesRepository) :
    UseCase<MovieDomainModel, Params>() {

    override suspend fun run(params: Params): Either<Failure, MovieDomainModel> {
        return try {
            moviesRepository.movieDetails(params.id).map { it.toDomainModel() }
        } catch (exp: Throwable) {
            Either.Left(MovieDetailsUseCaseException(exp))
        }
    }

    data class Params(val id: Long)
    data class MovieDetailsUseCaseException(val t: Throwable) : FeatureFailure()
}