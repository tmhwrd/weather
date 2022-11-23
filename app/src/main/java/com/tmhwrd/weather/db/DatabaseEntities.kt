package com.tmhwrd.weather.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tmhwrd.weather.domain.CityForecast

@Entity
data class DatabaseForecast constructor(
    @PrimaryKey
    val city: String,
    val state: String,
    val temp: String,
    val hiLo: String,
    val precipitation: String,
    val timeUpdated: String,
    val fiveDay: String,
    val thumbnail: String
)