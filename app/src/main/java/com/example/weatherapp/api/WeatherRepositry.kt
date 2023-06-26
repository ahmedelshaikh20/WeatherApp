package com.example.weatherapp.api

import com.example.weatherapp.BuildConfig
import com.example.weatherapp.model.SuggestionDataItem
import com.example.weatherapp.model.WeatherDataItem

class WeatherRepositry() {

  suspend fun getWeatherByLocation(long: Double, lat: Double): WeatherDataItem? {

    val response =
      RetrofitInstance.api.getCurrentWeather(long, lat, BuildConfig.API_KEY, "metric").body()
    val result = response?.let {
      WeatherDataItem(
        it.name,
        it.countryInfo.country,
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

  suspend fun getWeatherByLocation(location: String): List<SuggestionDataItem> {

    val response = RetrofitInstances.geocodingApi.getAllLocationPrediction(
      location,
      apiKey = BuildConfig.GEOCDOING_API_KEY
    )
    val resultList = ArrayList<SuggestionDataItem>()
    response.body()?.results?.let {
      resultList.addAll(it.map {
        SuggestionDataItem(
          it.city,
          it.country,
          it.country_code,
          it.state,
          it.formatted,
          it.lat,
          it.lon
        )
      })
    }

    return resultList.toList()

  }

}
