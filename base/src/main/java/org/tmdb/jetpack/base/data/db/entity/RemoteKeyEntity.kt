package org.tmdb.jetpack.base.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RemoteKeys")
data class RemoteKeyEntity(
    @PrimaryKey val type: String,
    val nextPage: Int
)