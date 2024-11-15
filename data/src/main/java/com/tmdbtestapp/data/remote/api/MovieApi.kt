package com.tmdbtestapp.data.remote.api

import com.tmdbtestapp.data.remote.entity.movieDetails.MovieDetailsDto
import com.tmdbtestapp.data.remote.entity.nowPlayingMovies.NowPlayingMoviesDto
import com.tmdbtestapp.data.remote.entity.popularMovies.PopularMoviesDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/popular")
    suspend fun getPopularMovies(): Response<PopularMoviesDto?>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(): Response<NowPlayingMoviesDto?>

    @GET("movie/{movieId}")
    suspend fun getMovieDetails(
        @Path("movieId") movieId: Int
    ): Response<MovieDetailsDto?>
}