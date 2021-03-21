package com.example.weatherapp.util

import android.content.Context
import android.net.ConnectivityManager

object Utility {

    const val APP_ID = "4f783b05ec6a0aa36286ec0e92e639f9"
    const val BASE_URL = "http://api.openweathermap.org/data/2.5/"

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val activeNetworkInfo = connectivityManager?.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}