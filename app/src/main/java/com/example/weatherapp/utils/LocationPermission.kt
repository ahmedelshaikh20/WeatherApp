package com.example.weatherapp.utils

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat



fun checkLocationPermission(activity: Activity, requestPermission: ActivityResultLauncher<String>) {

  if (ActivityCompat.checkSelfPermission(
      activity,
      Manifest.permission.ACCESS_FINE_LOCATION
    ) != PackageManager.PERMISSION_GRANTED
  ) {
    requestPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
  }
}



fun checkFineLocation(activity: Activity): Boolean {
  return ActivityCompat.checkSelfPermission(
    activity,
    Manifest.permission.ACCESS_FINE_LOCATION
  ) == PackageManager.PERMISSION_GRANTED
}



