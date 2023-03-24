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
import com.example.weatherapp.viewmodel.searchviewmodel
import kotlinx.coroutines.NonDisposableHandle.parent


class SearchFragment : Fragment() {
  private lateinit var viewModel: searchviewmodel
  private lateinit var binding: FragmentSearchBinding


  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

     binding = FragmentSearchBinding.inflate(inflater)
    val view = inflater.inflate(R.layout.fragment_search,container,false)
    viewModel = ViewModelProvider(this).get(searchviewmodel::class.java)
    /////////
viewModel.currentLocation.observe(viewLifecycleOwner , Observer {
binding.time.text= it.toString()
})
    return view
  }


}
