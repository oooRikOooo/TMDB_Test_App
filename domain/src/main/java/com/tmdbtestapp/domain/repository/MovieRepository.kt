package com.tmdbtestapp.domain.repository

import com.tmdbtestapp.domain.entity.movie.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getPopularMovies(): Result<List<Movie>>

    suspend fun getNowPlayingMovies(): Result<List<Movie>>

    fun getFavoriteMovies(): Flow<List<Movie>>

    fun isMovieFavoriteFlow(id: Int): Flow<Boolean>

    suspend fun addToFavorites(movie: Movie)

    suspend fun removeFromFavorites(id: Int)

    suspend fun getMovieDetails(id: Int): Result<Movie>

    suspend fun getVideoUrl(movieId: Int): Result<String>
}