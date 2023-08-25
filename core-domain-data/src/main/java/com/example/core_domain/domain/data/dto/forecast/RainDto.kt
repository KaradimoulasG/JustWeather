package com.example.core_domain.domain.data.dto.forecast

import com.squareup.moshi.Json

data class RainDto(
    @field:Json(name = "3h") val threeHours: Double? = 0.0,
)
