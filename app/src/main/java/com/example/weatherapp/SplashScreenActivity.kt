package com.example.weatherapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.weatherapp.utils.checkLocationPermission
import com.example.weatherapp.viewmodel.searchviewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

 val viewModel: searchviewmodel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))

      viewModel.apiRequestDone.observe(this) {

        if (it == true) {

          finish()
        }

      }


    }
}
