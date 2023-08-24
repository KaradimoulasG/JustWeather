package com.example.core_domain.domain.data.dto.airPollution

data class AirPollutionDetailsDto(
    val components: com.example.core_domain.domain.data.dto.airPollution.ComponentsDto,
    val dt: Int,
    val main: AirPollutionMainDto,
)
