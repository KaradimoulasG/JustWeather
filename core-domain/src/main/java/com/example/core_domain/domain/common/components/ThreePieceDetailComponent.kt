package com.example.core_domain.domain.common.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.example.core_domain.R
import com.example.core_domain.databinding.ThreePieceDetailComponentBinding

class ThreePieceDetailComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0,
) : RelativeLayout(context, attrs, defStyleRes) {

    private val binding = ThreePieceDetailComponentBinding.inflate(LayoutInflater.from(context), this)

    fun setUpComponent(windSpeed: Int, humidity: Int, pressure: Int) {
        binding.apply {
            windTv.text = context.getString(R.string.wind_speed_details, windSpeed)
            humidityTv.text = context.getString(R.string.humidity_details, humidity) + "%"
            pressureTv.text = context.getString(R.string.pressure_details, pressure)
        }
    }
}