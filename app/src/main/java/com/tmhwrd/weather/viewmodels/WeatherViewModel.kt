package com.tmhwrd.weather.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.tmhwrd.weather.db.getDatabase
import com.tmhwrd.weather.network.Forecast
import com.tmhwrd.weather.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel(application: Application) : AndroidViewModel(application) {
    private val weatherRepository = WeatherRepository(getDatabase(application))
    val forecasts = weatherRepository.forecasts
    val forecast = weatherRepository.forecast

    init {
        refreshDataFromRepository()
    }

    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                weatherRepository.fetchForecasts()
            } catch (e: Exception) {
                Log.e("tmhwrd", e.message ?: "")
            }
        }
    }

    fun forecastTapped(forecast: Forecast) = weatherRepository.updateForecast(forecast)
}