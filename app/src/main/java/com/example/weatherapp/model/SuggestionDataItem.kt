package com.example.weatherapp.model

data class SuggestionDataItem(
  var city :String? =null ,
  var country :String? = null,
  var countryCode : String? =null,
  var state : String? =null,
  var formattedAddress : String? =null,
  var latitude : Double?=null,
  val longitude : Double?=null
)
