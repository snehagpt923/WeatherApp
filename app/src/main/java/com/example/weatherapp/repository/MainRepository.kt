package com.example.weatherapp.repository

import android.content.Context
import com.example.weatherapp.R
import com.example.weatherapp.model.WeatherData
import com.example.weatherapp.retrofit.NetworkMapper
import com.example.weatherapp.retrofit.WeatherRetrofit
import com.example.weatherapp.room.CacheMapper
import com.example.weatherapp.room.WeatherDao
import com.example.weatherapp.util.DataState
import com.example.weatherapp.util.Utility
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainRepository
constructor(
    private val weatherDao: WeatherDao,
    private val weatherRetrofit: WeatherRetrofit,
    private val cacheMapper: CacheMapper,
    private val networkMapper: NetworkMapper,
    private val context: Context
) {
    suspend fun getCityWeather(cityName: String): Flow<DataState<WeatherData>> = flow {
        emit(DataState.Loading)
        try {
            if (Utility.isNetworkAvailable(context)) {
                val weatherNetworkData = weatherRetrofit.getWeatherData(cityName, Utility.APP_ID)
                val weatherData = networkMapper.mapFromEntity(weatherNetworkData)
                val isFavouriteMarked = weatherDao.isFavourite(cityName) ?: false
                weatherDao.insert(cacheMapper.mapToEntity(weatherData))
                markFavourite(isFavouriteMarked, cityName)
            }
            val cachedWeatherData = weatherDao.get(cityName)
            if (cachedWeatherData != null) {
                emit(DataState.Success(cacheMapper.mapFromEntity(cachedWeatherData)))
            } else {
                emit(DataState.Error(Exception(String.format(context.getString(R.string.no_data_found), cityName))))
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun markFavourite(isFavourite: Boolean, cityName: String) {
        weatherDao.markFavourite(isFavourite, cityName)
    }
}