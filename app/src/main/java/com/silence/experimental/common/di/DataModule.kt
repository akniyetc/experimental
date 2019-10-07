package com.silence.experimental.common.di

import com.silence.experimental.common.data.ExperimentalDataBase
import com.silence.experimental.common.data.MainService
import com.silence.experimental.common.data.NetworkHandler
import com.silence.experimental.common.data.PreferenceHelper
import com.silence.experimental.movies.data.cache.MoviesCache
import com.silence.experimental.movies.data.cache.MoviesCacheImpl
import com.silence.experimental.movies.data.remote.MoviesRemote
import com.silence.experimental.movies.data.remote.MoviesRemoteImpl
import com.silence.experimental.movies.data.repository.MoviesRepositoryImpl
import com.silence.experimental.movies.domain.repository.MoviesRepository
import com.silence.experimental.movies.domain.usecase.GetMovieDetails
import com.silence.experimental.movies.domain.usecase.GetMovies
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideMoviesCache(
        db: ExperimentalDataBase,
        preferenceHelper: PreferenceHelper
    ): MoviesCache = MoviesCacheImpl(db, preferenceHelper)

    @Singleton
    @Provides
    fun provideMoviesRemote(
        service: MainService,
        networkHandler: NetworkHandler,
        moviesCache: MoviesCache
    ): MoviesRemote = MoviesRemoteImpl(service, networkHandler, moviesCache)

    @Singleton
    @Provides
    fun provideMoviesRepository(repository: MoviesRepositoryImpl): MoviesRepository = repository

    @Singleton
    @Provides
    fun provideMoviesUseCase(moviesRepository: MoviesRepository) = GetMovies(moviesRepository)

    @Singleton
    @Provides
    fun provideMovieDetailsUseCase(moviesRepository: MoviesRepository) =
        GetMovieDetails(moviesRepository)
}