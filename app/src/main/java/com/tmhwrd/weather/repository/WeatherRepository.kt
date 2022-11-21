package com.tmhwrd.weather.repository

import android.util.Log
import com.tmhwrd.weather.db.ForecastDatabase
import com.tmhwrd.weather.network.WeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository(private val database: ForecastDatabase) {

    suspend fun fetchCurrentConditions() {
        withContext(Dispatchers.IO) {
            val currentConditions =
                WeatherNetwork.service.getCurrentConditions("2GGilTZY8szTiFX1jvr4OUhDSxRsdl0M")
            Log.d("tmhwrd", currentConditions.toString())
        }
    }
}