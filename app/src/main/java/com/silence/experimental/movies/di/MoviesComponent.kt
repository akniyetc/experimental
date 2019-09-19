package com.silence.experimental.movies.di

import com.silence.experimental.movies.presentation.MoviesFragment
import dagger.Subcomponent

@MoviesScope
@Subcomponent/*(modules = [MoviesModule::class])*/
interface MoviesComponent {
    fun inject(fragment: MoviesFragment)
}