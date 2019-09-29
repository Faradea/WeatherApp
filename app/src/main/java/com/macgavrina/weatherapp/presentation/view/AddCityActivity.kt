package com.macgavrina.weatherapp.presentation.view

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.macgavrina.weatherapp.LOG_TAG
import com.macgavrina.weatherapp.R
import com.macgavrina.weatherapp.data.model.City
import com.macgavrina.weatherapp.data.model.Coordinates
import com.macgavrina.weatherapp.domain.usecase.CityUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_add_city.*

//Use MVVM for this activity is overhead

class AddCityActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_city)

        add_city_add_button.setOnClickListener {

            //ToDo UI can be improved by using icons and error state in input layouts

            if (add_city_name_et.text.isNullOrEmpty()) {
                showError("Please enter city name")
                return@setOnClickListener
            }
            val cityName = add_city_name_et.text.toString()

            val lat = add_city_latitude_et.text.toString().toFloatOrNull()
            if (lat == null) {
                showError("Please enter latitude in correct format")
                return@setOnClickListener
            }

            val lng = add_city_longitude_et.text.toString().toFloatOrNull()
            if (lng == null) {
                showError("Please enter longitude in correct format")
                return@setOnClickListener
            }

            val newCity = City()
            newCity.name = cityName
            newCity.coordinates.lat = lat
            newCity.coordinates.lng = lng

            CityUseCase.addCity(newCity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    setResult(Activity.RESULT_OK)
                    //finishActivity(ADD_CITY_ACTIVITY_REQUEST_CODE)
                    finish()
                }, {error ->
                    Log.e(LOG_TAG, "Error adding city to DB, $error")
                    showError("Error adding city")
                })

        }
    }

    private fun showError(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}
