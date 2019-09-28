package com.macgavrina.weatherapp.data.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.macgavrina.weatherapp.LOG_TAG
import com.macgavrina.weatherapp.data.model.City
import com.macgavrina.weatherapp.data.reppository.CityRepository
import io.reactivex.schedulers.Schedulers

@Database(entities = arrayOf(City::class), version = AppDatabase.DATABASE_VERSION)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cityDAO(): CityDAO

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "WeatherAppDB"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "Sample.db"
            )
                // prepopulate the database after onCreate was called
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Log.d(LOG_TAG, "onCreateDB")
                        // insert the data on the IO Thread
                        initializeDataInDB()
                    }
                })
                .build()


        private fun initializeDataInDB() {
            createDefaultCities()
        }

        private fun createDefaultCities() {
            val cityMoscow = City()
            val citySP = City()

            cityMoscow.name = "Moscow"
            cityMoscow.coordinates.lat = 55.751244F
            cityMoscow.coordinates.lng = 37.618423F

            citySP.name = "Saint-Petersburg"
            citySP.coordinates.lat = 59.939095F
            citySP.coordinates.lng = 30.315868F


            //ToDo добавить в CompositeDisposable и подумать где его очищать

            CityRepository.addCity(cityMoscow)
                .subscribeOn(Schedulers.io())
                .subscribe {
                    Log.d(LOG_TAG, "Moscow is added to DB")
                }

            CityRepository.addCity(citySP)
                .subscribeOn(Schedulers.io())
                .subscribe {
                    Log.d(LOG_TAG, "Saint-Petersburg is added to DB")
                }
        }
    }
}