package com.tmhwrd.weather

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tmhwrd.weather.domain.CityForecast
import com.tmhwrd.weather.network.CurrentConditions
import com.tmhwrd.weather.network.WeatherNetwork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

enum class WeatherScreen(@StringRes val title: Int) {
    Capitals(title = R.string.select_your_city),
    FiveDay(title = R.string.five_day),
}

@Composable
fun AppBar(
    currentScreen: WeatherScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(id = currentScreen.title)) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun WeatherApp(modifier: Modifier = Modifier, viewModel: WeatherViewModel = viewModel()) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = WeatherScreen.valueOf(
        backStackEntry?.destination?.route ?: WeatherScreen.Capitals.name
    )

    Scaffold(
        topBar = {
            AppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = WeatherScreen.Capitals.name,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = WeatherScreen.Capitals.name) {
                CapitalCitiesScreen(modifier,
                    listOf(
                        CityForecast(
                            "Boston",
                            "MA",
                            "58°F",
                            "58°↑,  58°↓",
                            "0\"",
                            "Updated 12:33 PM"
                        ),
                        CityForecast(
                            "Boston",
                            "MA",
                            "58°F",
                            "58°↑,  58°↓",
                            "0\"",
                            "Updated 12:33 PM"
                        ),
                        CityForecast(
                            "Boston",
                            "MA",
                            "58°F",
                            "58°↑,  58°↓",
                            "0\"",
                            "Updated 12:33 PM"
                        ),
                        CityForecast(
                            "Boston",
                            "MA",
                            "58°F",
                            "58°↑,  58°↓",
                            "0\"",
                            "Updated 12:33 PM"
                        ),
                        CityForecast(
                            "Boston",
                            "MA",
                            "58°F",
                            "58°↑,  58°↓",
                            "0\"",
                            "Updated 12:33 PM"
                        )
                    ),
                    onNextButtonClicked = {
                        navController.navigate(WeatherScreen.FiveDay.name)
                    })
            }
            composable(route = WeatherScreen.FiveDay.name) {
                CapitalCitiesScreen(
                    modifier,
                    listOf(
                        CityForecast(

                            "Albany",
                            "NY",
                            "58°F",
                            "58°↑,  58°↓",
                            "2\"",
                            "Updated 12:43 PM"
                        )
                    )
                )
            }
        }
    }
}
