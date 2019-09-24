package com.silence.experimental.movies.presentation.details

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.transition.TransitionInflater
import com.silence.experimental.R
import com.silence.experimental.common.extension.*
import com.silence.experimental.common.presentation.BaseFragment
import com.silence.experimental.common.presentation.ViewState
import com.silence.experimental.movies.di.MoviesComponent
import com.silence.experimental.movies.presentation.entity.MoviePresentationModel
import com.silence.experimental.movies.presentation.entity.PosterUrls
import kotlinx.android.synthetic.main.fragment_movie_details.*

class MovieDetailsFragment: BaseFragment() {
    override val layoutId = R.layout.fragment_movie_details

    private var moviesComponent: MoviesComponent? = null
    private lateinit var movieDetailsViewModel: MovieDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)

        injectMembers()

        movieDetailsViewModel = viewModel(viewModelFactory) {
            observe(viewState, ::handleViewState)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieDetailsViewModel.loadMovieDetails(arguments?.getLong(MOVIE_ID) ?: 0L)
    }

    private fun injectMembers() {
        moviesComponent = appComponent.plusMoviesComponent()
            .also { it.inject(this) }
    }

    private fun handleViewState(viewState: ViewState<MoviePresentationModel>?) {
        viewState?.let { state ->
            bindMovieDetails(state.data)
            progressDetails.visible(state.isLoading)
            state.errorMessage?.let { showMessage(it, contentDetails) }
        }
    }

    private fun bindMovieDetails(movie: MoviePresentationModel?) {
        movie?.let {
            collapsingToolbar.setExpandedTitleColor(Color.WHITE)
            collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE)
            toolbarDetails.title = it.title
            imgMoviePoster.loadFromUrl(PosterUrls.URL_780 + it.backdropPath)
            tvMovieTagLine.text = it.tagLine
            tvMovieOverview.text = it.overview
            ratingBarMovieDetails.rating = it.voteAverage.toFloat()
            tvMovieReleaseDate.text = it.releaseDate
            tvMovieBudget.text = it.budget.withDollar()
            tvMovieStatus.text = it.status
            tvAdult.visible(it.adult)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        moviesComponent = null
    }

    companion object{
        const val MOVIE_ID = "movie_details_id"
    }
}