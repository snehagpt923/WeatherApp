package com.example.weatherapp.di

import com.example.weatherapp.repository.MainRepository
import com.example.weatherapp.retrofit.BlogRetrofit
import com.example.weatherapp.retrofit.NetworkMapper
import com.example.weatherapp.room.BlogDao
import com.example.weatherapp.room.CacheMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        blogDao: BlogDao,
        blogRetrofit: BlogRetrofit,
        cacheMapper: CacheMapper,
        networkMapper: NetworkMapper
    ): MainRepository {
        return MainRepository(blogDao, blogRetrofit, cacheMapper, networkMapper)
    }
}