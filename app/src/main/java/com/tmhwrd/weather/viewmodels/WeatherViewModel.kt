package com.tmhwrd.weather.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.tmhwrd.weather.db.getDatabase
import com.tmhwrd.weather.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel(application: Application) : AndroidViewModel(application) {
    private val weatherRepository = WeatherRepository(getDatabase(application))
    val forecasts = weatherRepository.forecasts

    init {
        refreshDataFromRepository()
    }

    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                val forecasts = weatherRepository.fetchForecasts()
                Log.d("abcde", forecasts.firstOrNull().toString())
            } catch (e: Exception) {
                Log.d("tmhwrd", e.message ?: "insert classic placeholder here")
            }
        }
    }

}