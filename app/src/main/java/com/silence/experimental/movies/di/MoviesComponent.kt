package com.silence.experimental.movies.di

import com.silence.experimental.movies.presentation.MoviesFragment
import dagger.Subcomponent

@MoviesScope
@Subcomponent
interface MoviesComponent {
    fun inject(fragment: MoviesFragment)
}