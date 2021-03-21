package com.example.weatherapp.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

object Utility {

    private const val EMPTY = ""
    const val APP_ID = "4f783b05ec6a0aa36286ec0e92e639f9"
    const val BASE_URL = "http://api.openweathermap.org/data/2.5/"
    const val TIME_FORMAT = "hh:mm a"
    const val DATE_TIME_FORMAT = "EEE, dd MMM, hh:mm a"

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val activeNetworkInfo = connectivityManager?.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    @SuppressLint("SimpleDateFormat")
    fun readableDateTime(timeStamp: Long?, format: String): String {
        if (timeStamp == null) return EMPTY
        val date = Date(timeStamp)
        val sdf = SimpleDateFormat(format)
        return sdf.format(date)
    }

    fun temperatureConverter(kelvin: Double?): String {
        if (kelvin == null) return EMPTY
        val df = DecimalFormat("#.#")
        val celsius = df.format(kelvin - 273.15F)
        return "$celsius°C"
    }

    fun humidityConverter(humidity: Int?): String {
        if (humidity != null) EMPTY
        return "$humidity %"
    }
}