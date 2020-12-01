package org.tmdb.jetpack.feature.movie.data.network.api

import retrofit2.Retrofit
import javax.inject.Inject

class MovieService @Inject constructor(
    private val retrofit: Retrofit
) {
    private val movieApi: MovieApi by lazy { retrofit.create(MovieApi::class.java) }

    suspend fun getTopRatedMovies(pageIndex: Int) = movieApi.getMovies(pageIndex)


}