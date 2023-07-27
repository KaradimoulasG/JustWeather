package com.example.justweather.data.dto.forecast

import com.example.justweather.data.dto.cityInfo.CloudsDto
import com.example.justweather.data.dto.cityInfo.WeatherDto
import com.squareup.moshi.Json

data class InfoDto(
    @field:Json(name = "clouds") val clouds: CloudsDto,
    @field:Json(name = "dt") val dt: Int,
    @field:Json(name = "dt_txt") val dt_txt: String,
    @field:Json(name = "main") val main: ForecastMainDto,
    @field:Json(name = "pop") val pop: Double,
    @field:Json(name = "rain") val rain: RainDto,
    @field:Json(name = "sys") val sys: ForecastSysDto,
    @field:Json(name = "visibility") val visibility: Int,
    @field:Json(name = "weather") val weather: List<WeatherDto>,
    @field:Json(name = "wind") val wind: ForecastWindDto
)