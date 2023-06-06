package com.example.weatherapp.model.geocodingmodel

data class Result(
    val address_line1: String,
    val address_line2: String,
    val category: String,
    val city: String,
    val country: String,
    val country_code: String,
    val county: String,
    val county_code: String,
    val datasource: Datasource,
    val formatted: String,
    val lat: Double,
    val lon: Double,
    val municipality: String,
    val name: String,
    val place_id: String,
    val postcode: String,
    val rank: Rank,
    val result_type: String,
    val state: String,
    val timezone: Timezone
)
