package com.example.justweather.data.repositories

import com.example.justweather.data.dto.CityInfoDto
import com.example.justweather.data.networkResultHandling.NetworkResult

interface ICityRepo {

    suspend fun getCityInfo(): NetworkResult<CityInfoDto>
}
