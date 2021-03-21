package com.example.weatherapp.di

import android.content.Context
import com.example.weatherapp.repository.MainRepository
import com.example.weatherapp.retrofit.WeatherRetrofit
import com.example.weatherapp.retrofit.NetworkMapper
import com.example.weatherapp.room.CacheMapper
import com.example.weatherapp.room.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        weatherDao: WeatherDao,
        weatherRetrofit: WeatherRetrofit,
        cacheMapper: CacheMapper,
        networkMapper: NetworkMapper,
        @ApplicationContext context: Context
    ): MainRepository {
        return MainRepository(weatherDao, weatherRetrofit, cacheMapper, networkMapper, context)
    }
}