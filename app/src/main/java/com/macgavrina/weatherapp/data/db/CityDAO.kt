package com.macgavrina.weatherapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.macgavrina.weatherapp.data.model.City
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface CityDAO {
    @Query("SELECT * FROM city")
    fun getAll(): Single<List<City>>

    @Insert
    fun insertCity(city: City): Completable

    @Query("SELECT * FROM city WHERE uid=:cityId")
    fun getCity(cityId: Int): Maybe<City>
}