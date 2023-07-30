package com.example.justweather.data.dto.airPollution

data class AirPollutionDetailsDto(
    val components: ComponentsDto,
    val dt: Int,
    val main: AirPollutionMainDto,
)
