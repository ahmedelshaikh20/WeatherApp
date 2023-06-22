package com.example.weatherapp.model

import com.google.gson.annotations.SerializedName

data class WeatherApiResponse(
  val base: String,
  val clouds: Clouds,
  val cod: Int,
  val coord: Coord,
  val dt: Long,
  val id: Int,
  val main: Main,
  val name: String,
  val rain: Rain = Rain(),
  @SerializedName("sys")
  val countryInfo: CountryInfo,
  @SerializedName("timezone")
  val timeZone: Int,
  val visibility: Int,
  val weather: List<Weather>,
  val wind: Wind
)
