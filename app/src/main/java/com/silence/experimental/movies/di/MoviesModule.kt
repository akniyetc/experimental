package com.silence.experimental.movies.di

import com.silence.experimental.common.data.ExperimentalDataBase
import com.silence.experimental.common.data.MainService
import com.silence.experimental.common.data.NetworkHandler
import com.silence.experimental.movies.data.cache.MoviesCache
import com.silence.experimental.movies.data.remote.MoviesRemote
import com.silence.experimental.movies.data.repository.MoviesRepositoryImpl
import com.silence.experimental.movies.domain.repository.MoviesRepository
import com.silence.experimental.movies.domain.usecase.GetMovies
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MoviesModule {

    /*@MoviesScope
    @Provides
    fun provideMoviesCache(db: ExperimentalDataBase) = MoviesCache(db)

    @MoviesScope
    @Provides
    fun provideMoviesRemote(
        service: MainService,
        networkHandler: NetworkHandler,
        moviesCache: MoviesCache
    ) = MoviesRemote(service, networkHandler, moviesCache)*/

    @MoviesScope
    @Provides
    fun provideMoviesRepository(repository: MoviesRepositoryImpl) = repository

    /*@MoviesScope
    @Provides
    fun provideMoviesUseCase(moviesRepository: MoviesRepository) = GetMovies(moviesRepository)*/
}