package com.example.justweather.domain.repoImpl

import com.example.justweather.data.OpenWeatherApi
import com.example.justweather.data.dto.forecast.ForecastDto
import com.example.justweather.data.networkResultHandling.NetworkResult
import com.example.justweather.data.persistence.CityDao
import com.example.justweather.data.repositories.ICityRepo

class CityRepoImpl(
    private val api: OpenWeatherApi,
    private val cityDao: CityDao
) : ICityRepo {

    override suspend fun getCityInfo() =
        api.getCityInfo()

    override suspend fun getFiveDayForecast(latitude: Double, longitude: Double) =
        api.getFiveDayForecast(latitude, longitude)
}
