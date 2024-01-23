package com.example.weatherapp.viewmodel


import android.util.Log
import androidx.lifecycle.*
import com.example.weatherapp.api.WeatherRepositry
import com.example.weatherapp.locationservices.LocationService
import com.example.weatherapp.model.WeatherDataItem
import com.google.ai.client.generativeai.GenerativeModel
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
class SearchViewModel @Inject constructor(
  private val weatherRepository: WeatherRepositry,
  private val locationService: LocationService,
  private val geminiAi: GenerativeModel
) : ViewModel() {


  private var _weatherResponse = MutableStateFlow<WeatherDataItem?>(null)
  val weatherResponse = _weatherResponse.asStateFlow()

  private var _isPermissionGranted = MutableStateFlow(false)
  val isPermissionGranted = _isPermissionGranted.asStateFlow()


  private var _focusOnSearch = MutableStateFlow(false)
  val focusOnSearch = _focusOnSearch.asStateFlow()

  private var _apiRequestDone = MutableStateFlow<Boolean>(false)
  val apiRequestDone = _apiRequestDone.asStateFlow()

  private var _recommendedOutfit = MutableStateFlow<String>("")
  val recommendedOutfit = _recommendedOutfit.asStateFlow()


  init {

  }

  suspend fun OnLocationGranted() {
    viewModelScope.launch {
      val latLng = locationService.getLastKnownLocation()
      latLng?.let { getWeatherData(it) }
    }

  }

  fun recommendOutfitAi(weather: Double?) {
    val prompt =
      "Recommend outfit for that weather $weather in Celsius? , i need answer in four words"
    viewModelScope.launch {
      if (weather != null) {
        val response = geminiAi.generateContent(prompt)
        _recommendedOutfit.value = response.text.toString()
      }
    }
  }

  fun LocationIsGranted() {
    _isPermissionGranted.update { true }
    viewModelScope.launch {
      OnLocationGranted()
    }
  }


  suspend fun getWeatherData(latLng: LatLng) {
    _weatherResponse.update {
      weatherRepository.getWeatherByLocation(latLng.latitude, latLng.longitude)

    }
    recommendOutfitAi(weatherResponse.value?.temperature)
    _apiRequestDone.update { true }
  }


  fun citySelected(selectedCity: WeatherDataItem) {
    _weatherResponse.update { selectedCity }
  }


}



