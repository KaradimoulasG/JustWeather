package com.example.core_domain.domain.data.dto.cityInfo

import com.squareup.moshi.Json

data class MainDto(
    @field:Json(name = "humidity") val humidity: Int,
    @field:Json(name = "pressure") val pressure: Int,
    @field:Json(name = "feels_like") val feels_like: Double,
    @field:Json(name = "temp") val temp: Double,
    @field:Json(name = "temp_max") val temp_max: Double,
    @field:Json(name = "temp_min") val temp_min: Double
)