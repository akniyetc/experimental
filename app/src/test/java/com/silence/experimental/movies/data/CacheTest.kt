package com.silence.experimental.movies.data

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.silence.experimental.AndroidTest
import com.silence.experimental.common.data.ExperimentalDataBase
import com.silence.experimental.common.data.PreferenceHelper
import com.silence.experimental.common.domain.entity.Either.Left
import com.silence.experimental.common.domain.entity.Either.Right
import com.silence.experimental.movies.data.cache.MoviesCache
import com.silence.experimental.movies.data.cache.MoviesCacheImpl
import com.silence.experimental.movies.data.cache.MoviesCacheImpl.MoviesCacheFailure
import com.silence.experimental.movies.data.cache.MoviesDao
import com.silence.experimental.movies.data.entity.MovieDBModel
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`

class CacheTest : AndroidTest() {
    @Mock
    private lateinit var db: ExperimentalDataBase

    @Mock
    private lateinit var dao: MoviesDao

    @Mock
    private lateinit var preferenceHelper: PreferenceHelper

    private lateinit var moviesCache: MoviesCache

    @Before
    fun setUp() {
        given { db.moviesDao() }.willReturn(dao)
        moviesCache = MoviesCacheImpl(db, preferenceHelper)
    }

    @Test
    fun `cache should return correct list`() = runBlocking {
        val testMovies = listOf(
            MovieDBModel(
                id = 1
            )
        )
        `when`(db.moviesDao().getAllMovies()).thenReturn(testMovies)
        val result = moviesCache.cachedMovies()
        result shouldEqual Right(testMovies)
    }

    @Test
    fun `cache should throw exception`() = runBlocking {
        val exception = NullPointerException()
        `when`(db.moviesDao().getAllMovies()).thenThrow(exception)
        val result = moviesCache.cachedMovies()
        result shouldEqual Left(MoviesCacheFailure(exception))
    }

    @Test
    fun `cache should insert list`() = runBlocking {
        val testMovies = listOf(
            MovieDBModel(
                id = 1
            )
        )
        moviesCache.insertMovies(testMovies)
        verify(db.moviesDao()).insertMovies(testMovies)
        verifyNoMoreInteractions(db.moviesDao())
    }

    @Test
    fun `cache should remind cache is not empty`() = runBlocking {
        val testMovies = listOf(
            MovieDBModel(
                id = 1
            )
        )
        `when`(db.moviesDao().getAllMovies()).thenReturn(testMovies)
        db.moviesDao().getAllMovies().isNotEmpty() shouldEqual true
    }

    @Test
    fun `cache should return expired time false`() {
        val currentTime = System.currentTimeMillis()
        given { preferenceHelper.lastCacheTime }.willReturn(currentTime)
        moviesCache.isExpired() shouldEqual false
    }

    @Test
    fun `cache should return expired time true`() {
        val time = System.currentTimeMillis() - EXPIRATION_TIME - 1000
        given { preferenceHelper.lastCacheTime }.willReturn(time)
        moviesCache.isExpired() shouldEqual true
    }

    companion object{
        private const val EXPIRATION_TIME = (60 * 10 * 1000).toLong()
    }
}