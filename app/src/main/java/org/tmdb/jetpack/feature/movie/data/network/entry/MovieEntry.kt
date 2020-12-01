package org.tmdb.jetpack.feature.movie.data.network.entry


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieEntry(
    val id: Int,
    val title: String,
    val overview: String,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "original_title") val originalTitle: String,
    @Json(name = "vote_count") val voteCount: Int
)
