package com.example.weatherapp.viewmodel


import android.util.Log
import androidx.lifecycle.*
import com.example.weatherapp.api.WeatherRepositry
import com.example.weatherapp.locationservices.LocationService
import com.example.weatherapp.model.WeatherDataItem
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SearchViewmodel @Inject constructor(
  val weatherRepository: WeatherRepositry,
  val locationService: LocationService
) : ViewModel() {


  private var _weatherResponse = MutableStateFlow(WeatherDataItem())
  val weatherResponse = _weatherResponse.asStateFlow()

  private var _isPermissionGranted = MutableStateFlow(false)
  val isPermissionGranted = _isPermissionGranted.asStateFlow()


  private var _focusOnSearch = MutableStateFlow(false)
  val focusOnSearch = _focusOnSearch.asStateFlow()

  private var _apiRequestDone = MutableStateFlow<Boolean>(false)
  val apiRequestDone = _apiRequestDone.asStateFlow()

  init {
  }

  suspend fun OnLocationGranted() {
    viewModelScope.launch {
      val latLng = locationService.getLastKnownLocation()
      latLng?.let { getWeatherData(it) }
    }

  }

//  fun OnLocationDismissed() {
//    _focusOnSearch.update { true }
//  }

  fun LocationIsGranted() {
    _isPermissionGranted.update { true }
    viewModelScope.launch {
      OnLocationGranted()
    }
  }


  suspend fun getWeatherData(latLng: LatLng) {
    _weatherResponse.update {
      weatherRepository.getWeatherByLocation(latLng.latitude, latLng.longitude)!!
    }

    _apiRequestDone.update {true}
    Log.d("Ahhhmed" , _weatherResponse.value.toString())
  }


  fun getDate(dt: Long): String {
    val date = Date((dt) * 1000)
    val sdf = SimpleDateFormat("yyyy-MM-dd,HH:mm")
    val formattedDate = sdf.format(date)
    val time = formattedDate.split(',').get(1)
    return time;

  }

  fun citySelected(selectedCity: WeatherDataItem) {
    _weatherResponse.update {   selectedCity}
  }


}


//Too pass an argument to viewmodel constructor we need to create our own implementation of the viewmodelProvider Factory

@Suppress("UNCHECKED_CAST")
class SearchViewModelFactory(
  private val weatherRepositry: WeatherRepositry,
  private val locationService: LocationService
) : ViewModelProvider.NewInstanceFactory() {
  override fun <T : ViewModel> create(modelClass: Class<T>) =
    (SearchViewmodel(weatherRepositry, locationService) as T)
}

