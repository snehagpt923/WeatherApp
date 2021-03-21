package com.example.weatherapp.retrofit

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherRetrofit {

    @GET("weather")
    suspend fun getWeatherData(
        @Query("q") cityName: String,
        @Query("appid") AppId: String
    ): WeatherNetworkEntity
}