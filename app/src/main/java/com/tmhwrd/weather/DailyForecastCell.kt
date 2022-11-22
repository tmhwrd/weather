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
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.tmhwrd.weather.domain.CityForecast


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
