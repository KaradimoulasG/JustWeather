package com.example.justweather.domain.model

import com.example.justweather.data.dto.CityInfoDto
import com.example.justweather.data.dto.CloudsDto
import com.example.justweather.data.dto.CoordinationDto
import com.example.justweather.data.dto.MainDto
import com.example.justweather.data.dto.SysDto
import com.example.justweather.data.dto.WeatherDto
import com.example.justweather.data.dto.WindDto

data class CityInfo(
    val base: String,
    val clouds: CloudsDto,
    val code: Int,
    val coordination: CoordinationDto,
    val timestamp: Int,
    val id: Int,
    val main: MainDto,
    val cityName: String,
    val system: SysDto,
    val visibility: Int,
    val weather: List<WeatherDto>,
    val wind: WindDto,
)

fun CityInfoDto.toCityInfo() =
    CityInfo(
        base = base,
        clouds = cloudsDto,
        code = cod,
        coordination = coord,
        timestamp = dt,
        id = id,
        main = main,
        cityName = name,
        system = sys,
        visibility = visibility,
        weather = weather,
        wind = windDto,
    )
