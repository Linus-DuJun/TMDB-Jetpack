package org.tmdb.jetpack.feature.movie.data.network.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import org.tmdb.jetpack.base.data.db.MyDatabase
import org.tmdb.jetpack.base.data.db.entity.MovieEntity
import org.tmdb.jetpack.base.data.db.entity.RemoteKeyEntity
import org.tmdb.jetpack.feature.movie.data.mapper.toMovieEntity
import org.tmdb.jetpack.feature.movie.data.network.api.MovieService
import retrofit2.HttpException
import java.io.IOException

const val TYPE = "Movie"

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val db: MyDatabase,
    private val movieService: MovieService
) : RemoteMediator<Int, MovieEntity>() {

    private val movieDao = db.moviesDao()
    private val remoteKeyDao = db.remoteKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                //In this demo, we never need to prepend. since REFRESH will always
                // load the first page in the list. Immediately return, reporting end of pagination
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = db.withTransaction {
                        remoteKeyDao.remoteKeyByType(TYPE)
                    }
                    remoteKey.nextPage
                }
            }
            // Suspending network load via Retrofit. This doesn't need to be wrapped in a withContext(Dispatcher.IO){...} block
            // since Retrofit's Coroutine CallAdapter dispatches on a worker thread
            val response = movieService.getTopRatedMovies(pageIndex = loadKey)
            val entries = response.results
            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    remoteKeyDao.deleteByType(TYPE)
                    movieDao.deleteAll()
                }
                remoteKeyDao.insert(RemoteKeyEntity(TYPE, response.page + 1))
                // Insert new movies into database, which invalidates the current PagingData,
                // allowing Paging to present the updates in the DB
                movieDao.insert(entries.map { it.toMovieEntity() })
            }
            MediatorResult.Success(endOfPaginationReached = response.page == response.totalPages)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}