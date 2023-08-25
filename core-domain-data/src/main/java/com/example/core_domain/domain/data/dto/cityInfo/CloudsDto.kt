package com.example.core_domain.domain.data.dto.cityInfo


import com.squareup.moshi.Json

data class CloudsDto(
    @field:Json(name = "all") val all: Int
)