package org.tmdb.jetpack.feature.movie.ui.mapper

import org.tmdb.jetpack.base.core.utils.SingleLiveEvent
import org.tmdb.jetpack.feature.movie.domain.model.Movie
import org.tmdb.jetpack.feature.movie.ui.item.MovieAdapterItem

fun Movie.toMovieAdapterItem(
    onClickEvent: SingleLiveEvent<Movie>,
) = MovieAdapterItem(
    id = id,
    title = title
).also {
    it.domainModel = this
    it.onClickEvent = onClickEvent
}