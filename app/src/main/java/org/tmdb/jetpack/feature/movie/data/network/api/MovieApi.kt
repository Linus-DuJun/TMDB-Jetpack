package org.tmdb.jetpack.feature.movie.data.network.api

import org.tmdb.jetpack.feature.movie.data.network.entry.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("/3/movie/top_rated")
    suspend fun getMovies(
        @Query ("page") page: Int
    ): MovieResponse

}