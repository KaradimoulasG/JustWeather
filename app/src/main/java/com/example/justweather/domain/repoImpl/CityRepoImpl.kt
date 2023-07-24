package com.example.justweather.domain.repoImpl

import com.example.justweather.data.OpenWeatherApi
import com.example.justweather.data.repositories.ICityRepo

class CityRepoImpl(
    private val api: OpenWeatherApi,
) : ICityRepo {

    override suspend fun getCityInfo() =
        api.getCityInfo()
}
