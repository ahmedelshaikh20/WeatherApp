package com.example.weatherapp.api

import com.example.weatherapp.API_KEY
import com.example.weatherapp.model.WeatherApiResponse
import retrofit2.Response

class WeatherRepositry() {

  suspend fun getWeatherByLocation(long : Double, lat :Double): Response<WeatherApiResponse> {

     return RetrofitInstance.api.getCurrentWeather(long, lat, API_KEY, "metric")

   }
}
