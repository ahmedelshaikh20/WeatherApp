package com.example.weatherapp.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.weatherapp.api.WeatherRepositry
import com.example.weatherapp.model.SugesstionDataItem
import com.example.weatherapp.model.WeatherApiResponse
import com.example.weatherapp.model.WeatherDataItem
import com.example.weatherapp.model.geocodingmodel.Result
import kotlinx.coroutines.launch

class searchListviewmodel(val weatherRepository: WeatherRepositry) : ViewModel() {

  private var _geocodingResponse = MutableLiveData<List<SugesstionDataItem>>()
  val geocodingResponse: LiveData<List<SugesstionDataItem>>
    get() = _geocodingResponse

  private var _suggestionList = MutableLiveData<List<WeatherDataItem>>()
  val suggestionList: LiveData<List<WeatherDataItem>>
    get() = _suggestionList




  fun searchTextChanged(searchQuery: String) {
    viewModelScope.launch {
      val suggestionList = ArrayList<String>()
      val countriesFound = ArrayList<String>()
      var results = weatherRepository.getWeatherByLocation(searchQuery)
      results?.forEach {
        if (!countriesFound.contains(it.state + "," + it.countryCode) && !countriesFound.contains(
            it.city + "," + it.countryCode
          )
        ) {
          suggestionList.add(it.formattedAddress)
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

  private fun updateSuggestionList() {
    val res = ArrayList<WeatherDataItem>()
    geocodingResponse.value?.forEach {
      viewModelScope.launch {
        val itemRes = weatherRepository.getWeatherByLocation(it.latitude , it.longitude)
        itemRes?.let { it1 -> res.add(it1) }
        _suggestionList.value = res.toList()

      }

    }

    Log.d("LOOOL" , res.toString())
  }

  private fun deleteRedundancy(
    response: List<SugesstionDataItem>?,
    countriesFound: ArrayList<String>
  ): List<SugesstionDataItem> {
    val newResponse = ArrayList<SugesstionDataItem>()
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

}

@Suppress("UNCHECKED_CAST")
class SearchListViewModelFactory(
  private val weatherRepositry: WeatherRepositry
) : ViewModelProvider.NewInstanceFactory() {
  override fun <T : ViewModel> create(modelClass: Class<T>) =
    (searchListviewmodel(weatherRepositry) as T)
}
