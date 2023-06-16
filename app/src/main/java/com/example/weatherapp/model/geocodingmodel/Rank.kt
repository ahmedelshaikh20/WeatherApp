package com.example.weatherapp.model.geocodingmodel

data class Rank(
    val confidence: Double,
    val confidence_city_level: Int,
    val importance: Double,
    val match_type: String
)
