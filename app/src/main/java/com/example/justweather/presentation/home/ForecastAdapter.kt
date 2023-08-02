package com.example.justweather.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.justweather.R
import com.example.justweather.common.extensions.showWeatherIcon
import com.example.justweather.common.extensions.transformToDateIndented
import com.example.justweather.domain.model.ForecastInfo
import kotlin.math.roundToInt

class ForecastAdapter : ListAdapter<ForecastInfo, ForecastAdapter.ForecastViewHolder>(DiffCallBack()) {

    private class DiffCallBack : DiffUtil.ItemCallback<ForecastInfo>() {
        override fun areItemsTheSame(oldItem: ForecastInfo, newItem: ForecastInfo) = oldItem.timestamp == newItem.timestamp
        override fun areContentsTheSame(oldItem: ForecastInfo, newItem: ForecastInfo) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ForecastViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.forecast_adapter_item, parent, false))

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val result = currentList[position]
        val context = holder.itemView.context
        val dateTimeFormatted = result.timestamp.transformToDateIndented()
        val iconToShow = result.weatherDetails[0].icon.showWeatherIcon()

        holder.dateTime.text = context.getString(R.string.date_time_title, result.timestampText)
        Glide.with(context).load(iconToShow).into(holder.weatherIcon)
        holder.temperature.text = context.getString(R.string.local_temp, result.mainDetails.temp.roundToInt())
    }

    fun addAll(items: List<ForecastInfo>) {
        submitList(items)
    }

    inner class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var dateTime: TextView = itemView.findViewById(R.id.date_time_tv)
        var weatherIcon: ImageView = itemView.findViewById(R.id.weather_iv)
        var temperature: TextView = itemView.findViewById(R.id.temperature_tv)
    }
}
