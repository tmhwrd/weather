package com.tmhwrd.weather

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tmhwrd.weather.network.UiForecast

@Composable
fun DailyForecastCell(
    modifier: Modifier,
    forecast: UiForecast,
) {
    Row(modifier) {
        Column {
            Text("${forecast.location}, ${forecast.temp}")
            Text(forecast.hiLo)
            Text("${forecast.current.precipitationText} ")
            Text("${forecast.timeStamp}")
        }
    }
    IconImage(forecast.current.iconId)
}
