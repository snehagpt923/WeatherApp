package com.example.weatherapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weatherCacheEntity: WeatherCacheEntity): Long

    @Query("SELECT * FROM weather where cityName = :cityName")
    suspend fun get(cityName: String): WeatherCacheEntity?

    @Query("UPDATE weather SET isFavourite = :isFavourite WHERE cityName = :cityName")
    suspend fun markFavourite(isFavourite: Boolean, cityName: String)

    @Query("SELECT isFavourite FROM weather WHERE cityName = :cityName")
    suspend fun isFavourite(cityName: String): Boolean?
}