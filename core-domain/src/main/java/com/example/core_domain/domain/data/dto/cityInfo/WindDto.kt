package com.example.core_domain.domain.data.dto.cityInfo

import com.squareup.moshi.Json

data class WindDto(
    @field:Json(name = "deg") val deg: Int,
    @field:Json(name = "speed") val speed: Double,
)
