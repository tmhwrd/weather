package com.tmhwrd.weather.network

import com.google.gson.annotations.SerializedName
import com.tmhwrd.weather.db.Daily
import com.tmhwrd.weather.db.Forecast
import com.tmhwrd.weather.db.Period
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

data class FiveDay(
    @SerializedName("Headline") var headline: Headline? = Headline(),
    @SerializedName("DailyForecasts") var forecasts: ArrayList<DailyForecast> = arrayListOf()
)

data class DailyForecast(
    @SerializedName("Date") var date: Date? = null,
    @SerializedName("Temperature") var temperature: TemperatureData? = TemperatureData(),
    @SerializedName("Day") var day: TimePeriod? = TimePeriod(),
    @SerializedName("Night") var night: TimePeriod? = TimePeriod(),
)

data class Headline(
    @SerializedName("Text") var text: String? = null,
)

abstract class PrecipitationData {
    abstract var hasPrecipitation: Boolean?
    abstract var precipitationType: String?
    abstract var precipitationIntensity: String?
    val precipitationText: String
        get() = if (this.hasPrecipitation == true) arrayOf(
            this.precipitationIntensity, this.precipitationType
        ).filter { !it.isNullOrEmpty() }.joinToString(" ") else "No Precipitation"
}

data class TimePeriod(
    @SerializedName("Icon") var iconId: Int? = null,
    @SerializedName("IconPhrase") var iconText: String? = null,
    @SerializedName("HasPrecipitation") override var hasPrecipitation: Boolean? = null,
    @SerializedName("PrecipitationType") override var precipitationType: String? = null,
    @SerializedName("PrecipitationIntensity") override var precipitationIntensity: String? = null
) : PrecipitationData()

data class CurrentConditions(
    @SerializedName("WeatherText") var text: String? = null,
    @SerializedName("WeatherIcon") var iconId: Int? = null,
    @SerializedName("HasPrecipitation") override var hasPrecipitation: Boolean? = null,
    @SerializedName("PrecipitationType") override var precipitationType: String? = null,
    @SerializedName("PrecipitationIntensity") override var precipitationIntensity: String? = null,
    @SerializedName("IsDayTime") var isDayTime: Boolean? = null,
    @SerializedName("Temperature") var temperatureData: TemperatureData? = TemperatureData(),
) : PrecipitationData()

data class Temperature(
    @SerializedName("Value") var value: Float? = null,
    @SerializedName("Unit") var Unit: String? = null,
    @SerializedName("UnitType") var UnitType: Int? = null
)

data class TemperatureData(
    @SerializedName("Metric") var Metric: Temperature? = Temperature(),
    @SerializedName("Imperial") var Imperial: Temperature? = Temperature(),
    @SerializedName("Minimum") var Minimum: Temperature? = Temperature(),
    @SerializedName("Maximum") var Maximum: Temperature? = Temperature()
)

data class WeatherDTO(
    val city: String,
    val timeStamp: Date,
    val currentConditions: CurrentConditions,
    val fiveDay: FiveDay
)

val List<WeatherDTO>.domainObjects get() = map { it.toAppObject() }

val TemperatureData?.hiLo
    get() = this?.let { "${it.Maximum.displayString}↑,  ${it.Minimum.displayString}↓" } ?: ""
val Temperature?.displayString: String get() = "${this?.value}°F"
val TimePeriod?.iconIdentifier: String get() = this?.iconId?.paddedTwoDigits ?: "01"
val Int?.paddedTwoDigits: String get() = toString().padStart(2, '0')
val Date?.displayString: String
    get() = this?.let {
        SimpleDateFormat(
            "h:mm MM-dd-yyyy", Locale.getDefault()
        ).format(it)
    } ?: ""

fun WeatherDTO.toAppObject(): Forecast {
    val forecast = fiveDay.forecasts.firstOrNull()
    val currentDay = Period(
        currentConditions.iconId.paddedTwoDigits,
        currentConditions.text ?: "",
        currentConditions.precipitationText
    )
    val fiveDay = mutableListOf<Daily>()
    this.fiveDay.forecasts.forEach {
        val day = it.day.toUiPeriod
        val night = it.night.toUiPeriod
        val daily = Daily(it.temperature.hiLo, it.date.displayString, day, night)
        fiveDay.add(daily)
    }
    return Forecast(
        city,
        timeStamp.displayString,
        forecast?.temperature.hiLo,
        currentConditions.temperatureData?.Imperial.displayString,
        currentDay,
        this.fiveDay.headline?.text ?: "Here's your extended forecast...",
        fiveDay
    )
}

val TimePeriod?.toUiPeriod
    get() = this?.let {
        Period(
            iconIdentifier, it.iconText ?: "", precipitationText
        )
    } ?: Period()