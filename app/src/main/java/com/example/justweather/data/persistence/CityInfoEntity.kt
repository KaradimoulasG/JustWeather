package com.example.justweather.data.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.justweather.data.dto.cityInfo.CloudsDto
import com.example.justweather.data.dto.cityInfo.CoordinationDto
import com.example.justweather.data.dto.cityInfo.MainDto
import com.example.justweather.data.dto.cityInfo.SysDto
import com.example.justweather.data.dto.cityInfo.WeatherDto
import com.example.justweather.data.dto.cityInfo.WindDto

@Entity
data class CityInfoEntity(
    @PrimaryKey(autoGenerate = false)
    val name: String,
    val base: String,
    val clouds: CloudsDto,
    val code: Int,
    val coordination: CoordinationDto,
    val timestamp: Int,
    val id: Int,
    val main: MainDto,
    val system: SysDto,
    val visibility: Int,
    val weather: List<WeatherDto>,
    val wind: WindDto,
)
