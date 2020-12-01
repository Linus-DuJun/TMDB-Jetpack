package org.tmdb.jetpack.feature.movie.data.mapper

import org.tmdb.jetpack.base.core.extensions.empty
import org.tmdb.jetpack.base.data.db.entity.MovieEntity
import org.tmdb.jetpack.feature.movie.data.network.entry.MovieEntry
import org.tmdb.jetpack.feature.movie.domain.model.Movie

fun MovieEntry.toMovieEntity() =
    MovieEntity(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath ?: String.empty(),
        originalTitle = originalTitle,
        voteCount = voteCount
    )

fun MovieEntity.toMovie() =
    Movie(
        id = id,
        title = title,
        posterPath = posterPath
    )