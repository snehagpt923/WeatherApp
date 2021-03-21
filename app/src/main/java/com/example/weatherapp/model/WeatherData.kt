package com.example.weatherapp.model

data class WeatherData(

    var id: Long? = null,

    var lon: Double? = null,

    var lat: Double? = null,

    var cloudsDescription: String? = null,

    var temp: Double? = null,

    var feelsLike: Double? = null,

    var tempMin: Double? = null,

    var tempMax: Double? = null,

    var pressure: Int? = null,

    var humidity: Int? = null,

    var windSpeed: Double? = null,

    var sunrise: Long? = null,

    var sunset: Long? = null,

    var cityName: String? = null,

    var country: String? = null,

    var dateTime: Long? = null,

    var isFavourite: Boolean = false
)