package com.silence.experimental.movies.presentation

import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.stub
import com.nhaarman.mockitokotlin2.verify
import com.silence.experimental.CoroutineRule
import com.silence.experimental.UnitTest
import com.silence.experimental.common.domain.entity.Either.*
import com.silence.experimental.common.domain.entity.Failure.*
import com.silence.experimental.common.presentation.ErrorHandler
import com.silence.experimental.common.presentation.ViewState
import com.silence.experimental.movies.domain.entity.MovieDomainModel
import com.silence.experimental.movies.domain.entity.toPresentationModel
import com.silence.experimental.movies.domain.usecase.GetMovies
import com.silence.experimental.movies.presentation.entity.MoviePresentationModel
import com.silence.experimental.movies.presentation.list.MoviesViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock

class MoviesViewModelTest : UnitTest() {

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutineRule()

    @Mock
    private lateinit var getMovies: GetMovies

    @Mock
    private lateinit var errorHandler: ErrorHandler

    private lateinit var moviesViewModel: MoviesViewModel

    @Mock
    private lateinit var testObserver: Observer<ViewState<List<MoviePresentationModel>>>

    @Before
    fun setUp() {
        moviesViewModel = MoviesViewModel(getMovies, errorHandler)
        moviesViewModel.viewState.observeForever(testObserver)
    }

    @Test
    fun `should handle correct data from usecase`() {
        val movies = listOf(MovieDomainModel(id = 1))
        val viewState = ViewState(
            data = movies.map { it.toPresentationModel() }
        )
        getMovies.stub {
            onBlocking { invoke() } doReturn Right(movies)
        }
        moviesViewModel.loadPopularMovies()
        verify(testObserver).onChanged(viewState)
    }

    @Test
    fun `should handle exception from usecase`() {
        val exception = NetworkConnection
        val viewState = ViewState<List<MoviePresentationModel>>(
            errorMessage = errorHandler.proceed(exception)
        )
        getMovies.stub {
            onBlocking { invoke() } doReturn Left(exception)
        }
        moviesViewModel.loadPopularMovies()
        verify(testObserver).onChanged(viewState)
    }
}