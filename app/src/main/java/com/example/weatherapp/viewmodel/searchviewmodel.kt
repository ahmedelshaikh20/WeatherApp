package com.example.weatherapp.viewmodel


import android.util.Log
import androidx.lifecycle.*
import com.example.weatherapp.api.WeatherRepositry
import com.example.weatherapp.locationservices.getLastKnownLocation
import com.example.weatherapp.model.WeatherApiResponse
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


  private var _weatherResponse = MutableLiveData<WeatherApiResponse>()
  val weatherResponse: LiveData<WeatherApiResponse>
    get() = _weatherResponse
  private var _geocodingResponse = MutableLiveData<List<Result>>()
  val geocodingResponse: LiveData<List<Result>>
    get() = _geocodingResponse

  private var _suggesstionList = MutableLiveData<List<String>>()
  val suggestionList: LiveData<List<String>>
    get() = _suggesstionList

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
      weatherRepository.getWeatherByLocation(latLng.latitude, latLng.longitude).body()
    updateData()

  }


  private fun updateData() {
    _currentLocation.value = weatherResponse.value?.name
    _currentweather.value = weatherResponse.value?.main?.temperature.toString()
    _currentHumidity.value = weatherResponse.value?.main?.humidity.toString()
    _currentDescription.value = weatherResponse.value?.weather?.get(0)?.description
    _currentPressure.value = weatherResponse.value?.main?.pressure.toString()
    _currenttime.value = getDate(weatherResponse.value?.dt!!, weatherResponse.value!!.timezone)
    _currentIcon.value = weatherResponse.value?.weather?.get(0)?.icon.toString()
  }


  private fun getDate(dt: Long, timezone: Int): String {
    val date = Date((dt) * 1000)
    val sdf = SimpleDateFormat("yyyy-MM-dd,HH:mm")
    val formattedDate = sdf.format(date)
    val time = formattedDate.split(',').get(1)
    return time;

  }


  fun searchTextChanged(searchQuery: String) {
    viewModelScope.launch {
      val suggestionList = ArrayList<String>()
      val countriesFound = ArrayList<String>()
      var response = weatherRepository.getWeatherByLocation(searchQuery).body()?.results
      response?.forEach {
        if (!countriesFound.contains(it.state+ "," + it.country_code) && !countriesFound.contains(it.city+ "," + it.country_code)) {
          suggestionList.add(it.formatted)
          if (it.city == null)
            countriesFound.add(it.state + "," + it.country_code)
          else
            countriesFound.add(it.city + "," + it.country_code)


        }
      }
      response = deleteRedundency(response, countriesFound)
      _geocodingResponse.value = response
      _suggesstionList.value = countriesFound.toList()


    }
  }

  private fun deleteRedundency(
    response: List<Result>?,
    countriesFound: ArrayList<String>
  ): List<Result>? {
    val newResponse = ArrayList<Result>()
    countriesFound.forEach {
      var curr_city = it
      if (response != null) {
        for (item in response) {
          if (curr_city .equals( item.city+","+item.country_code )|| curr_city.equals(item.state+","+item.country_code)) {
            newResponse.add(item)
            break
          }
        }
      }
    }
    Log.d("NewResponse", newResponse.size.toString())
    return newResponse.toList()
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

