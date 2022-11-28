package com.tmhwrd.weather.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class DataConverter {
    private val periodType: Type = object : TypeToken<Period>() {}.type
    private val dailyListType: Type = object : TypeToken<List<Daily>>() {}.type

    @TypeConverter
    fun fromPeriod(period: Period?): String? = period?.let { Gson().toJson(it, periodType) }

    @TypeConverter
    fun toPeriod(periodString: String?): Period? =
        periodString?.let { Gson().fromJson(it, periodType) }

    @TypeConverter
    fun fromDailyList(dailies: List<Daily>?): String? = dailies?.let { Gson().toJson(it, dailyListType) }

    @TypeConverter
    fun toDailies(dailiesString: String?): List<Daily>? =
        dailiesString?.let { Gson().fromJson(it, dailyListType) }
}