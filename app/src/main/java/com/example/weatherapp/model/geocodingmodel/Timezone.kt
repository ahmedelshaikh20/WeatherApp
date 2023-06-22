package com.example.weatherapp.model.geocodingmodel

import com.google.gson.annotations.SerializedName

data class Timezone(
  @SerializedName("abbreviation_DST")
  val abbreviationDST: String,
  @SerializedName("abbreviation_STD")
  val abbreviationSTD: String,
  val name: String,
  @SerializedName("offset_DST")
  val offsetDST: String,
  @SerializedName("offset_DST_seconds")
  val offsetDSTSeconds: Int,
  @SerializedName("offset_STD")
  val offset_STD: String,
  @SerializedName("offset_STD_seconds")
  val offsetSTDSeconds: Int
)
