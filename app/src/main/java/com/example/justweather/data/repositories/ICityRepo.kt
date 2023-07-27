package com.example.justweather.data.repositories

import com.example.justweather.data.dto.cityInfo.CityInfoDto
import com.example.justweather.data.dto.forecast.ForecastDto
import com.example.justweather.data.networkResultHandling.NetworkResult

interface ICityRepo {

    suspend fun getCityInfo(): NetworkResult<CityInfoDto>
    suspend fun getFiveDayForecast(latitude: Double, longitude: Double): NetworkResult<ForecastDto>
}
