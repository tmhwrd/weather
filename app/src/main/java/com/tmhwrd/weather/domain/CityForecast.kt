package com.tmhwrd.weather.domain

data class CityForecast(
    val city: String,
    val state: String,
    val temp: String,
    val hiLo: String,
    val precipitation: String,
    val timeUpdated: String,
    val fiveDay: String = "",
    val thumbnail: String = "",
    val iconId: String = "01"
)
