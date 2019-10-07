package com.silence.experimental.movies.domain.usecase

import com.silence.experimental.common.domain.entity.map
import com.silence.experimental.movies.data.entity.toDomainModel
import com.silence.experimental.movies.domain.repository.MoviesRepository
import javax.inject.Inject

class GetMovies @Inject constructor(private val moviesRepository: MoviesRepository){

    suspend operator fun invoke() = moviesRepository.popularMovies().map { repositoryModels ->
        repositoryModels.map {
            it.toDomainModel()
        }
    }
}