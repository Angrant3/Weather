package com.example.weather

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("weather")
    fun getWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric" // Đơn vị nhiệt độ là Celsius
    ): Call<WeatherResponse>
}
