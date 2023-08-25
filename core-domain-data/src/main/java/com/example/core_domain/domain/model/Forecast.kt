package com.example.core_domain.domain.model

import com.example.core_domain.domain.data.dto.forecast.ForecastCityDto
import com.example.core_domain.domain.data.dto.forecast.ForecastDto
import com.example.core_domain.domain.data.dto.forecast.InfoDto

data class Forecast(
    val cityName: ForecastCityDto,
    val numberOfResults: Int,
    val code: String,
    val list: List<InfoDto>,
    val message: Int,
)

fun ForecastDto.toForecast() =
    Forecast(
        cityName = city,
        numberOfResults = cnt,
        code = cod,
        list = list,
        message = message,
    )
