package com.example.weatherapp.utils

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat

private const val MY_PERMISSIONS_REQUEST_LOCATION = 99


fun checkLocationPermission(activity: Activity) {
  if (ActivityCompat.checkSelfPermission(
      activity,
      Manifest.permission.ACCESS_FINE_LOCATION
    ) != PackageManager.PERMISSION_GRANTED
  ) {
    // Should we show an explanation?
    if (ActivityCompat.shouldShowRequestPermissionRationale(
        activity,
        Manifest.permission.ACCESS_FINE_LOCATION
      )
    ) {
      AlertDialog.Builder(activity)
        .setTitle("Location Permission Needed")
        .setMessage("This app needs the Location permission, please accept to use location functionality")
        .setPositiveButton(
          "OK"
        ) { _, _ ->
          //Prompt the user once explanation has been shown
          requestLocationPermission(activity)
        }
        .create()
        .show()
    } else {
      // No explanation needed, we can request the permission.
      requestLocationPermission(activity)
    }
  }
}

private fun requestLocationPermission(activity: Activity) {
  ActivityCompat.requestPermissions(
    activity,
    arrayOf(
      Manifest.permission.ACCESS_FINE_LOCATION,
    ),
    MY_PERMISSIONS_REQUEST_LOCATION
  )
}

fun checkFineLocation(activity: Activity): Boolean {
  return ActivityCompat.shouldShowRequestPermissionRationale(
    activity,
    Manifest.permission.ACCESS_FINE_LOCATION
  )

}
