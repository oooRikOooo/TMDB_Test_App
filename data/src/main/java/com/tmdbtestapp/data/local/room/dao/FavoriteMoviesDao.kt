package com.tmdbtestapp.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tmdbtestapp.data.local.room.entity.FavoriteMovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMoviesDao {
    @Insert
    suspend fun addToFavorites(movieEntity: FavoriteMovieEntity)

    @Query("DELETE FROM favorite_movies WHERE _id =:id")
    suspend fun removeFromFavorites(id: Int)

    @Query("SELECT * FROM favorite_movies WHERE _id = :id")
    fun getFavoriteMovie(id: Int): Flow<FavoriteMovieEntity?>

    @Query("SELECT * FROM favorite_movies")
    fun getAllFavorites(): Flow<List<FavoriteMovieEntity>>
}