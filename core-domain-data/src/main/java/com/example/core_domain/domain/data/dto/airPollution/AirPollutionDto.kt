package com.example.core_domain.domain.data.dto.airPollution

import com.example.core_domain.domain.data.dto.cityInfo.CoordinationDto


data class AirPollutionDto(
    val coord: CoordinationDto,
    val list: List<com.example.core_domain.domain.data.dto.airPollution.AirPollutionDetailsDto>
)