package com.tmhwrd.weather.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Forecast(
    @PrimaryKey
    val location: String = "",
    val timeStamp: String = "",
    val hiLo: String = "",
    val temp: String = "",
    val current: Period = Period(),
    val fiveDayHeadline: String = "",
    val fiveDay: List<Daily> = emptyList(),
)

data class Daily(val hiLo: String, val displayDate: String, val day: Period, val night: Period)

data class Period(
    val iconId: String = "", val text: String = "", val precipitationText: String = ""
)
