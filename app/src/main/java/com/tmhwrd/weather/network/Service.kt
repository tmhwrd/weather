package com.tmhwrd.weather.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {
    @GET("currentconditions/v1/{id}")
    suspend fun getCurrentConditions(
        @Path("id") id: Int, @Query("apikey") apiKey: String
    ): List<CurrentConditions>

    @GET("forecasts/v1/daily/5day/{id}")
    suspend fun getFiveDayForecast(
        @Path("id") id: Int, @Query("apikey") apiKey: String
    ): FiveDay
}

object WeatherNetwork {
    private val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()
    private val retrofit = Retrofit.Builder().baseUrl("https://dataservice.accuweather.com/")
        .addConverterFactory(GsonConverterFactory.create(gson)).build()

    val service: WeatherService = retrofit.create(WeatherService::class.java)
}
