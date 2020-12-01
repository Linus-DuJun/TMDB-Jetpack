package org.tmdb.jetpack.base.data.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import org.tmdb.jetpack.base.data.db.entity.MovieEntity

@Dao
abstract class MovieDao : BaseDao<MovieEntity>() {

    @Query("SELECT * FROM Movies")
    abstract fun query(): PagingSource<Int, MovieEntity>

    @Query("DELETE FROM Movies")
    abstract fun deleteAll()
}