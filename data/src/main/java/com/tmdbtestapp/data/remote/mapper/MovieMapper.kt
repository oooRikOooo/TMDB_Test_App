package com.tmdbtestapp.data.remote.mapper

import com.tmdbtestapp.data.local.room.entity.FavoriteMovieEntity
import com.tmdbtestapp.data.remote.entity.common.MovieDto
import com.tmdbtestapp.domain.entity.movie.Movie
import javax.inject.Inject

class MovieMapper @Inject constructor() {
    private fun map(dto: MovieDto?): Movie? {
        dto ?: return null

        return Movie(
            id = dto.id ?: return null,
            title = dto.title.orEmpty(),
            posterPath = dto.posterPath.orEmpty()
        )
    }

    fun map(entity: FavoriteMovieEntity): Movie {
        return Movie(
            id = entity.id,
            title = entity.title,
            posterPath = entity.posterPath
        )
    }

    fun mapFromDto(dtoList: List<MovieDto>?): List<Movie> {
        dtoList ?: return emptyList()

        return dtoList.mapNotNull { map(it) }
    }

    fun mapFromEntity(dtoList: List<FavoriteMovieEntity>): List<Movie> {
        return dtoList.map { map(it) }
    }

    fun mapToEntity(movie: Movie): FavoriteMovieEntity {
        return FavoriteMovieEntity(
            id = movie.id,
            title = movie.title,
            posterPath = movie.posterPath
        )
    }
}