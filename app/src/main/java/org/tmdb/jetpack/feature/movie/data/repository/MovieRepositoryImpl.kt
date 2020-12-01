package org.tmdb.jetpack.feature.movie.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.tmdb.jetpack.base.data.db.MyDatabase
import org.tmdb.jetpack.feature.movie.data.mapper.toMovie
import org.tmdb.jetpack.feature.movie.data.network.api.MovieService
import org.tmdb.jetpack.feature.movie.data.network.datasource.MovieRemoteMediator
import org.tmdb.jetpack.feature.movie.domain.model.Movie
import org.tmdb.jetpack.feature.movie.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieService: MovieService,
    private val db: MyDatabase
) : MovieRepository {
    override fun getTopRatedMovies(): Flow<PagingData<Movie>> {
        val movieDao = db.moviesDao()
        return Pager(
            config = PagingConfig(pageSize = 1, prefetchDistance = 1, enablePlaceholders = false),
            remoteMediator = MovieRemoteMediator(db = db, movieService),
            pagingSourceFactory = { movieDao.query() }
        ).flow.map {
            it.map {
                it.toMovie()
            }
        }
    }
}