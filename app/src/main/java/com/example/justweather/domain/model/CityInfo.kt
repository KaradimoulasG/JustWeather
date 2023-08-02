package com.example.justweather.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.justweather.data.dto.cityInfo.CityInfoDto
import com.example.justweather.data.dto.cityInfo.CloudsDto
import com.example.justweather.data.dto.cityInfo.CoordinationDto
import com.example.justweather.data.dto.cityInfo.MainDto
import com.example.justweather.data.dto.cityInfo.SysDto
import com.example.justweather.data.dto.cityInfo.WeatherDto
import com.example.justweather.data.dto.cityInfo.WindDto

@Entity
data class CityInfo(
    val base: String,
    val clouds: CloudsDto? = null,
    val code: Int,
    val coordination: CoordinationDto,
    val timestamp: Int,
    val id: Int,
    val mainDetails: MainDto,
    @PrimaryKey(autoGenerate = false)
    val cityName: String,
    val system: SysDto,
    val visibility: Int,
    val weatherDetails: List<WeatherDto>,
    val windDetails: WindDto,
)

fun CityInfoDto.toCityInfo() =
    CityInfo(
        base = base,
        clouds = cloudsDto,
        code = cod,
        coordination = coord,
        timestamp = dt,
        id = id,
        mainDetails = main,
        cityName = name,
        system = sys,
        visibility = visibility,
        weatherDetails = weather,
        windDetails = wind,
    )
