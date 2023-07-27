package com.example.justweather.domain.model

import com.example.justweather.data.dto.forecast.ForecastCityDto
import com.example.justweather.data.dto.forecast.ForecastDto
import com.example.justweather.data.dto.forecast.InfoDto

data class Forecast(
    val cityName: ForecastCityDto,
    val cnt: Int,
    val code: String,
    val list: List<InfoDto>,
    val message: Int,
)

fun ForecastDto.toForecast(): Forecast {
    return Forecast(
        cityName = city,
        cnt = cnt,
        code = cod,
        list = list,
        message = message,
    )
}
