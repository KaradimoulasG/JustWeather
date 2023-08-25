package com.example.core_domain.domain.model

import com.example.core_domain.domain.data.dto.cityInfo.CloudsDto
import com.example.core_domain.domain.data.dto.cityInfo.WeatherDto
import com.example.core_domain.domain.data.dto.forecast.ForecastMainDto
import com.example.core_domain.domain.data.dto.forecast.ForecastSysDto
import com.example.core_domain.domain.data.dto.forecast.ForecastWindDto
import com.example.core_domain.domain.data.dto.forecast.InfoDto
import com.example.core_domain.domain.data.dto.forecast.RainDto

data class ForecastInfo(
    val clouds: CloudsDto,
    val timestamp: Int,
    val timestampText: String,
    val mainDetails: ForecastMainDto,
    val percentageChanceOfRain: Double? = 0.0,
    val rainVolumeLastThreeHours: RainDto? = null,
    val partOfDay: ForecastSysDto,
    val visibility: Int,
    val weatherDetails: List<WeatherDto>,
    val windDetails: ForecastWindDto,
)

fun List<InfoDto>.toForecastInfo() =
    map { infoDto ->
        ForecastInfo(
            clouds = infoDto.clouds!!,
            timestamp = infoDto.dt,
            timestampText = infoDto.dt_txt,
            mainDetails = infoDto.main,
            percentageChanceOfRain = infoDto.pop,
            rainVolumeLastThreeHours = infoDto.rain,
            partOfDay = infoDto.sys,
            visibility = infoDto.visibility,
            weatherDetails = infoDto.weather,
            windDetails = infoDto.wind,
        )
    }
