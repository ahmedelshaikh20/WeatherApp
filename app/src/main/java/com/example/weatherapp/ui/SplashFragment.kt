package com.example.weatherapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentSplashBinding
import com.example.weatherapp.utils.checkFineLocation
import com.example.weatherapp.utils.checkLocationPermission
import com.example.weatherapp.viewmodel.SearchViewmodel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class SplashFragment : Fragment() {
  lateinit var viewModel: SearchViewmodel
  lateinit var binding: FragmentSplashBinding
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    binding = FragmentSplashBinding.inflate(inflater)
    viewModel = ViewModelProvider(requireActivity()).get(SearchViewmodel::class.java)
    if (checkFineLocation(requireActivity()))
      viewModel.LocationIsGranted()
    val requestPermission =
      registerForActivityResult(ActivityResultContracts.RequestPermission()) { isPermissionGranted: Boolean ->

        Log.d("isPermissionGranted", isPermissionGranted.toString())
        if (isPermissionGranted)
          viewModel.LocationIsGranted()
//        else
//          viewModel.OnLocationDismissed()

      }
      viewModel.viewModelScope.launch {
        viewModel.isPermissionGranted.collectLatest {
          viewModel.viewModelScope.launch {
            viewModel.OnLocationGranted()
          }
        }
      }

    checkLocationPermission(requireActivity(), requestPermission)

    viewModel.viewModelScope.launch {
      viewModel.apiRequestDone.collectLatest {

        if (it) {
          Navigation.findNavController(requireView())
            .navigate(R.id.action_splashFragment_to_currentLocationFragment)
        }
      }
    }


    return binding.root

  }
}
