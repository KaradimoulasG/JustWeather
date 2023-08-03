package com.example.justweather.domain.model

import com.example.justweather.data.dto.airPollution.AirPollutionDetailsDto
import com.example.justweather.data.dto.airPollution.AirPollutionDto
import com.example.justweather.data.dto.cityInfo.CoordinationDto

data class AirPollution(
    val coordination: CoordinationDto,
    val detailsList: List<AirPollutionDetailsDto>
)

fun AirPollutionDto.toAirPollution() =
    AirPollution(
        coordination = coord,
        detailsList = list,
    )