package com.tmhwrd.weather

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tmhwrd.weather.ui.theme.WeatherTheme

@Composable
fun CapitolCitiesScreen(
    modifier:Modifier,
    city: String,
    state: String,
    temp: String,
    hiLo: String,
    precipitation: String,
    timeUpdated: String,
    onNextButtonClicked: () -> Unit = {}
    ) {
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(text = "$city, $state $temp")
                Text(text = hiLo)
                Text(text = "$precipitation Precipitation")
                Text(text = timeUpdated)
            }
            Button(
            onClick = onNextButtonClicked,
            modifier = modifier.widthIn(min = 125.dp)
        ) {
            Text(stringResource(R.string.five_day))
        }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeatherTheme {
        CapitolCitiesScreen(Modifier, "Boston", "MA", "58°F", "58°↑,  58°↓", "0\"", "Updated 12:33 PM")
    }
}
