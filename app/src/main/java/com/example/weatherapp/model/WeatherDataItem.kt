package com.example.weatherapp.model

data class WeatherDataItem(
  var name: String,
  var country : String,
  var description: String,
  var temperature: Double,
  var humidity: Int,
  var pressure: Int,
  var icon: String,
  var time : Long

)
