package com.example.weatherapp.model.geocodingmodel

import com.google.gson.annotations.SerializedName

data class Rank(
    val confidence: Double,
    @SerializedName("confidence_city_level")
    val confidenceCityLevel: Double,
    val importance: Double,
    @SerializedName("match_type")
    val matchType: String
)
