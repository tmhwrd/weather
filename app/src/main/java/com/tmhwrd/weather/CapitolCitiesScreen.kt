package com.tmhwrd.weather

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tmhwrd.weather.dataclasses.CityForecast
import com.tmhwrd.weather.ui.theme.WeatherTheme

@Composable
fun CapitolCitiesScreen(
    modifier: Modifier,
    cityData: List<CityForecast>,
    onNextButtonClicked: () -> Unit = {}
) {
    LazyColumn {
        items(cityData) { forecast ->
            CityForecastView(modifier, forecast, onNextButtonClicked)
        }
    }
}

@Composable
fun CityForecastView(
    modifier: Modifier,
    cityData: CityForecast,
    onNextButtonClicked: () -> Unit = {}
) {
    Row(modifier = Modifier.padding(24.dp)) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text("${cityData.city}, ${cityData.state} ${cityData.temp}")
            Text(cityData.hiLo)
            Text("${cityData.precipitation} Precipitation")
            Text(cityData.timeUpdated)
        }
        Button(
            onClick = onNextButtonClicked,
            modifier = modifier.widthIn(min = 125.dp)
        ) {
            Text(stringResource(R.string.five_day))

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeatherTheme {
        CapitolCitiesScreen(
            Modifier,
            listOf(
                CityForecast(
                    "Boston",
                    "MA",
                    "58°F",
                    "58°↑,  58°↓",
                    "0\"",
                    "Updated 12:33 PM"
                )
            )
        )
    }
}
