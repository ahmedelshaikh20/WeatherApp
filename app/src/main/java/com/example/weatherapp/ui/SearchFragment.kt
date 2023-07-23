package com.example.weatherapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.FragmentSearchBinding
import com.example.weatherapp.model.adapter.SearchListAdapter
import com.example.weatherapp.viewmodel.searchListviewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

  lateinit var binding: FragmentSearchBinding
  lateinit var recyclerView: RecyclerView
  val  viewModel : searchListviewmodel by viewModels()
  lateinit var searchListAdapter: SearchListAdapter

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    binding = FragmentSearchBinding.inflate(inflater)
    recyclerView = binding.recyclerView
    viewModel.suggestionList.observe(viewLifecycleOwner, Observer {
      searchListAdapter = SearchListAdapter(it, requireContext())
      val layoutManager = LinearLayoutManager(requireContext())
      recyclerView.layoutManager = layoutManager
      recyclerView.adapter = searchListAdapter
      searchListAdapter.notifyDataSetChanged()
    })
    binding.searhView.requestFocus()
    getTextinSearchView()
    return binding.root
  }

  private fun getTextinSearchView() {
    binding.searhView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
      override fun onQueryTextSubmit(query: String?): Boolean {
        TODO("Not yet implemented")
      }

      override fun onQueryTextChange(newText: String?): Boolean {

        if (newText != null) {
          if (newText.length > 2) {
            viewModel.searchTextChanged(newText)
          }
        }

        return true

      }

    })
  }

}
