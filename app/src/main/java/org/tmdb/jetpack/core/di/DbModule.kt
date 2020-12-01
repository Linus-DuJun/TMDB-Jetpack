package org.tmdb.jetpack.core.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import org.tmdb.jetpack.base.data.db.MyDatabase
import org.tmdb.jetpack.base.data.db.dao.MovieDao
import org.tmdb.jetpack.base.data.db.dao.PeopleDao
import org.tmdb.jetpack.base.data.db.dao.TvShowsDao
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class DbModule {

    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext context: Context): MyDatabase {
        return MyDatabase.getInstance(context)
    }

    @Provides
    fun providesMoviesDao(db: MyDatabase): MovieDao {
        return db.moviesDao()
    }

    @Provides
    fun providesPeopleDao(db: MyDatabase): PeopleDao {
        return db.peopleDao()
    }

    @Provides
    fun providesTvShowsDao(db: MyDatabase): TvShowsDao {
        return db.tvShowsDao()
    }
}