package com.example.weatherapp.database

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

interface GeocodingDao {

  @Query("SELECT * From GeocodingResults where searchQuery == search_query")
  fun getResults( searchQuery : String) : GeocodingResults

  @Insert(onConflict = OnConflictStrategy.REPLACE)
   fun insertResult(geocodingResults: GeocodingResults?)

}
