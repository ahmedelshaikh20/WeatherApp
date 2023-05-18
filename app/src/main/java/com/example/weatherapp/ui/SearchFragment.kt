package com.example.weatherapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.api.WeatherRepositry
import com.example.weatherapp.databinding.FragmentSearchBinding
import com.example.weatherapp.utils.checkFineLocation
import com.example.weatherapp.utils.checkLocationPermission
import com.example.weatherapp.viewmodel.SearchViewModelFactory
import com.example.weatherapp.viewmodel.searchviewmodel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {
  private lateinit var viewModel: searchviewmodel
  private lateinit var binding: FragmentSearchBinding
  private lateinit var fusedLocationClient: FusedLocationProviderClient
  private lateinit var weatherRepositry: WeatherRepositry


  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    binding = FragmentSearchBinding.inflate(inflater)
    weatherRepositry = WeatherRepositry()
    val viewModelFactory = SearchViewModelFactory(weatherRepositry , fusedLocationClient)
     viewModel = ViewModelProvider(this, viewModelFactory).get(searchviewmodel::class.java)
    viewModel.currentLocation.observe(viewLifecycleOwner, Observer {
      binding.currentLocation.text = it
    })

    viewModel.currentweather.observe(viewLifecycleOwner, Observer {
      binding.temperature.text = it
    })
    viewModel.currentHumidity.observe(viewLifecycleOwner, Observer {
      binding.humidity.text = it+" %"
    })
    viewModel.currentPressure.observe(viewLifecycleOwner, Observer {
      binding.pressure.text = it+" hPa"
    })
    viewModel.currenttimer.observe(viewLifecycleOwner, Observer {
      binding.time.text = it
    })
    viewModel.currentDescription.observe(viewLifecycleOwner, Observer {
      binding.description.text = it
    })
//
    viewModel.isPermissionGranted.observe(viewLifecycleOwner , Observer {
      viewModel.viewModelScope.launch {
      viewModel.OnLocationGranted()}
    })

    viewModel.foucsOnSearch.observe(viewLifecycleOwner , Observer {
      if (it ){
        binding.searchView.requestFocus()
      }
    })
    val requestPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isPermissionGranted: Boolean ->

      Log.d("isPermissionGranted" , isPermissionGranted.toString())
      if (isPermissionGranted)
        viewModel.LocationIsGranted()
      else
        viewModel.OnLocationDismissed()

    }
    checkLocationPermission( requireActivity() ,requestPermission)
    return binding.root
  }


  override fun onResume() {
    if (checkFineLocation(requireActivity()))
      viewModel.LocationIsGranted()
    super.onResume()
  }



  //TO GET LAST KNOWN LOCATION 3 STEPS NEEDED
  //1- Check if location permission granted or not.
  //2- If not go and request it.
  //3- If granted then get last known location (Lat and Long as its needed for open weather api).

}
