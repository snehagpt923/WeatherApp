package com.example.weatherapp.retrofit

import com.example.weatherapp.model.WeatherData
import com.example.weatherapp.util.EntityMapper
import javax.inject.Inject

class NetworkMapper
@Inject constructor() : EntityMapper<WeatherNetworkEntity, WeatherData> {
    override fun mapFromEntity(entity: WeatherNetworkEntity): WeatherData {
        return WeatherData(
            id = entity.id,
            lon = entity.coord?.lon,
            lat = entity.coord?.lat,
            cloudsDescription = entity.weather?.get(0)?.description,
            temp = entity.main?.temp,
            feelsLike = entity.main?.feelsLike,
            tempMin = entity.main?.tempMin,
            tempMax = entity.main?.tempMax,
            pressure = entity.main?.pressure,
            humidity = entity.main?.humidity,
            windSpeed = entity.wind?.speed,
            sunrise = entity.sys?.sunrise,
            sunset = entity.sys?.sunset,
            cityName = entity.name,
            country = entity.sys?.country,
            dateTime = entity.dt
        )
    }

    override fun mapToEntity(domainModel: WeatherData): WeatherNetworkEntity {
        return WeatherNetworkEntity()
    }
}