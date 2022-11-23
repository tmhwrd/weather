package com.tmhwrd.weather

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tmhwrd.weather.domain.CityForecast
import com.tmhwrd.weather.network.Period
import com.tmhwrd.weather.network.UiForecast
import com.tmhwrd.weather.ui.theme.WeatherTheme
import com.tmhwrd.weather.viewmodels.WeatherViewModel

@Composable
fun CapitalCitiesScreen(
    modifier: Modifier,
    viewModel: WeatherViewModel = viewModel(),
    onNextButtonClicked: () -> Unit = {}
) {
    val forecasts by viewModel.forecasts.observeAsState()
    LazyColumn {
        items(forecasts ?: emptyList()) { forecast ->
            CapitalCityView(modifier, forecast, onNextButtonClicked)
        }
    }
}

@Composable
fun CapitalCityView(
    modifier: Modifier, forecast: UiForecast, onNextButtonClicked: () -> Unit = {}
) {
    Row(modifier = Modifier.padding(24.dp)) {
        DailyForecastCell(
            modifier = Modifier.weight(1f), forecast
        )
        Spacer(Modifier.weight(.2f))
        Button(
            onClick = onNextButtonClicked, modifier
        ) {
            Text(stringResource(R.string.five_day))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeatherTheme {
        CapitalCityView(
            modifier = Modifier, forecast = UiForecast(
                "3/16",
                "30/20",
                "Boston, MA",
                "0",
                Period("01", "Some text", "Some other text"),
                "Here's your five day...",
                emptyList()
            )
        )
    }
}
