package org.tmdb.jetpack.base.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TvShows")
data class TvShowEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val overview: String,
    @ColumnInfo(name = "poster_path") val posterPath: String,
)