package com.example.core_domain.domain.data.dto.cityInfo

import com.squareup.moshi.Json

data class CoordinationDto(
    @field:Json(name = "lat") val lat: Double,
    @field:Json(name = "lon") val lon: Double
)