package com.example.weatherapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentCurrentlocationBinding
import com.example.weatherapp.utils.checkFineLocation
import com.example.weatherapp.utils.checkLocationPermission
import com.example.weatherapp.viewmodel.searchviewmodel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CurrentLocationFragment : Fragment() {
  private lateinit var binding: FragmentCurrentlocationBinding
  val viewModel: searchviewmodel by viewModels()

  //  lateinit var viewModel : searchviewmodel
  private lateinit var searchView: SearchView
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    binding = FragmentCurrentlocationBinding.inflate(inflater)
    searchView = binding.search

    viewModel.currentLocation.observe(viewLifecycleOwner, Observer {
      binding.currentLocation.text = it
    })

    viewModel.currentweather.observe(viewLifecycleOwner, Observer {
      binding.temperature.text = it
    })
    viewModel.currentHumidity.observe(viewLifecycleOwner, Observer {
      binding.humidity.text = it + " %"
    })
    viewModel.currentPressure.observe(viewLifecycleOwner, Observer {
      binding.pressure.text = it + " hPa"
    })
    viewModel.currenttimer.observe(viewLifecycleOwner, Observer {
      binding.time.text = it
    })
    viewModel.currentDescription.observe(viewLifecycleOwner, Observer {
      binding.description.text = it
    })


    viewModel.currentIcon.observe(viewLifecycleOwner, Observer {
      val iconUrl = getString(R.string.baseIconUrl) + it + "@2x.png"

      Glide.with(requireContext())
        .load(iconUrl)
        .apply(RequestOptions().override(200, 200))
        .into(binding.weatherIcon)
    })
    viewModel.isPermissionGranted.observe(viewLifecycleOwner, Observer {
      viewModel.viewModelScope.launch {
        viewModel.OnLocationGranted()
      }
    })

    viewModel.foucsOnSearch.observe(viewLifecycleOwner, Observer {
      if (it) {
        binding.search.requestFocus()
      }
    })
    binding.search.setOnSearchClickListener {
      Navigation.findNavController(this.requireView())
        .navigate(R.id.action_currentLocationFragment_to_searchFragment)

    }
    val bundle = arguments
    val args = bundle?.let { CurrentLocationFragmentArgs.fromBundle(it) }
    Log.d("Selected City", args?.selectedCity.toString())

    if (args?.selectedCity != null) {
      viewModel.citySelected(args.selectedCity!!)
    } else {
      val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isPermissionGranted: Boolean ->

          Log.d("isPermissionGranted", isPermissionGranted.toString())
          if (isPermissionGranted)
            viewModel.LocationIsGranted()
          else
            viewModel.OnLocationDismissed()

        }

      checkLocationPermission(requireActivity(), requestPermission)
    }
    return binding.root

  }

  override fun onResume() {

//    if (checkFineLocation(requireActivity()))
//      viewModel.LocationIsGranted()
    super.onResume()
  }


}
