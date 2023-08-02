package com.example.justweather.common.extensions

import android.annotation.SuppressLint
import android.view.View
import com.example.justweather.R
import com.example.justweather.common.WeatherDescription
import java.text.SimpleDateFormat
import java.util.Date

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

@SuppressLint("SimpleDateFormat")
fun Int.transformToDate(): String {
    val simpleDate = SimpleDateFormat("E, dd/M/yyyy hh:mm:ss")
    return simpleDate.format(Date())
}

@SuppressLint("SimpleDateFormat")
fun Int.transformToDateIndented(): String {
    val simpleDate = SimpleDateFormat("E\n, dd/M/yyyy\n hh:mm")
    return simpleDate.format(Date())
}

fun String.showWeatherIcon() =
    when (this) {
        WeatherDescription.ClearSkyDay.iconId -> R.drawable.ic_clear_sky_day
        WeatherDescription.ClearSkyNight.iconId -> R.drawable.ic_clear_sky_night
        WeatherDescription.FewCloudsDay.iconId -> R.drawable.ic_cloudy_day
        WeatherDescription.FewCloudsNight.iconId -> R.drawable.ic_cloudy_night
        WeatherDescription.ScatteredCloudsDay.iconId -> R.drawable.ic_cloudy_day
        WeatherDescription.ScatteredCloudsNight.iconId -> R.drawable.ic_cloudy_night
        WeatherDescription.BrokenCloudsDay.iconId -> R.drawable.ic_cloudy_day
        WeatherDescription.BrokenCloudsNight.iconId -> R.drawable.ic_cloudy_night
        WeatherDescription.ShowerRainDay.iconId -> R.drawable.ic_rain_day
        WeatherDescription.ShowerRainNight.iconId -> R.drawable.ic_rain_night
        WeatherDescription.RainDay.iconId -> R.drawable.ic_rain_day
        WeatherDescription.RainNight.iconId -> R.drawable.ic_rain_night
        WeatherDescription.ThunderstormDay.iconId -> R.drawable.ic_thunderstorm
        WeatherDescription.ThunderstormNight.iconId -> R.drawable.ic_thunderstorm
        WeatherDescription.SnowDay.iconId -> R.drawable.ic_snow_day
        WeatherDescription.SnowNight.iconId -> R.drawable.ic_snow_night
        WeatherDescription.MistDay.iconId -> R.drawable.ic_mist
        WeatherDescription.MistNight.iconId -> R.drawable.ic_mist
        else -> R.drawable.ic_mist
    }
