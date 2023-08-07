package com.example.justweather.common.extensions

import android.annotation.SuppressLint
import android.view.View
import com.example.justweather.R
import com.example.justweather.common.WeatherDescription
import java.text.SimpleDateFormat

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

@SuppressLint("SimpleDateFormat")
fun Int.toDate(): String {
    val simpleDate = SimpleDateFormat("E, dd/M/yyyy hh:mm:ss")
    return simpleDate.format(this)
}

@SuppressLint("SimpleDateFormat")
fun toDateFormatted(timestamp: Int): String {
//    val simpleDate = SimpleDateFormat("E,\nhh:mm")
//    return simpleDate.format(this)

//    if (this == null) return ""
//    // Get instance of calendar
//    val calendar = Calendar.getInstance(Locale.getDefault())
//    // get current date from ts
//    calendar.timeInMillis = this.toLong()
//    // return formatted date
//    return android.text.format.DateFormat.format("E, hh:mm", calendar).toString()

//    val sdf = SimpleDateFormat("dd/MM/yy hh:mm a")
//    val netDate = Date(this.toLong())
//    return sdf.format(netDate)

    val day = SimpleDateFormat("EEEE").format(timestamp * 1000)
    val time = SimpleDateFormat("HH:mm").format(timestamp * 1000)
    return "$day\n$time"
}

// @SuppressLint("SimpleDateFormat")
// fun Int.toDateFormatted(): String = SimpleDateFormat("E,\nhh:mm").format(this)

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
