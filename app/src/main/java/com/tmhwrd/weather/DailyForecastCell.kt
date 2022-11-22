package com.tmhwrd.weather

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tmhwrd.weather.domain.CityForecast
import com.tmhwrd.weather.network.UiForecast


@Composable
fun DailyForecastCell(
    modifier: Modifier,
    forecast: CityForecast,
) {
    Row(modifier) {
        Column {
            Text("${forecast.city}, ${forecast.state} ${forecast.temp}")
            Text(forecast.hiLo)
            Text("${forecast.precipitation} Precipitation")
            Text(forecast.timeUpdated)
        }
    }
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data("https://developer.accuweather.com/sites/default/files/${forecast.iconId}-s.png")
            .crossfade(true)
            .build(),
        contentDescription = null,
        modifier = Modifier.requiredSize(72.dp),
        contentScale = ContentScale.Fit
    )
}

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
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data("https://developer.accuweather.com/sites/default/files/${forecast.current.iconId}-s.png")
            .crossfade(true)
            .build(),
        contentDescription = null,
        modifier = Modifier.requiredSize(72.dp),
        contentScale = ContentScale.Fit
    )
}
