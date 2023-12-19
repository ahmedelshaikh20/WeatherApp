package com.example.weatherapp.locationservices

import android.annotation.SuppressLint
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Singleton
class LocationService @Inject constructor(val fusedLocationClient: FusedLocationProviderClient) {

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

