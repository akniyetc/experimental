package com.silence.experimental.movies.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.stub
import com.nhaarman.mockitokotlin2.verify
import com.silence.experimental.CoroutineRule
import com.silence.experimental.UnitTest
import com.silence.experimental.common.domain.entity.Either.Left
import com.silence.experimental.common.domain.entity.Either.Right
import com.silence.experimental.common.domain.entity.Failure.NetworkConnection
import com.silence.experimental.common.presentation.ErrorHandler
import com.silence.experimental.common.presentation.ViewState
import com.silence.experimental.movies.domain.entity.MovieDomainModel
import com.silence.experimental.movies.domain.entity.toPresentationModel
import com.silence.experimental.movies.domain.usecase.GetMovieDetails
import com.silence.experimental.movies.presentation.details.MovieDetailsViewModel
import com.silence.experimental.movies.presentation.entity.MoviePresentationModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock

class MovieDetailsViewModelTest: UnitTest() {

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutineRule()

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var getMovieDetails: GetMovieDetails

    @Mock
    private lateinit var errorHandler: ErrorHandler

    private lateinit var movieDetailsViewModel: MovieDetailsViewModel

    @Mock
    private lateinit var testObserver: Observer<ViewState<MoviePresentationModel>>

    @Before
    fun setUp() {
        movieDetailsViewModel = MovieDetailsViewModel(getMovieDetails, errorHandler)
        movieDetailsViewModel.viewState.observeForever(testObserver)
    }

    @Test
    fun `should handle correct data from usecase`() {
        val movie = MovieDomainModel(id = 1)
        val viewState = ViewState(
            data = movie.toPresentationModel()
        )
        getMovieDetails.stub {
            onBlocking { invoke(1) } doReturn Right(movie)
        }
        movieDetailsViewModel.loadMovieDetails(1)
        verify(testObserver).onChanged(viewState)
    }

    @Test
    fun `should handle exception from usecase`() {
        val exception = NetworkConnection
        val viewState = ViewState<MoviePresentationModel>(
            errorMessage = errorHandler.proceed(exception)
        )
        getMovieDetails.stub {
            onBlocking { invoke(1) } doReturn Left(exception)
        }
        movieDetailsViewModel.loadMovieDetails(1)
        verify(testObserver).onChanged(viewState)
    }
}