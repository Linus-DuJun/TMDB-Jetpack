package org.tmdb.jetpack.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import org.tmdb.jetpack.feature.movie.data.repository.MovieRepositoryImpl
import org.tmdb.jetpack.feature.movie.domain.repository.MovieRepository

@InstallIn(ActivityComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindMovieRepository(repository: MovieRepositoryImpl): MovieRepository
}