package com.tmdbtestapp.domain.repository

import com.tmdbtestapp.common.utils.DataState
import com.tmdbtestapp.domain.entity.MovieDetails
import com.tmdbtestapp.domain.entity.movie.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getPopularMovies(): DataState<List<Movie>>

    suspend fun getNowPlayingMovies(): DataState<List<Movie>>

    suspend fun getFavoriteMovies(): Flow<List<Movie>>

    suspend fun isMovieFavoriteFlow(id: Int): Flow<Boolean>

    suspend fun addToFavorites(movie: Movie)

    suspend fun removeFromFavorites(id: Int)

    suspend fun getMovieDetails(id: Int): DataState<MovieDetails>
}