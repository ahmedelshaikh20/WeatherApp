package com.example.weatherapp.api

import com.example.weatherapp.BuildConfig
import com.example.weatherapp.model.SugesstionDataItem
import com.example.weatherapp.model.WeatherApiResponse
import com.example.weatherapp.model.WeatherDataItem
import com.example.weatherapp.model.geocodingmodel.GeocodingApiResponse
import retrofit2.Response

class WeatherRepositry() {

  suspend fun getWeatherByLocation(long: Double, lat: Double): WeatherDataItem? {

    val response =
      RetrofitInstance.api.getCurrentWeather(long, lat, BuildConfig.API_KEY, "metric").body()
    val result = response?.let {
      WeatherDataItem(
        it.name,
        it.sys.country,
        it.weather[0].description,
        it.main.temperature,
        it.main.humidity,
        it.main.pressure,
        it.weather[0].icon,
        it.dt
      )
    }
    return result

  }

  suspend fun getWeatherByLocation(location: String): List<SugesstionDataItem>{

    val response = RetrofitInstances.geocodingApi.getAllLocationPrediction(
      location,
      "json",
      BuildConfig.GEOCDOING_API_KEY
    )
    val resultList = ArrayList<SugesstionDataItem>()
    response.body()?.results?.let {
      resultList.addAll(it.map {
        SugesstionDataItem(
          it.city,
          it.country,
          it.country_code,
          it.state,
          it.formatted,
          it.lat,
          it.lon
        )
    } )
    }

    return resultList.toList()

  }

}
