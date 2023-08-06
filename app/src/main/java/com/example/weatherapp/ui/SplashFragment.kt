package com.example.weatherapp.ui

import android.content.Intent
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
import androidx.navigation.Navigation
import com.example.weatherapp.MainActivity
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentSplashBinding
import com.example.weatherapp.utils.checkFineLocation
import com.example.weatherapp.utils.checkLocationPermission
import com.example.weatherapp.viewmodel.searchviewmodel
import kotlinx.coroutines.launch


class SplashFragment : Fragment() {
  lateinit var viewModel: searchviewmodel
  lateinit var binding: FragmentSplashBinding
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    binding = FragmentSplashBinding.inflate(inflater)
    viewModel = ViewModelProvider(requireActivity()).get(searchviewmodel::class.java)
    if (checkFineLocation(requireActivity()))
      viewModel.LocationIsGranted()
    val requestPermission =
      registerForActivityResult(ActivityResultContracts.RequestPermission()) { isPermissionGranted: Boolean ->

        Log.d("isPermissionGranted", isPermissionGranted.toString())
        if (isPermissionGranted)
          viewModel.LocationIsGranted()
        else
          viewModel.OnLocationDismissed()

      }
    viewModel.isPermissionGranted.observe(this, Observer {
      viewModel.viewModelScope.launch {
        viewModel.OnLocationGranted()
      }
    })

    checkLocationPermission(requireActivity(), requestPermission)

    viewModel.apiRequestDone.observe(this) {

      if (it == true) {
        Navigation.findNavController(this.requireView())
          .navigate(R.id.action_splashFragment_to_currentLocationFragment)
      }
    }


    return binding.root

  }
}
