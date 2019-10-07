package com.silence.experimental.movies.data.remote

import com.silence.experimental.common.data.MainService
import com.silence.experimental.common.data.NetworkHandler
import com.silence.experimental.common.domain.entity.Either
import com.silence.experimental.common.domain.entity.Either.Left
import com.silence.experimental.common.domain.entity.Either.Right
import com.silence.experimental.common.domain.entity.Failure
import com.silence.experimental.common.domain.entity.Failure.NetworkConnection
import com.silence.experimental.common.domain.entity.Failure.ServerError
import com.silence.experimental.movies.data.cache.MoviesCache
import com.silence.experimental.movies.data.entity.MovieRemoteModel
import com.silence.experimental.movies.data.entity.toDBModel
import javax.inject.Inject

class MoviesRemoteImpl @Inject constructor(
    private val mainService: MainService,
    private val networkHandler: NetworkHandler,
    private val cache: MoviesCache
) : MoviesRemote {

    override suspend fun loadPopularMovies(): Either<Failure, List<MovieRemoteModel>> {
        return if (networkHandler.hasNetworkConnection()) {
            mainService.loadPopularMovies().results.let {
                saveMovies(it)
                loadData(it)
            }
        } else {
            Left(NetworkConnection)
        }
    }

    override suspend fun loadMovieDetails(id: Long): Either<Failure, MovieRemoteModel> {
        return if (networkHandler.hasNetworkConnection()) {
            loadData(mainService.loadMovieDetails(id))
        } else {
            Left(NetworkConnection)
        }
    }

    private suspend fun saveMovies(movies: List<MovieRemoteModel>) =
        cache.insertMovies(movies.map { it.toDBModel() })

    private fun <T> loadData(data: T): Either<Failure, T> {
        return try {
            Right(data)
        } catch (t: Throwable) {
            Left(ServerError)
        }
    }

}