package com.tmdbtestapp.data.repository

import com.tmdbtestapp.common.base.BaseRepository
import com.tmdbtestapp.common.utils.DataState
import com.tmdbtestapp.data.local.room.dao.FavoriteMoviesDao
import com.tmdbtestapp.data.remote.api.MovieApi
import com.tmdbtestapp.data.remote.mapper.MovieDetailsMapper
import com.tmdbtestapp.data.remote.mapper.MovieMapper
import com.tmdbtestapp.domain.entity.MovieDetails
import com.tmdbtestapp.domain.entity.movie.Movie
import com.tmdbtestapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieMapper: MovieMapper,
    private val movieDetailsMapper: MovieDetailsMapper,
    private val favoriteMoviesDao: FavoriteMoviesDao,
    private val movieApi: MovieApi
) : BaseRepository(), MovieRepository {
    override suspend fun getPopularMovies(): DataState<List<Movie>> {
        return obtain(
            response = movieApi.getPopularMovies(),
            mapper = { movieMapper.mapFromDto(it.results) }
        )
    }

    override suspend fun getNowPlayingMovies(): DataState<List<Movie>> {
        return obtain(
            response = movieApi.getNowPlayingMovies(),
            mapper = { movieMapper.mapFromDto(it.results) }
        )
    }

    override suspend fun getFavoriteMovies(): Flow<List<Movie>> {
        return favoriteMoviesDao.getAllFavorites().map { movieMapper.mapFromEntity(it) }
    }

    override suspend fun isMovieFavoriteFlow(id: Int): Flow<Boolean> {
        return favoriteMoviesDao.getFavoriteMovie(id)
            .map { it != null }
    }

    override suspend fun addToFavorites(movie: Movie) {
        val mappedMovie = movieMapper.mapToEntity(movie)

        favoriteMoviesDao.addToFavorites(mappedMovie)
    }

    override suspend fun removeFromFavorites(id: Int) {
        favoriteMoviesDao.removeFromFavorites(id)
    }

    override suspend fun getMovieDetails(id: Int): DataState<MovieDetails> {
        return obtain(
            response = movieApi.getMovieDetails(id),
            mapper = { movieDetailsMapper.map(it) }
        )
    }
}