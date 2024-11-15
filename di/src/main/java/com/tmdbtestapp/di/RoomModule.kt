package com.tmdbtestapp.di

import android.content.Context
import androidx.room.Room
import com.tmdbtestapp.data.local.room.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(
        @ApplicationContext context: Context,
    ): MovieDatabase = Room.databaseBuilder(
        context,
        MovieDatabase::class.java,
        "tmdb_test_app.db"
    )
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideFavoritesDao(
        database: MovieDatabase,
    ) = database.favoritesDao()

}