package com.example.weatherapp.model.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.weatherapp.R
import com.example.weatherapp.databinding.SuggestionItemLayoutBinding
import com.example.weatherapp.model.WeatherApiResponse

class SearchListAdapter(val res: List<WeatherApiResponse>, val context: Context) :
  RecyclerView.Adapter<SearchListAdapter.ResultViewHolder>() {
  class ResultViewHolder(private val itemBinding: SuggestionItemLayoutBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {
    var city: TextView
    var temprature: TextView
    var imageView: ImageView
var iconUrl :String

    init {
      city = itemBinding.cityName
      temprature = itemBinding.temperature
      imageView = itemBinding.icon
      iconUrl = itemBinding.root.context.getString(R.string.baseIconUrl)
    }

  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
    val binding = SuggestionItemLayoutBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
    return ResultViewHolder(binding)
  }

  override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
    val resultItem = res.get(position)

    if (resultItem.name != null) {
      holder.city.text = resultItem.name + "," + resultItem.sys.country
    } else
      holder.city.text = resultItem.name + "," + resultItem.sys.country.capitalize()
    holder.temprature.text = resultItem.main.temperature.toString()
    val currentIcon = resultItem.weather?.get(0)?.icon.toString()
    val iconUrl = holder.iconUrl + currentIcon + "@2x.png"
    Glide.with(context)
      .load(iconUrl)
      .apply(RequestOptions().override(200, 200))
      .into(holder.imageView)
  }


  override fun getItemCount(): Int {
    return res.size
  }
}
