package com.example.weatherapp


import androidx.activity.compose.setContent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.example.weatherapp.navigation.Navigation
import com.example.weatherapp.viewmodel.SearchViewmodel
import dagger.hilt.android.AndroidEntryPoint
import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  val viewModel: SearchViewmodel by viewModels()
  @SuppressLint("SuspiciousIndentation")
  @RequiresApi(Build.VERSION_CODES.M)

  override fun onCreate(savedInstanceState: Bundle?) {
    setContent {

      Navigation()

      }
    super.onCreate(savedInstanceState)

  }


}






