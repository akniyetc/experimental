package com.silence.experimental.movies.data.cache

import com.silence.experimental.common.data.ExperimentalDataBase
import com.silence.experimental.common.domain.entity.Either
import com.silence.experimental.common.domain.entity.Either.Left
import com.silence.experimental.common.domain.entity.Either.Right
import com.silence.experimental.common.domain.entity.Failure
import com.silence.experimental.common.domain.entity.Failure.FeatureFailure
import com.silence.experimental.movies.data.entity.MovieDBModel
import javax.inject.Inject

class MoviesCache @Inject constructor (private val moviesDB: ExperimentalDataBase) {

    suspend fun getCachedMovies(): Either<Failure, List<MovieDBModel>> {
        return try {
            Right(moviesDB.moviesDao().getAllMovies())
        } catch (t: Throwable) {
            Left(MoviesCacheFailure(t))
        }
    }

    suspend fun insertMovies(movies: List<MovieDBModel>) {
        moviesDB.moviesDao().insertMovies(movies)
    }

    suspend fun isCached(): Boolean {
        return moviesDB.moviesDao().getAllMovies().isNotEmpty()
    }

    class MoviesCacheFailure(exception: Throwable): FeatureFailure()
}