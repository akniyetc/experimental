package com.silence.experimental.movies.data

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.silence.experimental.UnitTest
import com.silence.experimental.common.domain.entity.Either.Right
import com.silence.experimental.movies.data.cache.MoviesCache
import com.silence.experimental.movies.data.entity.MovieRemoteModel
import com.silence.experimental.movies.data.remote.MoviesRemote
import com.silence.experimental.movies.data.repository.MoviesRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`

class RepositoryTest : UnitTest() {

    @Mock
    private lateinit var remote: MoviesRemote

    @Mock
    private lateinit var cache: MoviesCache

    private lateinit var moviesRepository: MoviesRepositoryImpl

    @Before
    fun setup() {
        moviesRepository = MoviesRepositoryImpl(remote, cache)
    }

    @Test
    fun `repository should return cached movies`() = runBlocking {
        given { cache.isExpired() }.willReturn(false)
        `when`(cache.isCached()).thenReturn(true)
        `when`(cache.cachedMovies()).thenReturn(Right(emptyList()))
        moviesRepository.popularMovies()
        verify(cache).cachedMovies()
        verifyZeroInteractions(remote)
    }

    @Test
    fun `repository should return remote movies`() = runBlocking {
        `when`(cache.isCached()).thenReturn(false)
        `when`(remote.loadPopularMovies()).thenReturn(Right(emptyList()))
        moviesRepository.popularMovies()
        verify(remote).loadPopularMovies()
        verifyNoMoreInteractions(remote)
    }

    @Test
    fun `repository should return correct movie details`() = runBlocking {
        val testMovieDetails = Right(MovieRemoteModel(id = 1))
        `when`(remote.loadMovieDetails(1)).thenReturn(testMovieDetails)
        val result = moviesRepository.movieDetails(1)
        verify(remote).loadMovieDetails(1)
        result shouldEqual testMovieDetails
        verifyNoMoreInteractions(remote)
    }
}