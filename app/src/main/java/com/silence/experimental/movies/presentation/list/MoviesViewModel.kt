package com.silence.experimental.movies.presentation.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silence.experimental.common.domain.entity.Failure
import com.silence.experimental.common.presentation.ErrorHandler
import com.silence.experimental.common.presentation.ViewState
import com.silence.experimental.movies.domain.entity.MovieDomainModel
import com.silence.experimental.movies.domain.entity.toPresentationModel
import com.silence.experimental.movies.domain.usecase.GetMovies
import com.silence.experimental.movies.presentation.entity.MoviePresentationModel
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias MoviesViewState = ViewState<List<MoviePresentationModel>>

class MoviesViewModel @Inject constructor(
    private val getMovies: GetMovies,
    private val errorHandler: ErrorHandler
) : ViewModel() {

    val viewState = MutableLiveData<MoviesViewState>().apply {
        value = MoviesViewState().apply { isLoading = true }
    }

    fun loadPopularMovies() {
        viewModelScope.launch {
            getMovies().either(::handleFailure, ::handlePopularMovies)
        }
    }

    private fun handlePopularMovies(movies: List<MovieDomainModel>) {
        viewState.value = MoviesViewState().apply {
            data = movies.map { it.toPresentationModel() }
            isLoading = false
            errorMessage = null
        }
    }

    private fun handleFailure(exception: Failure) {
        viewState.value = MoviesViewState().apply {
            data = null
            isLoading = false
            errorMessage = errorHandler.proceed(exception)
        }
    }
}