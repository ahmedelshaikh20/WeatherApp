package com.example.weatherapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherDataItem(
  var name: String?,
  var country : String? ,
  var description: String?,
  var temperature: Double?,
  var humidity: Int? ,
  var pressure: Int?,
  var icon: String?=null,
  var time : Long?

):Parcelable
