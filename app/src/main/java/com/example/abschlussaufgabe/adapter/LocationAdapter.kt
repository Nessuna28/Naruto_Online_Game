package com.example.abschlussaufgabe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.abschlussaufgabe.databinding.LocationItemBinding
import com.example.abschlussaufgabe.ui.MainViewModel

class LocationAdapter(private var dataset: Map<String, Int>,
                      private var viewModel: MainViewModel
): RecyclerView.Adapter<LocationAdapter.ItemViewHolder>() {


    inner class ItemViewHolder(val binding: LocationItemBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            LocationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val locationName = dataset.keys.elementAt(position)
        val locationImage = dataset.values.elementAt(position)

        holder.binding.ivLocation.setImageResource(locationImage)

        holder.binding.ivLocation.setOnClickListener {
            viewModel.setLocation(locationName, locationImage)
        }
    }
}