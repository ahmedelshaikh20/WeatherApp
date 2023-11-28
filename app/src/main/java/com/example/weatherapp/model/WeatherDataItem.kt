package com.example.weatherapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherDataItem(
  var name: String = "NewYork",
  var country : String? = "US",
  var description: String = "Sunny",
  var temperature: Double = 13.0,
  var humidity: Int =10,
  var pressure: Int =10,
  var icon: String?=null,
  var time : Long=10

):Parcelable
