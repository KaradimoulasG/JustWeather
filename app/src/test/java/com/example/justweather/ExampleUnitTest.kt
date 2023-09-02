package com.example.justweather

import com.example.core_domain.domain.data.OpenWeatherApi
import com.example.core_domain.domain.data.persistence.CityDao
import com.example.core_domain.domain.repoImpl.CityRepoImpl
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    private var weatherService: CityRepoImpl? = null
    private val weatherApi: OpenWeatherApi? = null
    private val cityDao: CityDao? = null

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        weatherService = CityRepoImpl(weatherApi!!, cityDao!!)
    }

    @Test
    suspend fun testGetWeatherForCity_Success() {
        // Mock the behavior of the WeatherApi when fetching weather for "Thessaloniki"
        `when`(weatherApi?.getCityInfo("Thessaloniki")).thenReturn(null)
        val result = weatherService?.getCityInfo("Thessaloniki")

        // Verify that the result matches the expected value
        assertEquals(null, result)
    }

    @Test
    suspend fun testGetWeatherForCity_Error() {
        // Mock the behavior of the WeatherApi when encountering an error
        `when`(weatherApi?.getCityInfo("Invalid City")).thenThrow(RuntimeException("API Error"))
        val result = weatherService?.getCityInfo("Invalid City")

        // Verify that the result indicates an error
        assertEquals("Error: Unable to fetch weather data", result)
    }
}