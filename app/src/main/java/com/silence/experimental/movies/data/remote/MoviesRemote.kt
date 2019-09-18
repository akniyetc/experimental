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

class MoviesRemote(
    private val mainService: MainService,
    private val networkHandler: NetworkHandler,
    private val cache: MoviesCache
) {

    suspend fun loadPopularMovies(): Either<Failure, List<MovieRemoteModel>> {
        return if (networkHandler.hasNetworkConnection()) {
            val remoteMovies = mainService.loadPopularMovies().results
            saveMovies(remoteMovies)
            request(remoteMovies)
        } else {
            Left(NetworkConnection)
        }
    }

    private fun <T> request(data: T?): Either<Failure, T> {
        return try {
            if (data == null) Left(ServerError) else Right(data)
        } catch (t: Throwable) {
            Left(ServerError)
        }
    }

    private suspend fun saveMovies(movies: List<MovieRemoteModel>?) {
        movies?.let {remoteMovies ->
            cache.insertMovies(
                remoteMovies.map { it.toDBModel() }
            )
        }
    }
}