package com.example.justweather.data.dto.airPollution

import com.example.justweather.data.dto.cityInfo.CoordinationDto

data class AirPollutionDto(
    val coord: CoordinationDto,
    val list: List<AirPollutionDetailsDto>
)