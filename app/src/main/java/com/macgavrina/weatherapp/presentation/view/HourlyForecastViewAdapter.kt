package com.macgavrina.weatherapp.presentation.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.macgavrina.weatherapp.MainApplication
import com.macgavrina.weatherapp.R
import com.macgavrina.weatherapp.data.model.HourlyForecastElement
import com.macgavrina.weatherapp.support.DateFormatter
import kotlinx.android.synthetic.main.hourly_forecast_list_item.view.*

class HourlyForecastViewAdapter:

    RecyclerView.Adapter<HourlyForecastViewAdapter.ViewHolder>() {

    private var mItems: List<HourlyForecastElement>? = null

    fun setForecast(forecast: List<HourlyForecastElement>) {
        this.mItems = forecast
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val time = view.hourly_forecast_time
        val temperature = view.hourly_forecast_temperature
        val pressure = view.hourly_forecast_pressure
        val humidity = view.hourly_forecast_humidity
        val wind = view.hourly_forecast_wind

        private var mItem: HourlyForecastElement? = null

        fun setItem(item: HourlyForecastElement) {
            mItem = item
        }

    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): HourlyForecastViewAdapter.ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        // create a new view
        val view = layoutInflater.inflate(R.layout.hourly_forecast_list_item, parent, false)
        // set the view's size, margins, paddings and layout parameters

        return ViewHolder(view)
    }


    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        val item = mItems?.get(position) ?: return


        holder.time.text = DateFormatter.formatShortDateAndTimeFromTimestamp(item.dt)
        holder.humidity.text = "${item.main.humidity} %"
        holder.pressure.text = MainApplication.applicationContext().resources.getString(R.string.pressure,
            String.format("%.0f", item.main.pressure))
        holder.temperature.text = MainApplication.applicationContext().resources.getString(R.string.temperature,
            String.format("%.0f", item.main.temp))
        holder.wind.text = MainApplication.applicationContext().resources.getString(R.string.windspeed,
            String.format("%.0f", item.wind.speed))

        holder.setItem(mItems!![position])
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        if (mItems != null) {
            return mItems!!.size
        }
        return -1
    }

}