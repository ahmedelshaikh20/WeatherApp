package com.example.weatherapp.viewmodel

import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.model.WeatherApiResponse

class searchviewmodel : ViewModel() {

  var _weatherResponse = MutableLiveData<WeatherApiResponse>()
  val weatherResponse: LiveData<WeatherApiResponse>
    get() = _weatherResponse

  var _currentLocation = MutableLiveData<String>()
  val currentLocation: LiveData<String>
    get() = _currentLocation

  var _currentweather = MutableLiveData<String>()
  val currentweather: LiveData<String>
    get() = _currentweather


  var _currenttime= MutableLiveData<String>()
  val currenttimer: LiveData<String>
    get() = _currenttime

  var _currentuv = MutableLiveData<String>()
  val currentuv: LiveData<String>
    get() = _currentuv

  var _currentrain = MutableLiveData<String>()
  val currentrain: LiveData<String>
    get() = _currentrain
  var _currentAQ = MutableLiveData<String>()
  val currentAQ: LiveData<String>
    get() = _currentAQ

  init {
    _currentLocation.value = "Cairo"

  }


}
