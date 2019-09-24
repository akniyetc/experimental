package com.silence.experimental.movies.di

import com.silence.experimental.movies.presentation.details.MovieDetailsFragment
import com.silence.experimental.movies.presentation.list.MoviesFragment
import dagger.Subcomponent

@MoviesScope
@Subcomponent
interface MoviesComponent {
    fun inject(fragment: MoviesFragment)
    fun inject(fragment: MovieDetailsFragment)
}