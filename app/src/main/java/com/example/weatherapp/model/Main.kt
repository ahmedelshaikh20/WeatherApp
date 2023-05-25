package com.example.weatherapp.model

import com.google.gson.annotations.SerializedName

data class Main(
  @SerializedName("feels_like")
    val feelsLike: Double,
  val grnd_level: Int,
  val humidity: Int,
  val pressure: Int,
  @SerializedName("sea_level")
  val seaLevel: Int,
  @SerializedName("temp")
  val temperature: Double,
  @SerializedName("temp_max")
  val maxTemperature: Double,
  @SerializedName("temp_min")
  val minTemperature: Double
)
