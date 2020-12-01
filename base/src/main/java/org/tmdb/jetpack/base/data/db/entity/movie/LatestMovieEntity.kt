package org.tmdb.jetpack.base.data.db.entity.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LatestMovie")
class LatestMovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val overview: String,
    val popularity: Number,
    @ColumnInfo(name = "poster_path") val posterPath: String,
    @ColumnInfo(name = "original_title") val originalTitle: String,
    @ColumnInfo(name = "vote_average") val voteAverage: Number,
    @ColumnInfo(name = "vote_count") val voteCount: Int
)