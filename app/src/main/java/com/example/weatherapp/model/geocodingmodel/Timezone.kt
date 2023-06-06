package com.example.weatherapp.model.geocodingmodel

data class Timezone(
    val abbreviation_DST: String,
    val abbreviation_STD: String,
    val name: String,
    val offset_DST: String,
    val offset_DST_seconds: Int,
    val offset_STD: String,
    val offset_STD_seconds: Int
)
