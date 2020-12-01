package org.tmdb.jetpack.base.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import org.tmdb.jetpack.base.data.db.entity.RemoteKeyEntity

@Dao
abstract class RemoteKeyDao : BaseDao<RemoteKeyEntity>() {

    @Query("SELECT * FROM RemoteKeys WHERE type = :type")
    abstract suspend fun remoteKeyByType(type: String): RemoteKeyEntity

    @Query("DELETE FROM RemoteKeys WHERE type = :type")
    abstract suspend fun deleteByType(type: String)
}