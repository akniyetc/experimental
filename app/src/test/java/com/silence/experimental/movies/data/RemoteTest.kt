package com.silence.experimental.movies.data

import com.nhaarman.mockitokotlin2.given
import com.silence.experimental.AndroidTest
import com.silence.experimental.common.data.MainService
import com.silence.experimental.common.data.NetworkHandler
import com.silence.experimental.common.domain.entity.Either.Left
import com.silence.experimental.common.domain.entity.Either.Right
import com.silence.experimental.common.domain.entity.Failure.NetworkConnection
import com.silence.experimental.movies.data.cache.MoviesCache
import com.silence.experimental.movies.data.entity.MovieRemoteModel
import com.silence.experimental.movies.data.entity.MoviesRemoteData
import com.silence.experimental.movies.data.remote.MoviesRemote
import com.silence.experimental.movies.data.remote.MoviesRemoteImpl
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class RemoteTest : AndroidTest() {

    @Mock
    private lateinit var service: MainService

    @Mock
    private lateinit var networkHandler: NetworkHandler

    @Mock
    private lateinit var cache: MoviesCache

    private lateinit var remote: MoviesRemote

    @Before
    fun setup() {
        remote = MoviesRemoteImpl(service, networkHandler, cache)
    }

    @Test
    fun `remote should return correct list`() = runBlocking {
        val testMovieData = MoviesRemoteData(results = listOf(MovieRemoteModel(id = 1)))
        given { networkHandler.hasNetworkConnection() }.willReturn(true)
        Mockito.`when`(service.loadPopularMovies()).thenReturn(testMovieData)

        val result = remote.loadPopularMovies()

        result shouldEqual Right(testMovieData.results)
    }

    @Test
    fun `remote should return network connection exception`() = runBlocking {
        given { networkHandler.hasNetworkConnection() }.willReturn(false)

        val result = remote.loadPopularMovies()

        result shouldEqual Left(NetworkConnection)
    }

    @Test
    fun `remote should return correct movie by id`() = runBlocking {
        given { networkHandler.hasNetworkConnection() }.willReturn(true)
        val movieDetails = MovieRemoteModel(id = 1)
        Mockito.`when`(service.loadMovieDetails(1)).thenReturn(movieDetails)

        val result = remote.loadMovieDetails(1)

        result shouldEqual Right(movieDetails)
    }
}