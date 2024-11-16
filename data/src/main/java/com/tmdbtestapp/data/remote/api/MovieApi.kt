package com.tmdbtestapp.data.remote.api

import com.tmdbtestapp.data.remote.entity.movieDetails.MovieDetailsDto
import com.tmdbtestapp.data.remote.entity.nowPlayingMovies.NowPlayingMoviesDto
import com.tmdbtestapp.data.remote.entity.popularMovies.PopularMoviesDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {
    @GET(POPULAR_MOVIES)
    suspend fun getPopularMovies(): Response<PopularMoviesDto?>

    @GET(NOW_PLAYING_MOVIES)
    suspend fun getNowPlayingMovies(): Response<NowPlayingMoviesDto?>

    @GET(MOVIE_DETAILS)
    suspend fun getMovieDetails(
        @Path("movieId") movieId: Int
    ): Response<MovieDetailsDto?>

    companion object {
        private const val POPULAR_MOVIES = "movie/popular"
        private const val NOW_PLAYING_MOVIES = "movie/now_playing"
        private const val MOVIE_DETAILS = "movie/{movieId}"
    }
}