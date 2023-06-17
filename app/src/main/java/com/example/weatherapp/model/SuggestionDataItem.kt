package com.example.weatherapp.model

data class SugesstionDataItem(
  var city :String? ,
  var country :String,
  var countryCode : String,
  var state : String,
  var formattedAddress : String,
  var latitude : Double,
  val longitude : Double
)
