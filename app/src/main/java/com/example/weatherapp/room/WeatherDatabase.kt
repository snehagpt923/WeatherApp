package com.example.weatherapp.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WeatherCacheEntity::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

    companion object {
        const val DATABASE_NAME: String = "weather_db"
    }
}