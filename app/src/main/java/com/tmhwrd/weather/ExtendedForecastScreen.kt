package com.tmhwrd.weather

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tmhwrd.weather.db.Daily
import com.tmhwrd.weather.db.Period
import com.tmhwrd.weather.viewmodels.WeatherViewModel

@Composable
fun ExtendedForecastScreen(viewModel: WeatherViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val forecast by viewModel.forecast.observeAsState()
    LazyColumn {
        items(forecast?.fiveDay ?: emptyList()) { fiveDay ->
            ExtendedForecastCell(fiveDay)
        }
    }
}

@Composable
fun ExtendedForecastCell(daily: Daily) {
    Column(modifier = Modifier.padding(24.dp)) {
        Text(text = daily.displayDate)
        Text(text = daily.hiLo)
        DayPartCell(isDay = true, period = daily.day)
        DayPartCell(isDay = false, period = daily.night)
    }
}

@Composable
fun DayPartCell(isDay: Boolean, period: Period) {
    Row {
        Column {
            Text(text = stringResource(if (isDay) R.string.day else R.string.night))
            Text(text = period.text)
            Text(text = period.precipitationText)
        }
        Spacer(Modifier.weight(.2f))
        IconImage(period.iconId)
    }
}