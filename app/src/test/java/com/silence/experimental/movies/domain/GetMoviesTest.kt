package com.silence.experimental.movies.domain

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.silence.experimental.UnitTest
import com.silence.experimental.common.domain.entity.Either.Left
import com.silence.experimental.common.domain.entity.Either.Right
import com.silence.experimental.common.domain.entity.Failure.NetworkConnection
import com.silence.experimental.movies.data.entity.MovieRemoteModel
import com.silence.experimental.movies.data.entity.toDomainModel
import com.silence.experimental.movies.domain.repository.MoviesRepository
import com.silence.experimental.movies.domain.usecase.GetMovies
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class GetMoviesTest : UnitTest() {

    @Mock
    private lateinit var moviesRepository: MoviesRepository

    private lateinit var getMovies: GetMovies

    @Before
    fun setUp() {
        getMovies = GetMovies(moviesRepository)
    }

    @Test
    fun `should get data from repository`() = runBlocking {
        val testMoviesData = listOf(
                MovieRemoteModel(id = 1)
            )
        given(moviesRepository.popularMovies()).willReturn(Right(testMoviesData))
        val result = getMovies()
        result shouldEqual Right(testMoviesData.map { it.toDomainModel() })
        verify(moviesRepository).popularMovies()
        verifyNoMoreInteractions(moviesRepository)
    }

    @Test
    fun `should get exception from repository`() = runBlocking {
        val exception = NetworkConnection
        given(moviesRepository.popularMovies()).willReturn(Left(exception))
        val result = getMovies()
        result shouldEqual Left(exception)
        verify(moviesRepository).popularMovies()
        verifyNoMoreInteractions(moviesRepository)
    }
}