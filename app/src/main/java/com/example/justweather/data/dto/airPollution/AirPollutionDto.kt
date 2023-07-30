package com.example.justweather.data.dto.airPollution

data class AirPollutionDto(
    val coord: List<Int>,
    val list: List<AirPollutionDetailsDto>
)