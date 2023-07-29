package com.example.justweather.data.persistence

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.justweather.data.dto.cityInfo.CloudsDto
import com.example.justweather.data.dto.cityInfo.CoordinationDto
import com.example.justweather.data.dto.cityInfo.MainDto
import com.example.justweather.data.dto.cityInfo.SysDto
import com.example.justweather.data.dto.cityInfo.WeatherDto
import com.example.justweather.data.dto.cityInfo.WindDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class WeatherTypeConverters {

    private val gson = Gson()

    @TypeConverter
    fun fromClouds(cloudsDto: CloudsDto) = gson.toJson(cloudsDto)

    @TypeConverter
    fun toClouds(json: String) = gson.fromJson(json, CloudsDto::class.java)

    @TypeConverter
    fun fromCoordination(coordination: CoordinationDto) = gson.toJson(coordination)

    @TypeConverter
    fun toCoordination(json: String) = gson.fromJson(json, CoordinationDto::class.java)

    @TypeConverter
    fun fromMainDto(main: MainDto) = gson.toJson(main)

    @TypeConverter
    fun toMainDto(json: String) = gson.fromJson(json, MainDto::class.java)

    @TypeConverter
    fun fromSysDto(sys: SysDto) = gson.toJson(sys)

    @TypeConverter
    fun toSysDto(json: String) = gson.fromJson(json, SysDto::class.java)

    @TypeConverter
    fun fromWeather(weather: List<WeatherDto>) = gson.toJson(weather)

    @TypeConverter
    fun toWeather(json: String): List<WeatherDto> {
        val type = object : TypeToken<List<WeatherDto>>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun fromWindDto(wind: WindDto) = gson.toJson(wind)

    @TypeConverter
    fun toWindDto(json: String) = gson.fromJson(json, WindDto::class.java)
}
