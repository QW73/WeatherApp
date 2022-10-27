package com.qw73.weatherapptask.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.qw73.weatherapptask.data.model.CityResponse
import com.qw73.weatherapptask.data.model.getFullName
import com.qw73.weatherapptask.databinding.ItemSearchBinding

class CityAdapter(private val onItemClicked: (CityResponse) -> Unit = {}) :
    ListAdapter<CityResponse, CityAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(private var binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cityResponse: CityResponse, onItemClicked: (CityResponse) -> Unit) {
            with(binding) {
                root.setOnClickListener { onItemClicked(cityResponse) }
                tCity.text = cityResponse.getFullName()
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder =

        ViewHolder(
            ItemSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position), onItemClicked)

    companion object DiffCallback : DiffUtil.ItemCallback<CityResponse>() {
        override fun areItemsTheSame(
            oldItem: CityResponse,
            newItem: CityResponse,
        ): Boolean = oldItem.name == newItem.name

        override fun areContentsTheSame(
            oldItem: CityResponse,
            newItem: CityResponse,
        ): Boolean = oldItem.name == newItem.name
    }
}