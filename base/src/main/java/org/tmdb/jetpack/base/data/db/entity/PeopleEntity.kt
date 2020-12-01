package org.tmdb.jetpack.base.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "People")
class PeopleEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val gender: Int,
)