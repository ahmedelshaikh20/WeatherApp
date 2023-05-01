package com.example.weatherapp.model

data class WeatherApiResponse(
  val base: String,
  val clouds: Clouds,
  val cod: Int,
  val coord: Coord,
  val dt: Int,
  val id: Int,
  val main: Main,
  val name: String,
  val rain: Rain = Rain(),
  val sys: Sys,
  val timezone: Int,
  val visibility: Int,
  val weather: List<Weather>,
  val wind: Wind
)
