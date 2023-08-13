package com.example.justweather.common

object Constants {

    const val BASE_URL = "https://api.openweathermap.org/"
    const val API_KEY = "will be used only during testing time and will not be uploaded"
}

object StatusCode {

    const val BadRequest = 400
    const val Unauthorized = 401
    const val Forbidden = 403
    const val NotFound = 404
    const val Locked = 423
}

enum class WeatherDescription(val iconId: String) {
    ClearSkyDay("01d"),
    ClearSkyNight("01n"),
    FewCloudsDay("02d"),
    FewCloudsNight("02n"),
    ScatteredCloudsDay("03d"),
    ScatteredCloudsNight("03n"),
    BrokenCloudsDay("04d"),
    BrokenCloudsNight("04n"),
    ShowerRainDay("09d"),
    ShowerRainNight("09n"),
    RainDay("10d"),
    RainNight("10n"),
    ThunderstormDay("11d"),
    ThunderstormNight("11n"),
    SnowDay("13d"),
    SnowNight("13n"),
    MistDay("50d"),
    MistNight("50n"),
}
