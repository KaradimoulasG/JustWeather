package com.example.justweather.data.dto.cityInfo

import com.squareup.moshi.Json

data class CloudsDto(
    @field:Json(name = "all") val all: Int
)