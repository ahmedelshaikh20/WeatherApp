package com.example.weatherapp.di

import android.app.Application
import com.example.weatherapp.locationservices.LocationService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LocationServicesModule {


  @Provides
  fun provideFusedLocationClient(appContext: Application):FusedLocationProviderClient{
    return LocationServices.getFusedLocationProviderClient(appContext.applicationContext)
  }

}
