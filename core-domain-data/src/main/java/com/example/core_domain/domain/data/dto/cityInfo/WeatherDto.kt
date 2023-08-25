package com.example.core_domain.domain.data.dto.cityInfo

import com.squareup.moshi.Json

data class WeatherDto(
    @field:Json(name = "description") val description: String,
    @field:Json(name = "icon") val icon: String,
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "main") val main: String
)