package com.tmdbtestapp.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tmdbtestapp.data.local.room.dao.FavoriteMoviesDao
import com.tmdbtestapp.data.local.room.entity.FavoriteMovieEntity

@Database(entities = [FavoriteMovieEntity::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoriteMoviesDao
}