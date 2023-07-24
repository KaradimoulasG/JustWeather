package com.example.justweather.data.dto

import com.squareup.moshi.Json

data class CoordinationDto(
    @field:Json(name = "lat") val lat: Double,
    @field:Json(name = "lon") val lon: Double
)