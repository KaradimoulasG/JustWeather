package com.example.justweather.presentation.favorites

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
import com.example.core_domain.R as R2
import com.example.core_domain.domain.common.extensions.showWeatherIcon
import com.example.core_domain.domain.model.CityInfo

class FavoritesAdapter(
    private val layoutInflater: LayoutInflater,
    private val onCityClick: (String) -> Unit,
    private val onCityLongClick: (String) -> Unit
) : ListAdapter<CityInfo, FavoritesAdapter.FavoritesViewHolder>(DiffCallBack()) {

    private class DiffCallBack : DiffUtil.ItemCallback<CityInfo>() {
        override fun areItemsTheSame(oldItem: CityInfo, newItem: CityInfo) = oldItem.timestamp == newItem.timestamp
        override fun areContentsTheSame(oldItem: CityInfo, newItem: CityInfo) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FavoritesViewHolder(LayoutInflater.from(parent.context).inflate(R2.layout.favorites_adapter_item, parent, false))
    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) = holder.bind(holder, position)
    fun addAll(cities: List<CityInfo>) = submitList(cities)

    inner class FavoritesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var weatherIcon: ImageView = itemView.findViewById(R2.id.weather_iv)
        private var cityName: TextView = itemView.findViewById(R2.id.city_name_tv)

        fun bind(holder: FavoritesViewHolder, pos: Int) {
            val result = currentList[pos]
            val context = holder.itemView.context
            val iconToShow = result.weatherDetails[0].icon.showWeatherIcon()

            Glide.with(context).load(iconToShow).into(holder.weatherIcon)
            holder.cityName.text = result.cityName

            holder.itemView.setOnClickListener {
                onCityClick.invoke(result.cityName)
            }

            holder.itemView.setOnLongClickListener {
                onCityLongClick.invoke(result.cityName)
                true
            }
        }
    }
}
