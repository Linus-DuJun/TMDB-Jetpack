package org.tmdb.jetpack.feature.movie.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.tmdb.jetpack.feature.movie.domain.model.Movie

interface MovieRepository {

    fun getTopRatedMovies(pageSize: Int): Flow<PagingData<Movie>>

}