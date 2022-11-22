package com.tmhwrd.weather.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("currentconditions/v1/326706")
    suspend fun getCurrentConditions(@Query("apikey") apiKey: String): List<CurrentConditions>
}

object WeatherNetwork {
    private val retrofit = Retrofit.Builder().baseUrl("https://dataservice.accuweather.com/")
        .addConverterFactory(GsonConverterFactory.create()).build()

    val service: WeatherService = retrofit.create(WeatherService::class.java)
}
