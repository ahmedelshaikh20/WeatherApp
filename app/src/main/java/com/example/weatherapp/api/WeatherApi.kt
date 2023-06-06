package com.example.weatherapp.api

import com.example.weatherapp.model.WeatherApiResponse
import com.example.weatherapp.model.geocodingmodel.GeocodingApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

  @GET("weather")
  suspend fun getCurrentWeather(@Query("lat") lat : Double , @Query("lon") lon : Double , @Query("appid") appid : String , @Query("units") units : String ) : Response<WeatherApiResponse>

}


interface GeocodingApi {

  @GET("autocomplete")
  suspend fun getAllLocationPrediction(@Query("text") country : String , @Query("format") format : String , @Query("apiKey") apiKey : String ) : Response<GeocodingApiResponse>


}
