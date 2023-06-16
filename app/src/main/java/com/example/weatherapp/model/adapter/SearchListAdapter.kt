package com.example.weatherapp.model.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.model.geocodingmodel.Result

class SearchListAdapter(val res: List<Result>) :
  RecyclerView.Adapter<SearchListAdapter.ResultViewHolder>() {
  class ResultViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var city: TextView
    var temprature: TextView

    init {
      city = view.findViewById(R.id.cityName)
      temprature = view.findViewById(R.id.temperature)

    }

  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
    val view =
      LayoutInflater.from(parent.context).inflate(R.layout.suggestion_item_layout, parent, false)
    return ResultViewHolder(view)
  }

  override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
val resultItem = res.get(position)
    if(resultItem.city!=null){
    holder.city.text = resultItem.city+","+resultItem.country_code.capitalize()}
    else
      holder.city.text = resultItem.state+","+resultItem.country_code.capitalize()
    holder.temprature.text="20"

  }

  override fun getItemCount(): Int {
    return res.size
  }
}
