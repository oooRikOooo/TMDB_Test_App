package com.tmdbtestapp.data.repository

import com.tmdbtestapp.common.BuildConfig
import com.tmdbtestapp.common.base.BaseRepository
import com.tmdbtestapp.data.local.room.dao.FavoriteMoviesDao
import com.tmdbtestapp.data.remote.api.MovieApi
import com.tmdbtestapp.data.remote.mapper.MovieMapper
import com.tmdbtestapp.domain.entity.movie.Movie
import com.tmdbtestapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieMapper: MovieMapper,
    private val favoriteMoviesDao: FavoriteMoviesDao,
    private val movieApi: MovieApi
) : BaseRepository(), MovieRepository {
    override suspend fun getPopularMovies(): Result<List<Movie>> {
        return obtain(
            response = movieApi.getPopularMovies(),
            mapper = { movieMapper.mapMovieDtoList(it.results) }
        )
    }

    override suspend fun getNowPlayingMovies(): Result<List<Movie>> {
        return obtain(
            response = movieApi.getNowPlayingMovies(),
            mapper = { movieMapper.mapMovieDtoList(it.results) }
        )
    }

    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return favoriteMoviesDao.getAllFavorites()
            .map { movieMapper.mapFavoriteMovieEntityList(it) }
    }

    override fun isMovieFavoriteFlow(id: Int): Flow<Boolean> {
        return favoriteMoviesDao.getFavoriteMovie(id)
            .map { it != null }
    }

    override suspend fun addToFavorites(movie: Movie) {
        val mappedMovie = movieMapper.mapMovie(movie)

        favoriteMoviesDao.addToFavorites(mappedMovie)
    }

    override suspend fun removeFromFavorites(id: Int) {
        favoriteMoviesDao.removeFromFavorites(id)
    }

    override suspend fun getMovieDetails(id: Int): Result<Movie> {
        return obtain(
            response = movieApi.getMovieDetails(id),
            mapper = { movieMapper.mapMovieDto(it) }
        )
    }

    override suspend fun getVideoUrl(movieId: Int): Result<String> {
        return Result.success(BuildConfig.VIDEO_URL)
    }
}