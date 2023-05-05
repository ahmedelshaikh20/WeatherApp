package com.example.weatherapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.example.weatherapp.API_KEY
import com.example.weatherapp.R
import com.example.weatherapp.api.RetrofitInstance
import com.example.weatherapp.databinding.FragmentSearchBinding
import com.example.weatherapp.utils.checkFineLocation
import com.example.weatherapp.utils.checkLocationPermission
import com.example.weatherapp.viewmodel.searchviewmodel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*


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
    viewModel.weatherResponse.observe(viewLifecycleOwner, Observer {
      viewModel._currentLocation.value = it.name
      var image_url = "https://openweathermap.org/img/wn/" + it.weather[0].icon + "@4x.png"
      Glide.with(this)
        .load(image_url) // image url
        .placeholder(R.drawable.ic_launcher_background) // any placeholder to load at start
        .error(R.drawable.ic_launcher_background)  // any image in case of error
        .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) // resizing
        .centerCrop()
        .into(binding.imageView2);
      viewModel._currentweather.value = it.main.temp.toString()
      val current_time = getDate(it.dt.toLong(), it.timezone)
      viewModel._currenttime.value = current_time
      viewModel._currentuv.value = "3.13"
      viewModel._currentAQ.value = "14"
      if (it.toString().contains("Rain"))
        viewModel._currentrain.value = it.rain.`1h`.toString()
      else
        viewModel._currentrain.value = "0"
    })
    viewModel.currentweather.observe(viewLifecycleOwner, Observer {
      binding.temperature.text = it
    })
    viewModel.currentrain.observe(viewLifecycleOwner, Observer {
      binding.rain.text = it+" %"
    })
    viewModel.currentAQ.observe(viewLifecycleOwner, Observer {
      binding.AQ.text = it
    })
    viewModel.currenttimer.observe(viewLifecycleOwner, Observer {
      binding.time.text = it
    })
    viewModel.currentuv.observe(viewLifecycleOwner, Observer {
      binding.uv.text = it
    })

    binding.imageView.setOnClickListener {
      checkLocationPermission(requireActivity())

      getLastKnownLocation()
    }
    return binding.root
  }

  private fun getDate(dt: Long, timezone: Int): String {
    val date = Date((dt) * 1000)
    val sdf = SimpleDateFormat("yyyy-MM-dd,HH:mm")
    val formattedDate = sdf.format(date)
    val time = formattedDate.split(',').get(1)
    return time;

  }


  //TO GET LAST KNOWN LOCATION 3 STEPS NEEDED
  //1- Check if location permission granted or not.
  //2- If not go and request it.
  //3- If granted then get last known location (Lat and Long as its needed for open weather api).
  fun getLastKnownLocation() {
    Log.d("Last Location", checkFineLocation(requireActivity()).toString())

    if (!checkFineLocation(requireActivity())) {
      fusedLocationClient.lastLocation.addOnSuccessListener {
        runBlocking {
          Log.d("Last Location", it.latitude.toString())
          var response =
            RetrofitInstance.api.getCurrentWeather(it.latitude, it.longitude, API_KEY, "metric")
          viewModel._weatherResponse.value = response.body()
        }
      }
    } else
      checkLocationPermission(requireActivity())
  }

}
