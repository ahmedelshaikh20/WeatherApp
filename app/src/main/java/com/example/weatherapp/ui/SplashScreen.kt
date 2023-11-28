package com.example.weatherapp.ui

import android.Manifest
import android.util.Log
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asAndroidColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherapp.R
import com.example.weatherapp.navigation.Screen
import com.example.weatherapp.viewmodel.SearchViewmodel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SplashScreen(navController: NavController, searchViewModel: SearchViewmodel) {
  val apiRequestDone by searchViewModel.apiRequestDone.collectAsState()
  val locationPermissionState = rememberPermissionState(
    Manifest.permission.ACCESS_FINE_LOCATION
  )
  val locationIsGranted by searchViewModel.isPermissionGranted.collectAsState()



  LaunchedEffect(key1 = locationPermissionState.status) {
    if (locationPermissionState.status.isGranted) {
      searchViewModel.LocationIsGranted()
    } else {
      locationPermissionState.launchPermissionRequest()
    }
  }

  LaunchedEffect(key1 = locationIsGranted ){
    if (locationIsGranted)
      searchViewModel.OnLocationGranted()

  }
  LaunchedEffect(key1 = apiRequestDone) {

    if (apiRequestDone)
      navController.navigate(Screen.HomeScreen.route)
  }


  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(colorResource(id = R.color.splashbackground))
  ) {
    AppImage(
      resource = R.drawable.splash_background, modifier = Modifier
        .size(100.dp)
        .align(
          Alignment.Center
        )
    )
  }
}

//@Composable
//fun RequestPermission() {
//  val locationPermissions = arrayOf(
//    Manifest.permission.ACCESS_FINE_LOCATION,
//    Manifest.permission.ACCESS_COARSE_LOCATION)
//  val locationPermissionLauncher = rememberLauncherForActivityResult(
//    contract = ActivityResultContracts.RequestMultiplePermissions(),
//    onResult = { permissions ->
//      val permissionsGranted = permissions.values.reduce { acc, isPermissionGranted ->
//        acc && isPermissionGranted
//      }
//
//      if (!permissionsGranted) {
//        //Logic when the permissions were not granted by the user
//      }
//    })
//  locationPermissionLauncher.launch(locationPermissions)
//  val shouldShowPermissionRationale: Boolean = shouldShowRequestPermissionRationale(permission=Manifest.permission.ACCESS_COARSE_LOCATION)
//
//}

@Composable
fun AppImage(
  modifier: Modifier = Modifier,
  @DrawableRes resource: Int,
  colorFilter: ColorFilter? = null
) {
  AndroidView(
    modifier = modifier,
    factory = { context ->
      ImageView(context).apply {
        setImageResource(resource)
        setColorFilter(colorFilter?.asAndroidColorFilter())
      }
    },
    update = {
      it.setImageResource(resource)
      it.colorFilter = colorFilter?.asAndroidColorFilter()
    }
  )
}

