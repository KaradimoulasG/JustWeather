package com.example.core_domain.domain.data

import com.example.core_domain.domain.common.Constants
import com.example.core_domain.domain.data.dto.airPollution.AirPollutionDto
import com.example.core_domain.domain.data.dto.cityInfo.CityInfoDto
import com.example.core_domain.domain.data.dto.forecast.ForecastDto
import com.example.core_domain.domain.data.networkResultHandling.NetworkResult
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {

    @GET("/data/2.5/weather?appid=${Constants.API_KEY}&units=metric")
    suspend fun getCityInfo(
        @Query("q") cityName: String,
    ): NetworkResult<CityInfoDto>

    @GET("/data/2.5/forecast?&appid=${Constants.API_KEY}&units=metric")
    suspend fun getFiveDayForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
    ): NetworkResult<ForecastDto>

    @GET("/data/2.5/air_pollution?&appid=${Constants.API_KEY}")
    suspend fun getAirPollution(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
    ): NetworkResult<AirPollutionDto>
}
