package com.silence.experimental.movies.domain

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.silence.experimental.UnitTest
import com.silence.experimental.common.domain.entity.Either
import com.silence.experimental.common.domain.entity.Either.*
import com.silence.experimental.common.domain.entity.Failure
import com.silence.experimental.common.domain.entity.Failure.*
import com.silence.experimental.movies.data.entity.MovieRemoteModel
import com.silence.experimental.movies.data.entity.toDomainModel
import com.silence.experimental.movies.domain.repository.MoviesRepository
import com.silence.experimental.movies.domain.usecase.GetMovieDetails
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class GetMovieDetailsTest: UnitTest() {

    @Mock
    private lateinit var moviesRepository: MoviesRepository

    private lateinit var getMovieDetails: GetMovieDetails

    @Before
    fun setUp() {
        getMovieDetails = GetMovieDetails(moviesRepository)
    }

    @Test
    fun `should get data from repository`() = runBlocking {
        val movie = MovieRemoteModel(id = 1)
        given(moviesRepository.movieDetails(1)).willReturn(Right(movie))
        val result = getMovieDetails(1)
        result shouldEqual Right(movie.toDomainModel())
        verify(moviesRepository).movieDetails(1)
        verifyNoMoreInteractions(moviesRepository)
    }

    @Test
    fun `should get exception from repository`() = runBlocking {
        val exception = NetworkConnection
        given(moviesRepository.movieDetails(1)).willReturn(Left(exception))
        val result = getMovieDetails(1)
        result shouldEqual Left(exception)
        verify(moviesRepository).movieDetails(1)
        verifyNoMoreInteractions(moviesRepository)
    }
}