package com.tmhwrd.weather

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tmhwrd.weather.domain.CityForecast
import com.tmhwrd.weather.ui.theme.WeatherTheme
import com.tmhwrd.weather.viewmodels.WeatherViewModel

@Composable
fun CapitalCitiesScreen(
    modifier: Modifier,
    viewModel: WeatherViewModel = viewModel(),
    onNextButtonClicked: () -> Unit = {}
) {
    LazyColumn {
        items(viewModel.uiState.cityForecasts) { forecast ->
            CapitalCityView(modifier, forecast, onNextButtonClicked)
        }
    }
}

@Composable
fun CapitalCityView(
    modifier: Modifier, forecast: CityForecast, onNextButtonClicked: () -> Unit = {}
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
        CityForecast(
            "Boston", "MA", "58°F", "58°↑,  58°↓", "0\"", "Updated 12:33 PM"
        )
    }
}
