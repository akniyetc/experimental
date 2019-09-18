package com.silence.experimental.movies.domain.usecase

import com.silence.experimental.common.domain.BaseUseCase
import com.silence.experimental.common.domain.entity.Either
import com.silence.experimental.common.domain.entity.Either.*
import com.silence.experimental.common.domain.entity.Failure
import com.silence.experimental.common.domain.entity.Failure.FeatureFailure
import com.silence.experimental.common.domain.entity.map
import com.silence.experimental.movies.data.entity.toDomainModel
import com.silence.experimental.movies.domain.entity.MovieDomainModel
import com.silence.experimental.movies.domain.repository.MoviesRepository
import com.silence.experimental.movies.domain.usecase.GetMovies.*

class GetMovies(private val moviesRepository: MoviesRepository) : BaseUseCase<List<MovieDomainModel>, Params>() {

    override suspend fun run(params: Params): Either<Failure, List<MovieDomainModel>> {
        return try {
            moviesRepository.popularMovies().map { repositoryModels ->
                repositoryModels.map {
                    it.toDomainModel()
                }
            }
        } catch (exp: Throwable) {
            Left(MoviesFailure(exp))
        }
    }

    class Params
    data class MoviesFailure(val t: Throwable): FeatureFailure()
}