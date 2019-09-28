package com.macgavrina.weatherapp.data.model

import androidx.room.ColumnInfo

data class Coordinates(
    var lat: Float = 0.0f,
    var lng: Float = 0.0f
)