package com.tmhwrd.weather

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tmhwrd.weather.domain.CityForecast

@Composable
fun DailyForecastCell(
    modifier: Modifier,
    forecast: CityForecast,
) {
    Column(modifier) {
        Text("${forecast.city}, ${forecast.state} ${forecast.temp}")
        Text(forecast.hiLo)
        Text("${forecast.precipitation} Precipitation")
        Text(forecast.timeUpdated)
    }
}
