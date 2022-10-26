package com.qw73.weatherapptask.ui.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.qw73.weatherapptask.R
import com.qw73.weatherapptask.data.model.DayWeather
import com.qw73.weatherapptask.data.model.DegreeUnit
import com.qw73.weatherapptask.databinding.ItemClosedInfoBinding
import com.qw73.weatherapptask.databinding.ItemDayBinding
import com.qw73.weatherapptask.databinding.ItemOpenInfoBinding


class DayAdapter :
    ListAdapter<DayWeather, DayAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(private var binding: ItemDayBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dayWeather: DayWeather) {
            with(binding) {
                layoutCollapsed.root.setOnClickListener {
                    layoutExpanded.root.visibility = if (layoutExpanded.root.isVisible)
                        View.GONE
                    else
                        View.VISIBLE
                }
                renderCollapsedView(layoutCollapsed, dayWeather)
                renderExpandedView(layoutExpanded, dayWeather)
            }
        }

        private fun renderExpandedView(
            layoutExpanded: ItemOpenInfoBinding,
            dayWeather: DayWeather,
        ) = with(layoutExpanded) {
            tSunrise.text = dayWeather.sunrise
            tSunset.text = dayWeather.sunset
            tHumidity.text = dayWeather.humidity
            tSpeed.text = dayWeather.windSpeed

        }

        private fun renderCollapsedView(
            layoutCollapsed: ItemClosedInfoBinding,
            dayWeather: DayWeather,
        ) = with(layoutCollapsed) {
            textDegree.text = dayWeather.temperature
            textDay.text = dayWeather.weekDay
            textDate.text = dayWeather.date
            textUnit.setText(
                when (dayWeather.unit) {
                    DegreeUnit.CELSIUS -> R.string.degree_c
                    DegreeUnit.FAHRENHEIT -> R.string.degree_f
                }
            )
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder =

        ViewHolder(
            ItemDayBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(getItem(position))

    }

    companion object DiffCallback : DiffUtil.ItemCallback<DayWeather>() {
        override fun areItemsTheSame(
            oldItem: DayWeather,
            newItem: DayWeather,
        ): Boolean {
            return oldItem.weekDay == newItem.weekDay
        }

        override fun areContentsTheSame(
            oldItem: DayWeather,
            newItem: DayWeather,
        ): Boolean {
            return oldItem.temperature == newItem.temperature
        }
    }
}