package com.example.weatherapp.di

import com.example.weatherapp.BuildConfig
import com.example.weatherapp.api.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

  @Provides
  fun geoCodingApi(): GeocodingApi {
    return Retrofit.Builder()
      .baseUrl(BuildConfig.GECODING_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .client(client)
      .build()
      .create(GeocodingApi::class.java)
  }
  @Provides
  fun weatherApi(): WeatherApi {
    return Retrofit.Builder()
      .baseUrl(BuildConfig.BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .client(client)
      .build()
      .create(WeatherApi::class.java)
  }



}
