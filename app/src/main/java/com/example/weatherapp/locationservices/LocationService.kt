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
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

//



@SuppressLint("MissingPermission")
suspend fun getLastKnownLocation(fusedLocationClient  :FusedLocationProviderClient, activity: Activity): LatLng? {
    return suspendCoroutine {
      val user_location = LatLng(-34.0, 151.0);
      if(!checkFineLocation(activity)){
        Log.d("LOCATION", checkFineLocation(activity).toString() )
        fusedLocationClient.lastLocation
        .addOnSuccessListener { newlocation: Location? ->
          Log.d("LOCATION", newlocation.toString())
          if(newlocation == null){
            Toast.makeText(activity, "Cannot get location.", Toast.LENGTH_SHORT).show()
          }
          else {
            var location: LatLng
            location = LatLng(newlocation.latitude, newlocation.longitude)

            it.resume(location)}
        }


      }
      else{
      checkLocationPermission(activity)
      it.resume(user_location)}
    }

}

