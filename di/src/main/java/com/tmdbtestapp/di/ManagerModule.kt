package com.tmdbtestapp.di

import com.tmdbtestapp.common.manager.ImageUrlManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ManagerModule {

    @Provides
    @Singleton
    fun provideImageUrlManager() = ImageUrlManager()

}