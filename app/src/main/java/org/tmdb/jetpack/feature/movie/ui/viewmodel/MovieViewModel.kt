package org.tmdb.jetpack.feature.movie.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.paging.map
import kotlinx.coroutines.flow.map
import org.tmdb.jetpack.base.core.utils.SingleLiveEvent
import org.tmdb.jetpack.feature.movie.domain.model.Movie
import org.tmdb.jetpack.feature.movie.domain.repository.MovieRepository
import org.tmdb.jetpack.feature.movie.ui.mapper.toMovieAdapterItem

class MovieViewModel @ViewModelInject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    val movieItems = movieRepository
        .getTopRatedMovies()
        .map {
            it.map {
                it.toMovieAdapterItem(onMovieClickEvent)
            }
        }

    val onMovieClickEvent = SingleLiveEvent<Movie>()
}