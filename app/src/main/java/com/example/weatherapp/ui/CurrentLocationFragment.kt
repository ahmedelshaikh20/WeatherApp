package com.example.weatherapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentCurrentlocationBinding
import com.example.weatherapp.utils.checkFineLocation
import com.example.weatherapp.viewmodel.SearchViewmodel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CurrentLocationFragment : Fragment() {
  private lateinit var binding: FragmentCurrentlocationBinding
  lateinit var viewModel: SearchViewmodel
  private lateinit var searchView: SearchView
  @SuppressLint("SetTextI18n")
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View? {

    viewModel = ViewModelProvider(requireActivity()).get(SearchViewmodel::class.java)
    binding = FragmentCurrentlocationBinding.inflate(inflater)
    searchView = binding.search

    viewModel.viewModelScope.launch {
      viewModel.weatherResponse.collect() {
        binding.currentLocation.text = it.name
        binding.description.text = it.description
        binding.temperature.text = it.temperature.toString()
        binding.humidity.text = it.humidity.toString() + " %"
        binding.pressure.text = it.pressure.toString() + " hPa"
        binding.time.text = viewModel.getDate(it.time)
        val iconUrl = getString(R.string.baseIconUrl) + it.icon + "@2x.png"
        Glide.with(requireContext()).load(iconUrl).apply(RequestOptions().override(200, 200))
          .into(binding.weatherIcon)

      }
    }

    binding.search.setOnSearchClickListener {
      Navigation.findNavController(this.requireView())
        .navigate(R.id.action_currentLocationFragment_to_searchFragment)

    }


    return binding.root

  }

  override fun onResume() {
    val bundle = arguments
    val args = bundle?.let { CurrentLocationFragmentArgs.fromBundle(it) }
    Log.d("Selected City", args?.selectedCity.toString())

    if (args?.selectedCity != null) {
      viewModel.citySelected(args.selectedCity!!)
    } else {
      if (checkFineLocation(requireActivity())) viewModel.LocationIsGranted()
    }
    super.onResume()
  }


}
