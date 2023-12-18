package com.example.weatherapp.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.weatherapp.api.WeatherRepositry
import com.example.weatherapp.model.SuggestionDataItem
import com.example.weatherapp.model.WeatherDataItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchListViewModel @Inject constructor(
  val weatherRepository: WeatherRepositry
) : ViewModel() {

  private var _geocodingResponse = MutableStateFlow(listOf<SuggestionDataItem>())
  val geocodingResponse = _geocodingResponse.asStateFlow()

  private var _suggestionList = MutableStateFlow(listOf<WeatherDataItem>())
  val suggestionList = _suggestionList.asStateFlow()

  private var _searchQuery = MutableStateFlow("")
  val searchQuery = _searchQuery.asStateFlow()


  fun searchTextChanged() {
    viewModelScope.launch {
      val suggestionList = ArrayList<String>()
      val countriesFound = ArrayList<String>()
      var results = weatherRepository.getWeatherByLocation(searchQuery.value)
      results?.forEach {
        if (!countriesFound.contains(it.state + "," + it.countryCode) && !countriesFound.contains(
            it.city + "," + it.countryCode
          )
        ) {
          it.formattedAddress?.let { it1 -> suggestionList.add(it1) }
          if (it.city == null)
            countriesFound.add(it.state + "," + it.countryCode)
          else
            countriesFound.add(it.city + "," + it.countryCode)
        }
      }

      results = deleteRedundancy(results, countriesFound)
      _geocodingResponse.value = results
      updateSuggestionList()
    }

  }


  init {
    viewModelScope.launch {
      searchQuery.debounce(500).collectLatest {
        searchTextChanged()
      }
    }

  }

  fun updateSearchQuery(searchQuery: String) {
    _searchQuery.value = searchQuery
  }

  private fun updateSuggestionList() {
    val res = ArrayList<WeatherDataItem>()
    if (geocodingResponse.value.isEmpty()){
      _suggestionList.value= emptyList()
    }
    else {
    geocodingResponse.value?.forEach {
      viewModelScope.launch {
        val itemRes = it.latitude?.let { it1 ->
          it.longitude?.let { it2 ->
            weatherRepository.getWeatherByLocation(
              it1,
              it2
            )
          }
        }
        itemRes?.let { it1 -> res.add(it1) }
        _suggestionList.value = res.toList()

      }

    }}

  }

  private fun deleteRedundancy(
    response: List<SuggestionDataItem>?,
    countriesFound: ArrayList<String>
  ): List<SuggestionDataItem> {
    val newResponse = ArrayList<SuggestionDataItem>()
    countriesFound.forEach {
      var curr_city = it
      if (response != null) {
        for (item in response) {
          if (curr_city.equals(item.city + "," + item.countryCode) || curr_city.equals(item.state + "," + item.countryCode)) {
            newResponse.add(item)
            break
          }
        }
      }
    }
    return newResponse.toList()
  }

  fun EmptySuggestionList() {
    _suggestionList.update {
      listOf()
    }
  }

}

