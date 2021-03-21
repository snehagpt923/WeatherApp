package com.example.weatherapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.weatherapp.R
import com.example.weatherapp.model.WeatherData
import com.example.weatherapp.util.DataState
import com.example.weatherapp.util.Utility
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        subscribeObservers()
        initViews()
    }

    private fun subscribeObservers() {
        viewModel.getWeatherLiveData.observe(this, {
            when (it) {
                is DataState.Success<WeatherData> -> {
                    displayProgressBar(false)
                    loadData(it.data)
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    displayError(it.exception.message)
                }
                is DataState.Loading -> {
                    resetUI()
                    displayProgressBar(true)
                }
            }
        })
    }

    private fun resetUI() {
        tv_error.visibility = View.GONE
        group.visibility = View.GONE
        iv_favourite.visibility = View.GONE
    }

    private fun setIsFavouriteUi(isFavourite: Boolean) {
        val drawable = if (isFavourite) {
            ContextCompat.getDrawable(this, R.drawable.ic_filled_favorite)
        } else {
            ContextCompat.getDrawable(this, R.drawable.ic_outline_favorite)
        }
        iv_favourite.setImageDrawable(drawable)
    }

    private fun displayError(message: String?) {
        if (message != null) {
            tv_error.visibility = View.VISIBLE
            tv_error.text = message
        } else {
            tv_error.text = ""
            tv_error.visibility = View.GONE
        }
    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        progress_bar.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }

    @SuppressLint("SetTextI18n")
    private fun loadData(weatherData: WeatherData) {
        weatherData.apply {
            setIsFavouriteUi(isFavourite)
            iv_favourite.visibility = View.VISIBLE

            tv_city.text = "$cityName, $country"
            tv_lat.text = String.format(getString(R.string.lat_long), lat, lon)
            tv_dateTime.text = Utility.readableDateTime(dateTime, Utility.DATE_TIME_FORMAT)
            tv_max.text =
                String.format(getString(R.string.max_temp), Utility.temperatureConverter(tempMax))
            tv_min.text =
                String.format(getString(R.string.min_temp), Utility.temperatureConverter(tempMin))
            tv_temperature.text = Utility.temperatureConverter(temp)
            tv_feels_like.text = String.format(
                getString(R.string.feels_like),
                Utility.temperatureConverter(feelsLike)
            )
            tv_humidity.text =
                String.format(getString(R.string.humidity), Utility.humidityConverter(humidity))
            tv_pressure.text = String.format(getString(R.string.pressure), pressure)
            tv_clouds.text =
                String.format(getString(R.string.clouds_description), cloudsDescription)
            tv_sunrise.text = String.format(
                getString(R.string.sunrise),
                Utility.readableDateTime(sunrise, Utility.TIME_FORMAT)
            )
            tv_sunset.text = String.format(
                getString(R.string.sunset),
                Utility.readableDateTime(sunset, Utility.TIME_FORMAT)
            )
            tv_wind.text = String.format(getString(R.string.wind_speed), windSpeed)
        }
        group.visibility = View.VISIBLE
    }

    private fun initViews() {
        iv_favourite.setOnClickListener(this)
        tv_dateTime.setOnClickListener(this)
        et_city.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.isNullOrEmpty()) {
                    showToast(getString(R.string.empty_search_msg))
                } else {
                    viewModel.getCityWeather(query.capitalize(Locale.getDefault()))
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_favourite -> {
                (viewModel.getWeatherLiveData.value as? DataState.Success<WeatherData>)?.let {
                    val isFavourite = it.data.isFavourite
                    it.data.cityName?.let { cityName ->
                        viewModel.markCityAsFavourite(isFavourite.not(), cityName)
                    }
                    it.data.isFavourite = isFavourite.not()
                    setIsFavouriteUi(isFavourite.not())
                }
            }
            R.id.tv_dateTime -> {
                if (et_city.query.isEmpty()) {
                    showToast(getString(R.string.empty_search_msg))
                } else {
                    viewModel.getCityWeather(et_city.query.toString())
                }
            }
        }
    }

    private fun showToast(message: String) =
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
}