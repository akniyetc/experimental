package com.silence.experimental.movies.data.repository

import com.silence.experimental.common.domain.entity.Either
import com.silence.experimental.common.domain.entity.Failure
import com.silence.experimental.common.domain.entity.map
import com.silence.experimental.movies.data.cache.MoviesCache
import com.silence.experimental.movies.data.entity.MovieRemoteModel
import com.silence.experimental.movies.data.entity.toRepositoryModel
import com.silence.experimental.movies.data.remote.MoviesRemote
import com.silence.experimental.movies.domain.repository.MoviesRepository
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val remote: MoviesRemote,
    private val cache: MoviesCache
) : MoviesRepository {

    override suspend fun popularMovies(): Either<Failure, List<MovieRemoteModel>> {
        return if (cache.isCached() && !cache.isExpired()) {
            cache.cachedMovies().map { moviesDBModels ->
                moviesDBModels.map { it.toRepositoryModel() }
            }
        } else {
            remote.loadPopularMovies()
        }
    }

    override suspend fun movieDetails(id: Long): Either<Failure, MovieRemoteModel> {
        return remote.loadMovieDetails(id)
    }
}