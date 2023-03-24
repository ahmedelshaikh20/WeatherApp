package com.example.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class searchviewmodel : ViewModel() {

  var _currentLocation = MutableLiveData<String>()
  val currentLocation :LiveData<String>
  get() = _currentLocation


  init {

      _currentLocation.value = "Cairo"
  }
}
