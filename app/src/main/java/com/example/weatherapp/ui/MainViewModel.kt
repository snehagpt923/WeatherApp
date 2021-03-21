package com.example.weatherapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.WeatherData
import com.example.weatherapp.repository.MainRepository
import com.example.weatherapp.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val weatherLiveData: MutableLiveData<DataState<WeatherData>> = MutableLiveData()

    val getWeatherLiveData: LiveData<DataState<WeatherData>>
        get() = weatherLiveData

    fun getCityWeather(cityName: String) {
        viewModelScope.launch {
            mainRepository.getCityWeather(cityName).onEach {
                weatherLiveData.value = it
            }.launchIn(viewModelScope)
        }
    }

    fun markCityAsFavourite(isFavourite: Boolean, cityName: String) {
        viewModelScope.launch {
            mainRepository.markFavourite(isFavourite, cityName)
        }
    }
}