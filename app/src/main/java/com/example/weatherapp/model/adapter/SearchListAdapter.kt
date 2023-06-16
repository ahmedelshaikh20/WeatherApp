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
import com.example.weatherapp.model.WeatherApiResponse

class SearchListAdapter(val res: List<WeatherApiResponse> , val context: Context) :
  RecyclerView.Adapter<SearchListAdapter.ResultViewHolder>() {
  class ResultViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var city: TextView
    var temprature: TextView
    var imageView: ImageView
    val iconUrl :String


    init {
      city = view.findViewById(R.id.cityName)
      temprature = view.findViewById(R.id.temperature)
      imageView = view.findViewById(R.id.icon)
      iconUrl = view.context.getString(R.string.baseIconUrl)
    }

  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
    val view =
      LayoutInflater.from(parent.context).inflate(R.layout.suggestion_item_layout, parent, false)
    return ResultViewHolder(view)
  }

  override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
    val resultItem = res.get(position)
    if (resultItem.name != null) {
      holder.city.text = resultItem.name + "," + resultItem.sys.country
    } else
      holder.city.text = resultItem.name + "," + resultItem.sys.country.capitalize()
    holder.temprature.text = resultItem.main.temperature.toString()
    val currentIcon = resultItem.weather?.get(0)?.icon.toString()
val iconUrl = holder.iconUrl+currentIcon+"@2x.png"
    Glide.with(context)
      .load(iconUrl)
      .apply( RequestOptions().override(200, 200))
      .into(holder.imageView)
  }


  override fun getItemCount(): Int {
    return res.size
  }
}
