package com.example.justweather.data.dto.forecast

import com.squareup.moshi.Json

data class RainDto(
    @field:Json(name = "3h") val threeHours: Double? = 0.0,
)
