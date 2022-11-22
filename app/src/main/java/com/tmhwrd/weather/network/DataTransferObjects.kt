package com.tmhwrd.weather.network

data class FiveDayForecast(
    var Headline: Headline? = Headline(),
    var DailyForecasts: ArrayList<DailyForecasts> = arrayListOf()
)

data class DailyForecasts(
    var Date: String? = null,
    var EpochDate: Int? = null,
    var Temperature: Temperature? = Temperature(),
    var Day: TimePeriod? = TimePeriod(),
    var Night: TimePeriod? = TimePeriod(),
    var Sources: List<String> = arrayListOf(),
    var MobileLink: String? = null,
    var Link: String? = null
)

data class Headline(
    var EffectiveDate: String? = null,
    var EffectiveEpochDate: Int? = null,
    var Severity: Int? = null,
    var Text: String? = null,
    var Category: String? = null,
    var EndDate: String? = null,
    var EndEpochDate: Int? = null,
)

data class TimePeriod(
    var Icon: Int? = null,
    var IconPhrase: String? = null,
    var HasPrecipitation: Boolean? = null,
    var PrecipitationType: String? = null,
    var PrecipitationIntensity: String? = null
)

data class CurrentConditions(
    var LocalObservationDateTime: String? = null,
    var EpochTime: Int? = null,
    var WeatherText: String? = null,
    var WeatherIcon: Int? = null,
    var HasPrecipitation: Boolean? = null,
    var PrecipitationType: String? = null,
    var PrecipitationIntensity: String? = null,
    var IsDayTime: Boolean? = null,
    var Temperature: Temperature? = Temperature(),
)

data class SystemSpecificTemperature(
    var Value: Float? = null,
    var Unit: String? = null,
    var UnitType: Int? = null
)

data class Temperature(
    var Metric: SystemSpecificTemperature? = SystemSpecificTemperature(),
    var Imperial: SystemSpecificTemperature? = SystemSpecificTemperature(),
    var Minimum: SystemSpecificTemperature? = SystemSpecificTemperature(),
    var Maximum: SystemSpecificTemperature? = SystemSpecificTemperature()
)

data class Forecast(
    val timeStamp: Long,
    val city: String,
    val currentConditions: CurrentConditions,
    val fiveDayForecast: FiveDayForecast
)

fun List<Forecast>.toDomainObjects(): List<UiForecast> {
    return map {
        it.toUiObject()
    }
}

val SystemSpecificTemperature.displayString: String get() = "$Value°F"
val TimePeriod.precipitationText: String get() = if (HasPrecipitation == true) "$PrecipitationIntensity $PrecipitationType" else "Precipitation: None"
fun Forecast.toUiObject(): UiForecast {
    val temp = "${currentConditions.Temperature?.Imperial?.displayString}"
    val forecast = fiveDayForecast.DailyForecasts.firstOrNull()
    val precipitation =
        if (currentConditions.HasPrecipitation == true) "${currentConditions.PrecipitationIntensity} ${currentConditions.PrecipitationType}" else "Precipitation: None"
    val hiLo = forecast?.Temperature?.let {
        "${it.Maximum?.displayString}↑,  ${it.Minimum?.displayString}↓"
    } ?: ""
    val currentDay = Period(currentConditions.WeatherIcon ?: 1, currentConditions.WeatherText ?: "", precipitation)
    val fiveDay = mutableListOf<Daily>()
    fiveDayForecast.DailyForecasts.forEach {
        val day = Period( it.Day?.Icon ?: 0, it.Day?.IconPhrase ?: "", it.Day?.precipitationText ?: "")
        val night = Period(it.Night?.Icon ?: 0, it.Night?.IconPhrase ?: "", it.Night?.precipitationText ?: "")
        val daily = Daily(it.Date ?: "", day, night)
        fiveDay.add(daily)
    }
    return UiForecast(timeStamp, hiLo, city, temp, currentDay, fiveDay)
}

data class UiForecast(
    val timeStamp: Long = System.currentTimeMillis(),
    val hiLo: String,
    val location: String,
    val temp: String,
    val current: Period,
    val fiveDay: List<Daily>,
)

data class Daily(val displayDate:String, val day: Period, val night: Period)
data class Period(val iconId: Int, val text: String, val precipitationText: String)
