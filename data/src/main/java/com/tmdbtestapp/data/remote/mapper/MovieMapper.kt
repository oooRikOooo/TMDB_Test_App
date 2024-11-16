package com.tmdbtestapp.data.remote.mapper

import com.tmdbtestapp.data.local.room.entity.FavoriteMovieEntity
import com.tmdbtestapp.data.remote.entity.common.MovieDto
import com.tmdbtestapp.data.remote.entity.movieDetails.MovieDetailsDto
import com.tmdbtestapp.domain.entity.movie.Movie
import javax.inject.Inject

class MovieMapper @Inject constructor() {
    private fun mapMovieDto(movieDto: MovieDto?): Movie? {
        movieDto ?: return null

        return Movie(
            id = movieDto.id ?: return null,
            title = movieDto.title.orEmpty(),
            posterPath = movieDto.posterPath.orEmpty()
        )
    }

    fun mapMovieDto(movieDetailsDto: MovieDetailsDto?): Movie? {
        movieDetailsDto ?: return null

        return Movie(
            id = movieDetailsDto.id ?: return null,
            title = movieDetailsDto.title.orEmpty(),
            posterPath = movieDetailsDto.posterPath.orEmpty(),
            description = movieDetailsDto.overview.orEmpty(),
            genres = movieDetailsDto.genres?.mapNotNull { it.name } ?: emptyList(),
            backdropPath = movieDetailsDto.backdropPath.orEmpty(),
            releaseDate = movieDetailsDto.releaseDate.orEmpty(),
            voteAverage = movieDetailsDto.voteAverage.toString()
        )
    }

    private fun mapFavoriteMovieEntity(entity: FavoriteMovieEntity): Movie {
        return Movie(
            id = entity.id,
            title = entity.title,
            posterPath = entity.posterPath
        )
    }

    fun mapMovieDtoList(dtoList: List<MovieDto>?): List<Movie> {
        dtoList ?: return emptyList()

        return dtoList.mapNotNull { mapMovieDto(it) }
    }

    fun mapFavoriteMovieEntityList(dtoList: List<FavoriteMovieEntity>): List<Movie> {
        return dtoList.map { mapFavoriteMovieEntity(it) }
    }

    fun mapMovie(movie: Movie): FavoriteMovieEntity {
        return FavoriteMovieEntity(
            id = movie.id,
            title = movie.title,
            posterPath = movie.posterPath
        )
    }
}