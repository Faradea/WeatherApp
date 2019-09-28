package com.macgavrina.weatherapp.data.model

import androidx.room.ColumnInfo

//ToDo Merge with Coord or migrate to domain level
data class Coordinates(
    var lat: Float = 0.0f,
    var lng: Float = 0.0f
)