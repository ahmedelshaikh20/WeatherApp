package com.example.weatherapp.ui

import android.annotation.SuppressLint
import android.app.SearchManager
import android.database.Cursor
import android.database.MatrixCursor
import android.os.Bundle
import android.provider.BaseColumns
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.cursoradapter.widget.CursorAdapter
import androidx.cursoradapter.widget.SimpleCursorAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.weatherapp.R
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
private lateinit var cursorAdapter: CursorAdapter
private lateinit var searchView : SearchView
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val from = arrayOf(SearchManager.SUGGEST_COLUMN_TEXT_1)
    val to = intArrayOf(R.id.searchItemID)
    cursorAdapter = SimpleCursorAdapter(context, R.layout.suggestion_item_layout, null, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER)


    fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    binding = FragmentSearchBinding.inflate(inflater)
    searchView = binding.searchView
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
viewModel.suggestionList.observe(viewLifecycleOwner , Observer {
  val cursor =
    MatrixCursor(arrayOf(BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1))
  it.forEachIndexed { index, suggestion ->
    cursor.addRow(arrayOf(index, suggestion)) }
  cursorAdapter.changeCursor(cursor)
  searchView.suggestionsAdapter = cursorAdapter

})
searchView.setOnSuggestionListener( object : SearchView.OnSuggestionListener{
  override fun onSuggestionSelect(position: Int): Boolean {
    TODO("Not yet implemented")
  }

  @SuppressLint("Range")
  override fun onSuggestionClick(position: Int): Boolean {
    val cursor = searchView.suggestionsAdapter.getItem(position) as Cursor
    val selection = cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1))
    searchView.setQuery(selection, false)
    Toast.makeText(requireContext() , selection , Toast.LENGTH_SHORT).show()
    return true
  }

})

    viewModel.currentIcon.observe(viewLifecycleOwner , Observer {
      val iconUrl = getString(R.string.baseIconUrl)+it+"@2x.png"

      Glide.with(requireContext())
        .load(iconUrl)
        .apply( RequestOptions().override(600, 600))
        .into(binding.weatherIcon)
        })
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
    getTextinSearchView()
    return binding.root

  }

  private fun getTextinSearchView() {
    binding.searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
      override fun onQueryTextSubmit(query: String?): Boolean {
        TODO("Not yet implemented")
      }

      override fun onQueryTextChange(newText: String?): Boolean {

        if (newText != null) {
          if(newText.length > 2){

            viewModel.searchTextChanged(newText)




          }
      }

        return true

      }

    } )
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
