package com.macgavrina.weatherapp.data.model

import androidx.room.*

//Each city item must contain a title of the city, its coordinates (lat, lng), humidity, and current air temperature.

@Entity
class City {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0

    @ColumnInfo(name = "name")
    var name: String = ""

    @Embedded
    var coordinates: Coordinates = Coordinates(0F, 0F)

    @ColumnInfo(name = "humidity")
    var humidity: Int = 0

    @Ignore
    var airTemp: Float = 0f
}