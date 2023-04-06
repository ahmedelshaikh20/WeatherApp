package com.example.weatherapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentSearchBinding
import com.example.weatherapp.utils.checkFineLocation
import com.example.weatherapp.utils.checkLocationPermission
import com.example.weatherapp.viewmodel.searchviewmodel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.NonDisposableHandle.parent


class SearchFragment : Fragment() {
  private lateinit var viewModel: searchviewmodel
  private lateinit var binding: FragmentSearchBinding
  private lateinit var fusedLocationClient: FusedLocationProviderClient


  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    binding = FragmentSearchBinding.inflate(inflater)
    viewModel = ViewModelProvider(this).get(searchviewmodel::class.java)
    viewModel.currentLocation.observe(viewLifecycleOwner, Observer {
      binding.currentLocation.text = it
    })

    binding.imageView.setOnClickListener {
     checkLocationPermission(requireActivity())
      getLastKnownLocation()
    }
    return binding.root
  }


  //TO GET LAST KNOWN LOCATION 3 STEPS NEEDED
  //1- Check if location permission granted or not.
  //2- If not go and request it.
  //3- If granted then get last known location (Lat and Long as its needed for open weather api).
  fun getLastKnownLocation(){
if(checkFineLocation(requireActivity())) {
  fusedLocationClient.lastLocation.addOnSuccessListener {
    
  }
} else
checkLocationPermission(requireActivity())
  }

}
