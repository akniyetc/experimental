package com.silence.experimental.movies.presentation

import android.os.Bundle
import com.silence.experimental.R
import com.silence.experimental.common.extension.observe
import com.silence.experimental.common.extension.viewModel
import com.silence.experimental.common.presentation.BaseFragment
import com.silence.experimental.common.presentation.ViewState
import com.silence.experimental.movies.di.MoviesComponent
import com.silence.experimental.movies.di.MoviesModule
import com.silence.experimental.movies.presentation.entity.MoviePresentationModel

class MoviesFragment: BaseFragment() {
    override val layoutId = R.layout.fragment_movies

    private var moviesComponent: MoviesComponent? = null
    private lateinit var moviesViewModel: MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        moviesComponent = appComponent.plusMoviesComponent(MoviesModule())
            .also { it.inject(this) }

        moviesViewModel = viewModel(viewModelFactory) {
            observe(viewState, ::handleViewState)
        }

    }

    private fun handleViewState(viewState: ViewState<List<MoviePresentationModel>>?) {
        viewState?.let {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        moviesComponent = null
    }
}