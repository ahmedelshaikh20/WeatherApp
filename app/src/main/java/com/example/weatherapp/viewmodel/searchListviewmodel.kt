package com.example.weatherapp.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.weatherapp.api.WeatherRepositry
import com.example.weatherapp.model.WeatherApiResponse
import com.example.weatherapp.model.geocodingmodel.Result
import kotlinx.coroutines.launch

class searchListviewmodel(val weatherRepository: WeatherRepositry) : ViewModel() {

  private var _geocodingResponse = MutableLiveData<List<Result>>()
  val geocodingResponse: LiveData<List<Result>>
    get() = _geocodingResponse

  private var _suggestionList = MutableLiveData<List<WeatherApiResponse>>()
  val suggestionList: LiveData<List<WeatherApiResponse>>
    get() = _suggestionList




  fun searchTextChanged(searchQuery: String) {
    viewModelScope.launch {
      val suggestionList = ArrayList<String>()
      val countriesFound = ArrayList<String>()
      var response = weatherRepository.getWeatherByLocation(searchQuery).body()?.results
      response?.forEach {
        if (!countriesFound.contains(it.state + "," + it.country_code) && !countriesFound.contains(
            it.city + "," + it.country_code
          )
        ) {
          suggestionList.add(it.formatted)
          if (it.city == null)
            countriesFound.add(it.state + "," + it.country_code)
          else
            countriesFound.add(it.city + "," + it.country_code)
        }
      }
      response = deleteRedundancy(response, countriesFound)
      _geocodingResponse.value = response
      updateSuggestionList()

    }
  }

  private fun updateSuggestionList() {
    val res = ArrayList<WeatherApiResponse>()
    geocodingResponse.value?.forEach {
      viewModelScope.launch {
        val itemRes = weatherRepository.getWeatherByLocation(it.lat , it.lon)
        itemRes.body()?.let { it1 -> res.add(it1) }
        _suggestionList.value = res.toList()

      }

    }

    Log.d("LOOOL" , res.toString())
  }

  private fun deleteRedundancy(
    response: List<Result>?,
    countriesFound: ArrayList<String>
  ): List<Result>? {
    val newResponse = ArrayList<Result>()
    countriesFound.forEach {
      var curr_city = it
      if (response != null) {
        for (item in response) {
          if (curr_city.equals(item.city + "," + item.country_code) || curr_city.equals(item.state + "," + item.country_code)) {
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

@Suppress("UNCHECKED_CAST")
class SearchListViewModelFactory(
  private val weatherRepositry: WeatherRepositry
) : ViewModelProvider.NewInstanceFactory() {
  override fun <T : ViewModel> create(modelClass: Class<T>) =
    (searchListviewmodel(weatherRepositry) as T)
}
