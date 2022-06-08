package com.example.catstagram.di

import com.example.catstagram.feature_catlist.data.network.RetrofitConfig
import com.example.catstagram.feature_catlist.data.network.TheCatApiService

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @ExperimentalSerializationApi
    @Singleton
    @Provides

    fun provideCatService(): TheCatApiService = RetrofitConfig.theCatApiService
}
