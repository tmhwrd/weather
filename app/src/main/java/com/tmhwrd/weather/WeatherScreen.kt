package com.tmhwrd.weather

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

enum class WeatherScreen() {
    Start,
    Capitols,
    FiveDay,
//    Search
}

@Composable
fun AppBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(id = R.string.app_name)) },
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

    Scaffold(
        topBar = {
            AppBar(
                canNavigateBack = false,
                navigateUp = { /* TODO: implement back navigation */ }
            )
        }
    ) { innerPadding ->
//        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = WeatherScreen.Capitols.name,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = WeatherScreen.Capitols.name) {
                CapitolCitiesScreen(modifier,
                    "Boston",
                    "MA",
                    "58°F",
                    "58°↑,  58°↓",
                    "0\"",
                    "Updated 12:33 PM",
                    onNextButtonClicked = {
                        navController.navigate(WeatherScreen.FiveDay.name)
                    })
            }
            composable(route = WeatherScreen.FiveDay.name) {
                CapitolCitiesScreen(
                    modifier,
                    "Albany",
                    "NY",
                    "58°F",
                    "58°↑,  58°↓",
                    "0\"",
                    "Updated 12:33 PM"
                )
            }
        }
    }
}
