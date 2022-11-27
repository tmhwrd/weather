package com.tmhwrd.weather.network

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat

data class FiveDayForecast(
    @SerializedName("Headline") var headline: Headline? = Headline(),
    @SerializedName("DailyForecasts") var forecasts: ArrayList<DailyForecast> = arrayListOf()
)

data class DailyForecast(
    @SerializedName("Date") var date: String? = null,
    @SerializedName("Temperature") var temperature: TemperatureData? = TemperatureData(),
    @SerializedName("Day") var day: TimePeriod? = TimePeriod(),
    @SerializedName("Night") var night: TimePeriod? = TimePeriod(),
) {
    val displayDate: String
        get() {
            date?.let {
                val pattern = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                val formatter = SimpleDateFormat("MM-dd-yyyy")
                return formatter.format(pattern.parse(it))
            }
            return "this "
        }
}

data class Headline(
    @SerializedName("Text") var Text: String? = null,
)

abstract class PrecipitationData {
    abstract var hasPrecipitation: Boolean?
    abstract var precipitationType: String?
    abstract var precipitationIntensity: String?
    val precipitationText: String
        get() = if (this.hasPrecipitation == true) arrayOf(
            this.precipitationIntensity,
            this.precipitationType
        ).filter { !it.isNullOrEmpty() }.joinToString ( " " ) else "No Precipitation"
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

data class Forecast(
    val timeStamp: Long,
    val city: String,
    val currentConditions: CurrentConditions,
    val fiveDayForecast: FiveDayForecast
)

val List<Forecast>.domainObjects get() = map { it.toUiObject() }


val TemperatureData?.hiLo
    get() = this?.let { "${it.Maximum?.displayString}↑,  ${it.Minimum?.displayString}↓" } ?: ""
val Long.toHumanReadableDT: String get() = SimpleDateFormat("hh:mm:ssZ MM/dd/yy").format(this)
val Temperature.displayString: String get() = "$value°F"
val TimePeriod?.iconIdentifier: String get() = this?.iconId?.paddedTwoDigits ?: "01"
val Int?.paddedTwoDigits: String get() = toString().padStart(2, '0')
fun Forecast.toUiObject(): UiForecast {
    val temp = "${currentConditions.temperatureData?.Imperial?.displayString}"
    val forecast = fiveDayForecast.forecasts.firstOrNull()
    val currentDay = Period(
        currentConditions.iconId.paddedTwoDigits,
        currentConditions.text ?: "",
        currentConditions.precipitationText
    )
    val fiveDay = mutableListOf<Daily>()
    fiveDayForecast.forecasts.forEach {
        val day = it.day.toUiPeriod
        val night = it.night.toUiPeriod
        val daily = Daily(it.temperature.hiLo, it.displayDate, day, night)
        fiveDay.add(daily)
    }
    return UiForecast(
        timeStamp.toHumanReadableDT,
        forecast?.temperature.hiLo,
        city,
        temp,
        currentDay,
        fiveDayForecast.headline?.Text ?: "Here's your extended forecast...",
        fiveDay
    )
}

val TimePeriod?.toUiPeriod
    get() = this?.let {
        Period(
            iconIdentifier,
            it.iconText ?: "",
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
