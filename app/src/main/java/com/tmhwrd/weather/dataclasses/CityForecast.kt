package com.tmhwrd.weather.dataclasses

data class CityForecast(
    val city: String,
    val state: String,
    val temp: String,
    val hiLo: String,
    val precipitation: String,
    val timeUpdated: String,
    val fiveDay: String = ""
)
