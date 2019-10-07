package com.silence.experimental.movies.presentation.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silence.experimental.common.domain.entity.Failure
import com.silence.experimental.common.presentation.ErrorHandler
import com.silence.experimental.common.presentation.ViewState
import com.silence.experimental.movies.domain.entity.MovieDomainModel
import com.silence.experimental.movies.domain.entity.toPresentationModel
import com.silence.experimental.movies.domain.usecase.GetMovieDetails
import com.silence.experimental.movies.presentation.entity.MoviePresentationModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetails: GetMovieDetails,
    private val errorHandler: ErrorHandler
) : ViewModel() {

    val viewState = MutableLiveData<ViewState<MoviePresentationModel>>().apply {
        value = ViewState<MoviePresentationModel>().apply { isLoading = true }
    }

    fun loadMovieDetails(id: Long) {
        viewModelScope.launch {
            getMovieDetails(id).either(::handleFailure, ::handleMovieDetails)
        }
    }

    private fun handleMovieDetails(movie: MovieDomainModel) {
        viewState.value = ViewState<MoviePresentationModel>().apply {
            data = movie.toPresentationModel()
            isLoading = false
            errorMessage = null
        }
    }

    private fun handleFailure(exception: Failure) {
        viewState.value = ViewState<MoviePresentationModel>().apply {
            data = null
            isLoading = false
            errorMessage = errorHandler.proceed(exception)
        }
    }
}