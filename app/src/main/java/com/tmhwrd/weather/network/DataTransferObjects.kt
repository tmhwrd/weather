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
    var HasPrecipitation: Boolean? = null
)

data class CurrentConditions(
    var LocalObservationDateTime: String? = null,
    var EpochTime: Int? = null,
    var WeatherText: String? = null,
    var WeatherIcon: Int? = null,
    var HasPrecipitation: Boolean? = null,
    var PrecipitationType: String? = null,
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
    var Imperial: SystemSpecificTemperature? = SystemSpecificTemperature()
)
