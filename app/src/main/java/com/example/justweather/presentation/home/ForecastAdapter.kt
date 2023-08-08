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
import com.example.justweather.common.extensions.toDateFormatted
import com.example.justweather.domain.model.ForecastInfo
import kotlin.math.roundToInt

class ForecastAdapter : ListAdapter<ForecastInfo, ForecastAdapter.ForecastViewHolder>(
    DiffCallBack(),
) {

    private class DiffCallBack : DiffUtil.ItemCallback<ForecastInfo>() {
        override fun areItemsTheSame(oldItem: ForecastInfo, newItem: ForecastInfo) = oldItem.timestamp == newItem.timestamp
        override fun areContentsTheSame(oldItem: ForecastInfo, newItem: ForecastInfo) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ForecastViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.forecast_adapter_item, parent, false))
    override fun onBindViewHolder(holder: ForecastAdapter.ForecastViewHolder, position: Int) = holder.bind(holder, position)
    fun addAll(items: List<ForecastInfo>) = submitList(items)

    inner class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var dateTime: TextView = itemView.findViewById(R.id.date_time_tv)
        private var weatherIcon: ImageView = itemView.findViewById(R.id.weather_iv)
        private var temperature: TextView = itemView.findViewById(R.id.temperature_tv)

        fun bind(holder: ForecastViewHolder, pos: Int) {
            val result = currentList[pos]
            val context = holder.itemView.context
            val dateTimeFormatted = toDateFormatted(result.timestamp)
            val iconToShow = result.weatherDetails[0].icon.showWeatherIcon()

            holder.dateTime.text = dateTimeFormatted
            Glide.with(context).load(iconToShow).into(holder.weatherIcon)
            holder.temperature.text = context.getString(R.string.local_temp, result.mainDetails.temp.roundToInt())
        }
    }
}
