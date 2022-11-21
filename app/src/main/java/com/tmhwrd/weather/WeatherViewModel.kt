package com.tmhwrd.weather

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.tmhwrd.weather.db.getDatabase
import com.tmhwrd.weather.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel(application: Application) : AndroidViewModel(application) {
    private val weatherRepository = WeatherRepository(getDatabase(application))

    init {
        refreshDataFromRepository()
    }

    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                weatherRepository.fetchCurrentConditions()
            } catch (e: Exception) {
                Log.d("tmhwrd", e?.message ?: "insert classic placeholder here")
            }
        }
    }

}