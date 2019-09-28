package com.macgavrina.weatherapp.data.model

data class WeatherForCity (
    val coord : Coord,
    val weather : List<Weather>,
    val base : String,
    val main : Main,
    val wind : Wind,
    val clouds : Clouds,
    val dt : Int,
    val sys : Sys,
    val id : Int,
    val name : String,
    val cod : Int
)