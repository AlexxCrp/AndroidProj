package com.example.training

data class WeatherResponse(
    val main: Main,
    val weather: List<Weather>,
)

data class Main(
    val temp: String,
    val humidity: String

)

data class Weather(
    val main: String
)
