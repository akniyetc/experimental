package com.silence.experimental.movies.data.cache

import com.silence.experimental.common.data.ExperimentalDataBase
import com.silence.experimental.common.data.PreferenceHelper
import com.silence.experimental.common.domain.entity.Either
import com.silence.experimental.common.domain.entity.Either.Left
import com.silence.experimental.common.domain.entity.Either.Right
import com.silence.experimental.common.domain.entity.Failure
import com.silence.experimental.common.domain.entity.Failure.FeatureFailure
import com.silence.experimental.movies.data.entity.MovieDBModel
import javax.inject.Inject

class MoviesCacheImpl @Inject constructor(
    private val moviesDB: ExperimentalDataBase,
    private val preferenceHelper: PreferenceHelper
) : MoviesCache {

    override suspend fun cachedMovies(): Either<Failure, List<MovieDBModel>> {
        return try {
            Right(moviesDB.moviesDao().getAllMovies())
        } catch (t: Throwable) {
            Left(MoviesCacheFailure(t))
        }
    }

    override suspend fun insertMovies(movies: List<MovieDBModel>) {
        moviesDB.moviesDao().insertMovies(movies)
            .also { setLastCacheTime(System.currentTimeMillis()) }
    }

    override suspend fun isCached(): Boolean {
        return moviesDB.moviesDao().getAllMovies().isNotEmpty()
    }

    override fun isExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = this.getLastCacheUpdateTimeMillis()
        return currentTime - lastUpdateTime > EXPIRATION_TIME
    }

    private fun setLastCacheTime(lastCache: Long) {
        preferenceHelper.lastCacheTime = lastCache
    }

    private fun getLastCacheUpdateTimeMillis(): Long =
        preferenceHelper.lastCacheTime

    data class MoviesCacheFailure(val t: Throwable) : FeatureFailure()

    companion object {
        private const val EXPIRATION_TIME = (60 * 10 * 1000).toLong()
    }
}