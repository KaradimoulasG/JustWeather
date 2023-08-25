package com.example.core_domain.domain.data.dto.forecast

import com.squareup.moshi.Json

data class ForecastDto(
    @field:Json(name = "city") val city: ForecastCityDto,
    @field:Json(name = "cnt") val cnt: Int,
    @field:Json(name = "cod") val cod: String,
    @field:Json(name = "list") val list: List<InfoDto>,
    @field:Json(name = "message") val message: Int
)