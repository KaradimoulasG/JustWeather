package com.example.justweather.data.dto.forecast

import com.example.justweather.data.dto.cityInfo.CoordinationDto
import com.squareup.moshi.Json

data class ForecastCityDto(
    @field:Json(name = "coord") val coord: CoordinationDto,
    @field:Json(name = "country") val country: String,
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "population") val population: Int,
    @field:Json(name = "sunrise") val sunrise: Int,
    @field:Json(name = "sunset") val sunset: Int,
    @field:Json(name = "timezone") val timezone: Int
)