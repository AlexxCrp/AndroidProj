package com.example.training.Interfaces

import com.example.training.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("weather")
    fun getCurrentWeather(
        @Query("lat") latitude: Double?,
        @Query("lon") longitude: Double?,
        @Query("units") units: String,
        @Query("appid") apiKey: String
    ): Call<WeatherResponse>
}