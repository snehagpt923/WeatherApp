package com.example.weatherapp.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class WeatherCacheEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Long? = null,

    @ColumnInfo(name = "lon")
    var lon: Double? = null,

    @ColumnInfo(name = "lat")
    var lat: Double? = null,

    @ColumnInfo(name = "cloudsdescription")
    var cloudsdescription: String? = null,

    @ColumnInfo(name = "temp")
    var temp: Double? = null,

    @ColumnInfo(name = "feelsLike")
    var feelsLike: Double? = null,

    @ColumnInfo(name = "tempMin")
    var tempMin: Double? = null,

    @ColumnInfo(name = "tempMax")
    var tempMax: Double? = null,

    @ColumnInfo(name = "pressure")
    var pressure: Int? = null,

    @ColumnInfo(name = "humidity")
    var humidity: Int? = null,

    @ColumnInfo(name = "windSpeed")
    var windSpeed: Double? = null,

    @ColumnInfo(name = "sunrise")
    var sunrise: Int? = null,

    @ColumnInfo(name = "sunset")
    var sunset: Int? = null,

    @ColumnInfo(name = "cityName")
    var cityName: String? = null,

    @ColumnInfo(name = "isFavourite")
    var isFavourite: Boolean = false
)