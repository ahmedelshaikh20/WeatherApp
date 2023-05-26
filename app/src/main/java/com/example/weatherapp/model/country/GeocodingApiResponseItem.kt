package com.example.weatherapp.model.country

data class GeocodingApiResponseItem(
    val country: String,
    val lat: Double,
    val local_names: LocalNames,
    val lon: Double,
    val name: String,
    val state: String
)
