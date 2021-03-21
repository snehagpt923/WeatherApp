package com.example.weatherapp.room

import com.example.weatherapp.model.WeatherData
import com.example.weatherapp.util.EntityMapper
import javax.inject.Inject

class CacheMapper
@Inject constructor() : EntityMapper<WeatherCacheEntity, WeatherData> {
    override fun mapFromEntity(entity: WeatherCacheEntity): WeatherData {
        return WeatherData(
            id = entity.id,
            lon = entity.lon,
            lat = entity.lat,
            cloudsdescription = entity.cloudsdescription,
            temp = entity.temp,
            feelsLike = entity.feelsLike,
            tempMin = entity.tempMin,
            tempMax = entity.tempMax,
            pressure = entity.pressure,
            humidity = entity.humidity,
            windSpeed = entity.windSpeed,
            sunrise = entity.sunrise,
            sunset = entity.sunset,
            cityName = entity.cityName,
            isFavourite = entity.isFavourite
        )
    }

    override fun mapToEntity(domainModel: WeatherData): WeatherCacheEntity {
        return WeatherCacheEntity(
            id = domainModel.id,
            lon = domainModel.lon,
            lat = domainModel.lat,
            cloudsdescription = domainModel.cloudsdescription,
            temp = domainModel.temp,
            feelsLike = domainModel.feelsLike,
            tempMin = domainModel.tempMin,
            tempMax = domainModel.tempMax,
            pressure = domainModel.pressure,
            humidity = domainModel.humidity,
            windSpeed = domainModel.windSpeed,
            sunrise = domainModel.sunrise,
            sunset = domainModel.sunset,
            cityName = domainModel.cityName,
            isFavourite = false
        )
    }
}