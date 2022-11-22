package com.tmhwrd.weather.viewmodels

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.tmhwrd.weather.db.getDatabase
import com.tmhwrd.weather.domain.CityForecast
import com.tmhwrd.weather.repository.WeatherRepository
import kotlinx.coroutines.launch

data class UiState(
    val cityForecasts: List<CityForecast> = listOf(
        CityForecast(
            "Boston", "MA", "58°F", "58°↑,  58°↓", "0\"", "Updated 12:33 PM"
        ),
        CityForecast(
            "Boston", "MA", "58°F", "58°↑,  58°↓", "0\"", "Updated 12:33 PM"
        ),
        CityForecast(
            "Boston", "MA", "58°F", "58°↑,  58°↓", "0\"", "Updated 12:33 PM"
        ),
        CityForecast(
            "Boston", "MA", "58°F", "58°↑,  58°↓", "0\"", "Updated 12:33 PM"
        ),
        CityForecast(
            "Boston", "MA", "58°F", "58°↑,  58°↓", "0\"", "Updated 12:33 PM"
        ),
        CityForecast(
            "Boston", "MA", "58°F", "58°↑,  58°↓", "0\"", "Updated 12:33 PM"
        ),
        CityForecast(
            "Boston", "MA", "58°F", "58°↑,  58°↓", "0\"", "Updated 12:33 PM"
        ),
        CityForecast(
            "Boston", "MA", "58°F", "58°↑,  58°↓", "0\"", "Updated 12:33 PM"
        ),
        CityForecast(
            "Boston", "MA", "58°F", "58°↑,  58°↓", "0\"", "Updated 12:33 PM"
        ),
        CityForecast(
            "Boston", "MA", "58°F", "58°↑,  58°↓", "0\"", "Updated 12:33 PM"
        ),
    )
)

class WeatherViewModel(application: Application) : AndroidViewModel(application) {
    private val weatherRepository = WeatherRepository(getDatabase(application))

    var uiState by mutableStateOf(UiState())
        private set

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