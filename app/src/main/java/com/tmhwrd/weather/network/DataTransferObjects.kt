package com.tmhwrd.weather.network

import java.text.SimpleDateFormat

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
) {
    val displayDate: String
        get() {
            Date?.let {
                val pattern = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                val formatter = SimpleDateFormat("MM-dd-yyyy")
                return formatter.format(pattern.parse(it))
            }
            return "this "
        }
}

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
    var Value: Float? = null, var Unit: String? = null, var UnitType: Int? = null
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

val List<Forecast>.domainObjects get() = map { it.toUiObject() }


val Temperature?.hiLo
    get() = this?.let { "${it.Maximum?.displayString}↑,  ${it.Minimum?.displayString}↓" } ?: ""
val Long.toHumanReadableDT: String get() = SimpleDateFormat("hh:mm:ssZ MM/dd/yy").format(this)
val SystemSpecificTemperature.displayString: String get() = "$Value°F"
val TimePeriod.precipitationText: String get() = if (HasPrecipitation == true) "${PrecipitationIntensity ?: ""} ${PrecipitationType ?: ""}" else "No Precipitation"
val TimePeriod?.iconIdentifier: String get() = this?.Icon?.paddedTwoDigits ?: "01"
val Int?.paddedTwoDigits: String get() = toString().padStart(2, '0')
fun Forecast.toUiObject(): UiForecast {
    val temp = "${currentConditions.Temperature?.Imperial?.displayString}"
    val forecast = fiveDayForecast.DailyForecasts.firstOrNull()
    val precipitation =
        if (currentConditions.HasPrecipitation == true) "${currentConditions.PrecipitationIntensity ?: ""} ${currentConditions.PrecipitationType ?: ""}" else "No Precipitation"
    val currentDay = Period(
        currentConditions.WeatherIcon.paddedTwoDigits,
        currentConditions.WeatherText ?: "",
        precipitation
    )
    val fiveDay = mutableListOf<Daily>()
    fiveDayForecast.DailyForecasts.forEach {
        val day = it.Day.toUiPeriod
        val night = it.Night.toUiPeriod
        val daily = Daily(it.Temperature.hiLo, it.displayDate, day, night)
        fiveDay.add(daily)
    }
    return UiForecast(
        timeStamp.toHumanReadableDT,
        forecast?.Temperature.hiLo,
        city,
        temp,
        currentDay,
        fiveDayForecast.Headline?.Text ?: "Here's your extended forecast...",
        fiveDay
    )
}

val TimePeriod?.toUiPeriod
    get() = this?.let {
        Period(
            iconIdentifier,
            it.IconPhrase ?: "",
            precipitationText
        )
    } ?: Period()

data class UiForecast(
    val timeStamp: String = "",
    val hiLo: String = "",
    val location: String = "",
    val temp: String = "",
    val current: Period = Period(),
    val fiveDayHeadline: String = "",
    val fiveDay: List<Daily> = emptyList(),
)

data class Daily(val hiLo: String, val displayDate: String, val day: Period, val night: Period)
data class Period(
    val iconId: String = "", val text: String = "", val precipitationText: String = ""
)
