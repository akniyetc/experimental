package com.silence.experimental.movies.presentation

import androidx.lifecycle.MutableLiveData
import com.silence.experimental.common.domain.entity.Failure
import com.silence.experimental.common.presentation.BaseViewModel
import com.silence.experimental.common.presentation.ErrorHandler
import com.silence.experimental.common.presentation.ViewState
import com.silence.experimental.movies.domain.entity.MovieDomainModel
import com.silence.experimental.movies.domain.entity.toPresentationModel
import com.silence.experimental.movies.domain.usecase.GetMovies
import com.silence.experimental.movies.domain.usecase.GetMovies.*
import com.silence.experimental.movies.presentation.entity.MoviePresentationModel
import javax.inject.Inject

class MoviesViewModel @Inject constructor(
    private val getMovies: GetMovies,
    private val errorHandler: ErrorHandler
) : BaseViewModel() {

    private val viewStateData = ViewState<List<MoviePresentationModel>>(
        isLoading = true
    )

    val viewState = MutableLiveData<ViewState<List<MoviePresentationModel>>>().apply {
        value = viewStateData
    }

    fun loadPopularMovies() {
        viewState.value?.let {
            it.data = null
            it.isLoading = true
            it.errorMessage = null
        }
        getMovies(this, Params()) { it.either(::handleFailure, ::handlePopularMovies) }
    }

    private fun handlePopularMovies(movies: List<MovieDomainModel>) {
        viewState.value = viewStateData.apply {
            data = movies.map { it.toPresentationModel() }
            isLoading = false
            errorMessage = null
        }
    }

    private fun handleFailure(exception: Failure) {
        errorHandler.proceed(exception) { message ->
            viewState.value = viewStateData.apply {
                data = null
                isLoading = false
                errorMessage = message
            }
        }
    }
}