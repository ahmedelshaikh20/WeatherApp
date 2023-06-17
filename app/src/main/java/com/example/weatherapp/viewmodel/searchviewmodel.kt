package com.example.weatherapp.viewmodel


import android.util.Log
import androidx.lifecycle.*
import com.example.weatherapp.api.WeatherRepositry
import com.example.weatherapp.locationservices.getLastKnownLocation
import com.example.weatherapp.model.WeatherApiResponse
import com.example.weatherapp.model.WeatherDataItem
import com.example.weatherapp.model.geocodingmodel.Result
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class searchviewmodel(
  val weatherRepository: WeatherRepositry,
  val fusedLocationClient: FusedLocationProviderClient
) : ViewModel() {


  private var _weatherResponse = MutableLiveData<WeatherDataItem>()
  val weatherResponse: LiveData<WeatherDataItem>
    get() = _weatherResponse

  private var _currentLocation = MutableLiveData<String>()
  val currentLocation: LiveData<String>
    get() = _currentLocation

  private var _currentweather = MutableLiveData<String>()
  val currentweather: LiveData<String>
    get() = _currentweather


  private var _currenttime = MutableLiveData<String>()
  val currenttimer: LiveData<String>
    get() = _currenttime

  private var _currentDescription = MutableLiveData<String>()
  val currentDescription: LiveData<String>
    get() = _currentDescription

  private var _currentHumidity = MutableLiveData<String>()
  val currentHumidity: LiveData<String>
    get() = _currentHumidity
  private var _currentPressure = MutableLiveData<String>()
  val currentPressure: LiveData<String>
    get() = _currentPressure

  private var _currentIcon = MutableLiveData<String>()
  val currentIcon: LiveData<String>
    get() = _currentIcon


  private var _isPermissionGranted = MutableLiveData<Boolean>()
  val isPermissionGranted: LiveData<Boolean>
    get() = _isPermissionGranted

  private var _foucsOnSearch = MutableLiveData<Boolean>()
  val foucsOnSearch: LiveData<Boolean>
    get() = _foucsOnSearch

  init {
    _currentLocation.value = "Cairo"
  }

  suspend fun OnLocationGranted() {
    viewModelScope.launch {
      val latLng = getLastKnownLocation(fusedLocationClient)
      latLng?.let { getWeatherData(it) }
    }

  }

  fun OnLocationDismissed() {
    _foucsOnSearch.value = true
  }

  fun LocationIsGranted() {
    _isPermissionGranted.value = true
  }


  suspend fun getWeatherData(latLng: LatLng) {

    _weatherResponse.value =
      weatherRepository.getWeatherByLocation(latLng.latitude, latLng.longitude)
    updateData()

  }


  private fun updateData() {
    _currentLocation.value = weatherResponse.value?.name
    _currentweather.value = weatherResponse.value?.temperature.toString()
    _currentHumidity.value = weatherResponse.value?.humidity.toString()
    _currentDescription.value = weatherResponse.value?.description
    _currentPressure.value = weatherResponse.value?.pressure.toString()
    _currenttime.value = getDate(weatherResponse.value?.time!!)
    _currentIcon.value = weatherResponse.value?.icon.toString()
  }


  private fun getDate(dt: Long): String {
    val date = Date((dt) * 1000)
    val sdf = SimpleDateFormat("yyyy-MM-dd,HH:mm")
    val formattedDate = sdf.format(date)
    val time = formattedDate.split(',').get(1)
    return time;

  }


}


//Too pass an argument to viewmodel constructor we need to create our own implementation of the viewmodelProvider Factory

@Suppress("UNCHECKED_CAST")
class SearchViewModelFactory(
  private val weatherRepositry: WeatherRepositry,
  private val fusedLocationClient: FusedLocationProviderClient
) : ViewModelProvider.NewInstanceFactory() {
  override fun <T : ViewModel> create(modelClass: Class<T>) =
    (searchviewmodel(weatherRepositry, fusedLocationClient) as T)
}

