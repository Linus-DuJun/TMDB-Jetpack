package org.tmdb.jetpack.base.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Movies")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val overview: String,
    @ColumnInfo(name = "poster_path") val posterPath: String,
    @ColumnInfo(name = "original_title") val originalTitle: String,
    @ColumnInfo(name = "vote_count") val voteCount: Int
)