package com.silence.experimental.movies.presentation

import android.os.Bundle
import android.view.View
import com.silence.experimental.R
import com.silence.experimental.common.extension.observe
import com.silence.experimental.common.extension.viewModel
import com.silence.experimental.common.extension.visible
import com.silence.experimental.common.presentation.BaseFragment
import com.silence.experimental.common.presentation.ViewState
import com.silence.experimental.movies.di.MoviesComponent
import com.silence.experimental.movies.presentation.entity.MoviePresentationModel
import kotlinx.android.synthetic.main.fragment_movies.*
import javax.inject.Inject

class MoviesFragment : BaseFragment() {
    override val layoutId = R.layout.fragment_movies

    private var moviesComponent: MoviesComponent? = null
    private lateinit var moviesViewModel: MoviesViewModel

    @Inject
    lateinit var moviesAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        injectMembers()

        moviesViewModel = viewModel(viewModelFactory) {
            observe(viewState, ::handleViewState)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        moviesViewModel.loadPopularMovies()
    }

    private fun initViews() {
        rvMovies.adapter = moviesAdapter
        swipeRefreshMovies.setOnRefreshListener { moviesViewModel.loadPopularMovies() }
    }

    private fun injectMembers() {
        moviesComponent = appComponent.plusMoviesComponent()
            .also { it.inject(this) }
    }

    private fun handleViewState(viewState: ViewState<List<MoviePresentationModel>>?) {
        viewState?.let { state ->
            state.data?.let { moviesAdapter.collection = it }
            rvMovies.visible(state.data != null)
            swipeRefreshMovies.isRefreshing = state.isLoading
            state.errorMessage?.let { showMessage(it, contentMovies) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        moviesComponent = null
    }
}