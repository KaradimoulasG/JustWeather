package com.example.justweather.data

import com.example.justweather.common.Constants
import com.example.justweather.data.dto.CityInfoDto
import com.example.justweather.data.networkResultHandling.NetworkResult
import retrofit2.http.GET

interface OpenWeatherApi {

    @GET("/data/2.5/weather?q=Athens&appid=${Constants.API_KEY}&units=metric")
    suspend fun getCityInfo(): NetworkResult<CityInfoDto>
}