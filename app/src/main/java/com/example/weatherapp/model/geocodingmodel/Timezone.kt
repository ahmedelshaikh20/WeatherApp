package com.example.weatherapp.model.geocodingmodel

import com.google.gson.annotations.SerializedName

data class Timezone(
    val abbreviation_DST: String,
    val abbreviation_STD: String,
    val name: String,
    @SerializedName("offset_DST")
    val offsetDST: String,
    @SerializedName("offset_DST_seconds")
    val offsetDSTseconds: Int,
    @SerializedName("offset_STD")
    val offset_STD: String,
    @SerializedName("offset_STD_seconds")
    val offsetSTDseconds: Int
)
