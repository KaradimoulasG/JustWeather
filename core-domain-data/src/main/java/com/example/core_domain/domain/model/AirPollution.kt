package com.example.core_domain.domain.model

import com.example.core_domain.domain.data.dto.airPollution.AirPollutionDetailsDto
import com.example.core_domain.domain.data.dto.airPollution.AirPollutionDto
import com.example.core_domain.domain.data.dto.cityInfo.CoordinationDto

data class AirPollution(
    val coordination: CoordinationDto,
    val detailsList: List<AirPollutionDetailsDto>
)

fun AirPollutionDto.toAirPollution() =
    AirPollution(
        coordination = coord,
        detailsList = list,
    )