package com.example.weatherapp.locationservices

import android.annotation.SuppressLint
import android.app.Activity
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext

import com.example.weatherapp.utils.checkFineLocation
import com.example.weatherapp.utils.checkLocationPermission
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LocationService(val fusedLocationClient: FusedLocationProviderClient) {

  @SuppressLint("MissingPermission")
  suspend fun getLastKnownLocation(): LatLng? {
    var userLocation: LatLng? = null
    return suspendCoroutine {
      fusedLocationClient.lastLocation
        .addOnSuccessListener { newlocation: Location? ->
          if (newlocation == null) {
            it.resume(userLocation)
          } else {
            userLocation = LatLng(newlocation.latitude, newlocation.longitude)
            it.resume(userLocation)
          }
        }

    }

  }
}

