package com.example.weatherapp.api

import com.example.weatherapp.model.WeatherApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

  @GET("weather")
  suspend fun getCurrentWeather(@Query("lat") lat : Double , @Query("lon") lon : Double , @Query("appid") appid : String , @Query("units") units : String ) : Response<WeatherApiResponse>
  @GET("weather")
  suspend fun getAirPollution(@Query("lat") lat : Double , @Query("lon") lon : Double , @Query("appid") appid : String , @Query("units") units : String ) : Response<WeatherApiResponse>
}
