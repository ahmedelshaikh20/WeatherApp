package com.example.weatherapp.di

import com.example.weatherapp.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class FirebaseModule {
  @Provides
  fun provideFirebaseAnalytics(): FirebaseAnalytics {
    return Firebase.analytics
  }

  @Provides
  fun provideGeminiAi(): GenerativeModel {
    return GenerativeModel(
      modelName = "gemini-pro",
      apiKey = BuildConfig.GEMINI_API_KEY
    )
  }
}
