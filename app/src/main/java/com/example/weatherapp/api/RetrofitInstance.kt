package com.example.weatherapp.api

import com.example.weatherapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val logging = HttpLoggingInterceptor().apply {
  this.level = HttpLoggingInterceptor.Level.BODY
};
var client = OkHttpClient.Builder()
  .addInterceptor(logging)
  .build()

object RetrofitInstance {
  val api : WeatherApi by lazy {
    Retrofit.Builder()
      .baseUrl(BuildConfig.BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .client(client)
      .build()
      .create(WeatherApi::class.java)

  }
}
