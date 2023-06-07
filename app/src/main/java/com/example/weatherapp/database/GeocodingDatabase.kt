package com.example.weatherapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [GeocodingResults::class], version = 1)
abstract class GeocodingDatabase : RoomDatabase() {
  abstract val geocodingDao: GeocodingDao

  companion object {

    @Volatile
    private var INSTANCE: GeocodingDatabase? = null

    fun getInstance(context: Context): GeocodingDatabase {
      synchronized(this) {
        var instance = INSTANCE
        if (instance == null) {
          instance = Room.databaseBuilder(
            context.applicationContext,
            GeocodingDatabase::class.java,
            "geocoding_database"
          )
            .fallbackToDestructiveMigration()
            .build()

          INSTANCE = instance
        }

        return instance
      }
    }

  }
}

