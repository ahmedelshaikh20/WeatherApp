package com.example.weatherapp.model.geocodingmodel

import com.google.gson.annotations.SerializedName

data class Result(
  @SerializedName("address_line1")
  val addressLine1: String,
  @SerializedName("address_line2")
  val addressLine2: String,
  val category: String,
  val city: String,
  val country: String,
  val country_code: String,
  val county: String,
  val county_code: String,
  @SerializedName("datasource")
  val dataInformation: DataInformation,
  val formatted: String,
  val lat: Double,
  val lon: Double,
  val municipality: String,
  val name: String,
  @SerializedName("place_id")
  val placeID: String,
  val postcode: String,
  val rank: Rank,
  @SerializedName("result_type")
  val resultType: String,
  val state: String,
  @SerializedName("timezone")
  val timeZone: Timezone
)
