package com.example.weatherapp


import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.example.weatherapp.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  @SuppressLint("SuspiciousIndentation")
  @RequiresApi(Build.VERSION_CODES.M)

  override fun onCreate(savedInstanceState: Bundle?) {
    setContent {
      Navigation()
    }
    super.onCreate(savedInstanceState)
  }


}






