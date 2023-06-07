package com.example.weatherapp.database

import androidx.room.ColumnInfo

data class GeocodingResults(
  @ColumnInfo(name = "search_query")
  val searchQuery : String ,
  @ColumnInfo(name = "results")
  val results: GeocodingResults?


)



