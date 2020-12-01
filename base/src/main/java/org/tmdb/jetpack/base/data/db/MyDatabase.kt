package org.tmdb.jetpack.base.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.tmdb.jetpack.base.data.db.dao.MovieDao
import org.tmdb.jetpack.base.data.db.dao.PeopleDao
import org.tmdb.jetpack.base.data.db.dao.RemoteKeyDao
import org.tmdb.jetpack.base.data.db.dao.TvShowsDao
import org.tmdb.jetpack.base.data.db.entity.MovieEntity
import org.tmdb.jetpack.base.data.db.entity.PeopleEntity
import org.tmdb.jetpack.base.data.db.entity.RemoteKeyEntity
import org.tmdb.jetpack.base.data.db.entity.TvShowEntity

@Database(entities = [MovieEntity::class, PeopleEntity::class, TvShowEntity::class, RemoteKeyEntity::class], version = 1)
abstract class MyDatabase : RoomDatabase() {

    abstract fun remoteKeyDao(): RemoteKeyDao
    abstract fun moviesDao(): MovieDao
    abstract fun tvShowsDao(): TvShowsDao
    abstract fun peopleDao(): PeopleDao

    companion object {
        private const val DATABASE_NAME = "TMDB_Database"
        @Volatile private var instance: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase {
            if (instance == null) {
                instance = buildDatabase(context)
            }
            return instance!!
        }

        @Synchronized
        private fun buildDatabase(context: Context): MyDatabase {
            return Room.databaseBuilder(context.applicationContext, MyDatabase::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    // Create and pre-populate the database. See this article for more details:
                    // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
                })
                .build()
        }
    }
}