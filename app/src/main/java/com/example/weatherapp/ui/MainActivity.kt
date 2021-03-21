package com.example.weatherapp.ui

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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val viewModel: MainViewModel by viewModels()
    private var weatherData: WeatherData? = null

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
        weatherData?.isFavourite = isFavourite
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

    private fun loadData(weatherData: WeatherData) {
        this.weatherData = weatherData
        setIsFavouriteUi(weatherData.isFavourite)
        iv_favourite.visibility = View.VISIBLE
        tv_city.text = weatherData.cityName
        tv_max.text = String.format(getString(R.string.max_temp), weatherData.tempMax)
        tv_min.text = String.format(getString(R.string.min_temp), weatherData.tempMin)
        tv_temperature.text = String.format(getString(R.string.temperature), weatherData.temp)
        tv_feels_like.text = String.format(getString(R.string.feels_like), weatherData.feelsLike)
        group.visibility = View.VISIBLE
    }

    private fun initViews() {
        iv_favourite.setOnClickListener(this)
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
                weatherData?.cityName?.let {
                    val isFavourite = weatherData?.isFavourite ?: false
                    viewModel.markCityAsFavourite(isFavourite.not(), it)
                    setIsFavouriteUi(isFavourite.not())
                }
            }
        }
    }

    private fun showToast(message: String) = Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
}