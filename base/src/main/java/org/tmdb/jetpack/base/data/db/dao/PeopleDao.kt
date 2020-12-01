package org.tmdb.jetpack.base.data.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import org.tmdb.jetpack.base.data.db.entity.PeopleEntity

@Dao
abstract class PeopleDao : BaseDao<PeopleEntity>() {

    @Query("SELECT * FROM People")
    abstract fun query(): PagingSource<Int, PeopleEntity>

    @Query("DELETE FROM People")
    abstract fun delete()
}