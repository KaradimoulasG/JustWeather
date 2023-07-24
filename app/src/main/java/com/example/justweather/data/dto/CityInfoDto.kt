package com.example.justweather.data.dto

import com.squareup.moshi.Json

data class CityInfoDto(
    @field:Json(name = "base") val base: String,
    @field:Json(name = "clouds") val cloudsDto: CloudsDto,
    @field:Json(name = "cod") val cod: Int,
    @field:Json(name = "coord") val coord: CoordinationDto,
    @field:Json(name = "dt") val dt: Int,
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "main") val main: MainDto,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "sys") val sys: SysDto,
    @field:Json(name = "visibility") val visibility: Int,
    @field:Json(name = "weather") val weather: List<WeatherDto>,
    @field:Json(name = "wind") val windDto: WindDto
)