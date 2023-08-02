package com.example.justweather.domain.model

import com.example.justweather.data.dto.forecast.ForecastCityDto
import com.example.justweather.data.dto.forecast.ForecastDto
import com.example.justweather.data.dto.forecast.InfoDto

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
