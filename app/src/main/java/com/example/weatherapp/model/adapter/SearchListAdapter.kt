package com.example.weatherapp.model.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.weatherapp.R
import com.example.weatherapp.databinding.SuggestionItemLayoutBinding
import com.example.weatherapp.model.WeatherApiResponse
import com.example.weatherapp.model.WeatherDataItem
import com.example.weatherapp.ui.CurrentLocationFragmentDirections
import com.example.weatherapp.ui.SearchFragmentDirections
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent

class SearchListAdapter(
  val res: List<WeatherDataItem>,
  val context: Context,
  val firebaseAnalytics: FirebaseAnalytics
) :
  RecyclerView.Adapter<SearchListAdapter.ResultViewHolder>() {
  class ResultViewHolder(itemBinding: SuggestionItemLayoutBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {
    var city: TextView
    var temprature: TextView
    var imageView: ImageView
    var iconUrl: String

    init {
      city = itemBinding.cityName
      temprature = itemBinding.temperature
      imageView = itemBinding.icon
      iconUrl = itemBinding.root.context.getString(R.string.baseIconUrl)
    }

  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
    val binding =
      SuggestionItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ResultViewHolder(binding)
  }

  override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
    val resultItem = res[position]

    if (resultItem.name != null) {
      holder.city.text = resultItem.name + "," + resultItem.country.capitalize()
    } else
      holder.city.text = resultItem.name + "," + resultItem.country.capitalize()
    holder.temprature.text = resultItem.temperature.toString()
    val currentIcon = resultItem.icon
    val iconUrl = holder.iconUrl + currentIcon + "@2x.png"
    Glide.with(context)
      .load(iconUrl)
      .apply(RequestOptions().override(200, 200))
      .into(holder.imageView)


    holder.itemView.setOnClickListener {
      val directions =
        SearchFragmentDirections.actionSearchFragmentToCurrentLocationFragment(resultItem)
      Navigation.findNavController(it).navigate(directions)

      firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {
      param("Item_name" , resultItem.name)
        param("Item_country" , resultItem.country)

      }
    }
  }


  override fun getItemCount(): Int {
    return res.size
  }
}
