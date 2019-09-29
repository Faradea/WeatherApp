package com.macgavrina.weatherapp.data.model

//ToDo separate - one class to one file

data class HourlyForecast (
    val cod: String,
    val message: Double,
    val cnt: Long,
    val list: List<HourlyForecastElement>,
    val city: City
)

data class HourlyForecastElement (
    val dt: Long,
    val main: MainClass,
    val weather: List<Weather>,
    val clouds: Clouds,
    val wind: Wind,
    val sys: Sys,
    val dtTxt: String,
    val rain: Rain? = null
)

data class MainClass (
    val temp: Double,
    val tempMin: Double,
    val tempMax: Double,
    val pressure: Double,
    val seaLevel: Double,
    val grndLevel: Double,
    val humidity: Long,
    val tempKf: Double
)

data class Rain (
    val the1H: Double? = null
)

enum class Pod {
    D,
    N
}

enum class Description {
    BrokenClouds,
    ClearSky,
    FewClouds,
    LightRain,
    OvercastClouds,
    ScatteredClouds
}

enum class MainEnum {
    Clear,
    Clouds,
    Rain
}