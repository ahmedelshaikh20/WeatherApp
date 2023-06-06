package com.example.weatherapp.api

import com.example.weatherapp.API_KEY
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.model.WeatherApiResponse
import com.example.weatherapp.model.geocodingmodel.GeocodingApiResponse
import retrofit2.Response

class WeatherRepositry() {

  suspend fun getWeatherByLocation(long : Double, lat :Double): Response<WeatherApiResponse> {

     return RetrofitInstance.api.getCurrentWeather(long, lat, BuildConfig.API_KEY, "metric")

   }
  suspend fun getWeatherByLocation(location : String): Response<GeocodingApiResponse> {

    return RetrofitInstances.geocodingApi.getAllLocationPrediction( location , "json",BuildConfig.GEOCDOING_API_KEY)

  }

}
