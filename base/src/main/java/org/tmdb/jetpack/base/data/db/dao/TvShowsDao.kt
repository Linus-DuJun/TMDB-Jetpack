package org.tmdb.jetpack.base.data.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import org.tmdb.jetpack.base.data.db.entity.TvShowEntity

@Dao
abstract class TvShowsDao : BaseDao<TvShowEntity>() {

    @Query("SELECT * FROM TvShows")
    abstract fun query(): PagingSource<Int, TvShowEntity>

    @Query("DELETE FROM TvShows")
    abstract fun delete()
}