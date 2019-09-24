package com.silence.experimental.movies.presentation.details

import androidx.lifecycle.MutableLiveData
import com.silence.experimental.common.domain.entity.Failure
import com.silence.experimental.common.presentation.BaseViewModel
import com.silence.experimental.common.presentation.ErrorHandler
import com.silence.experimental.common.presentation.ViewState
import com.silence.experimental.movies.domain.entity.MovieDomainModel
import com.silence.experimental.movies.domain.entity.toPresentationModel
import com.silence.experimental.movies.domain.usecase.GetMovieDetails
import com.silence.experimental.movies.domain.usecase.GetMovieDetails.*
import com.silence.experimental.movies.presentation.entity.MoviePresentationModel
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetails: GetMovieDetails,
    private val errorHandler: ErrorHandler
) : BaseViewModel() {

    private val viewStateData = ViewState<MoviePresentationModel>(
        isLoading = true
    )

    val viewState = MutableLiveData<ViewState<MoviePresentationModel>>().apply {
        value = viewStateData
    }

    fun loadMovieDetails(id: Long) {
        getMovieDetails(this, Params(id)) { it.either(::handleFailure, ::handleMovieDetails) }
    }

    private fun handleMovieDetails(movie: MovieDomainModel) {
        viewState.value = viewStateData.apply {
            data = movie.toPresentationModel()
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