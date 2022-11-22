import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.tmhwrd.weather.DailyForecastCell
import com.tmhwrd.weather.R
import com.tmhwrd.weather.domain.CityForecast
import com.tmhwrd.weather.ui.theme.WeatherTheme

@Composable
fun ExtendedForecastScreen(
    modifier: Modifier, forecasts: List<CityForecast>, onNextButtonClicked: () -> Unit = {}
) {
    LazyColumn {
        items(forecasts) { forecast ->
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
        Spacer(Modifier.weight(.5f))
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeatherTheme {
        ExtendedForecastScreen(
            Modifier, listOf(
                CityForecast(
                    "Boston", "MA", "58°F", "58°↑,  58°↓", "0\"", "Updated 12:33 PM"
                )
            )
        )
    }
}
