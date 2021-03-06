package com.example.weatherapp.repository

import com.example.weatherapp.model.Blog
import com.example.weatherapp.retrofit.BlogRetrofit
import com.example.weatherapp.retrofit.NetworkMapper
import com.example.weatherapp.room.BlogDao
import com.example.weatherapp.room.CacheMapper
import com.example.weatherapp.util.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import kotlinx.coroutines.flow.Flow

class MainRepository
constructor(
    private val blogDao: BlogDao,
    private val blogRetrofit: BlogRetrofit,
    private val cacheMapper: CacheMapper,
    private val networkMapper: NetworkMapper
) {
    suspend fun getBlogs(): Flow<DataState<List<Blog>>> = flow {
        emit(DataState.Loading)
        delay(1000)
        try {
            val networkBlogs = blogRetrofit.get()
            val blogs = networkMapper.mapFromEntityList(networkBlogs)
            for (blog in blogs) {
                blogDao.insert(cacheMapper.mapToEntity(blog))
            }
            val cachedBlogs = blogDao.get()
            emit(DataState.Success(cacheMapper.mapFromEntityList(cachedBlogs)))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}