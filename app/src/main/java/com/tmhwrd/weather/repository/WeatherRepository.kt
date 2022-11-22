package com.tmhwrd.weather.repository

import android.util.Log
import com.tmhwrd.weather.BuildConfig
import com.tmhwrd.weather.db.ForecastDatabase
import com.tmhwrd.weather.network.WeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class WeatherRepository(private val database: ForecastDatabase) {
    private val allStateCapitals: List<Pair<String, Int>> = listOf(
        "Albany, New York" to 329673,
        "Annapolis, Maryland" to 329302,
        "Atlanta, Georgia" to 2625833,
        "Augusta, Maine" to 333496,
        "Austin, Texas" to 351193,
        "Baton Rouge, Louisiana" to 329147,
        "Bismarck, North Dakota" to 329830,
        "Boise, Idaho" to 328736,
        "Boston, Massachusetts" to 2089587,
//        "Carson City, Nevada" to 334448,
//        "Charleston, West Virginia" to 331471,
//        "Cheyenne, Wyoming" to 331604,
//        "Columbia, South Carolina" to 330679,
//        "Columbus, Ohio" to 350128,
//        "Concord, New Hampshire" to 329508,
//        "Denver, Colorado" to 2626674,
//        "Des Moines, Iowa" to 328810,
//        "Dover, Delaware" to 332276,
//        "Frankfort, Kentucky" to 328975,
//        "Harrisburg, Pennsylvania" to 330288,
//        "Hartford, Connecticut" to 327356,
//        "Helena, Montana" to 334230,
//        "Honolulu, Hawaii" to 348211,
//        "Indianapolis, Indiana" to 2626739,
//        "Jackson, Mississippi" to 329432,
//        "Jefferson City, Missouri" to 329435,
//        "Juneau, Alaska" to 331728,
//        "Lansing, Michigan" to 329381,
//        "Lincoln, Nebraska" to 329505,
//        "Little Rock, Arkansas" to 326862,
//        "Madison, Wisconsin" to 331530,
//        "Montgomery, Alabama" to 326706,
//        "Montpelier, Vermont" to 336167,
//        "Nashville, Tennessee" to 3592984,
//        "Oklahoma City, Oklahoma" to 350143,
//        "Olympia, Washington" to 331418,
//        "Phoenix, Arizona" to 346935,
//        "Pierre, South Dakota" to 335569,
//        "Providence, Rhode Island" to 330414,
//        "Raleigh,North Carolina" to 329823,
//        "Richmond, Virginia" to 331252,
//        "Sacramento, California" to 347627,
//        "Salem, Oregon" to 330144,
//        "Salt Lake City, Utah" to 331216,
//        "Santa Fe, New Mexico" to 329558,
//        "Springfield, Illinois" to 328763,
//        "St. Paul, Minnesota" to 3591017,
//        "Tallahassee, Florida" to 328170,
//        "Topeka, Kansas" to 328851,
//        "Trenton, New Jersey" to 329551,
    )
    
    suspend fun fetchForecasts() {
        allStateCapitals.map { capital ->
            withContext(Dispatchers.IO) {
                val conditionsCall = async {
                    WeatherNetwork.service.getCurrentConditions(
                        capital.second, BuildConfig.API_TOKEN
                    )
                }
                val fiveDayCall = async {
                    WeatherNetwork.service.getFiveDayForecast(
                        capital.second, BuildConfig.API_TOKEN
                    )
                }
                val currentConditions = conditionsCall.await()
                val fiveDayForecast = fiveDayCall.await()
                Log.d("abcde", "${capital.second}")
            }
        }
    }
}
