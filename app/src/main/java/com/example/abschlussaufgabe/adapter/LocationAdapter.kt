package com.example.abschlussaufgabe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.abschlussaufgabe.data.datamodels.modelForFight.fightDataForDatabase.Location
import com.example.abschlussaufgabe.databinding.LocationItemBinding
import com.example.abschlussaufgabe.ui.MainViewModel

class LocationAdapter(private var dataset: List<Location>,
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
        val location = dataset[position]

        holder.binding.ivLocation.setImageResource(location.image)

        holder.binding.ivLocation.setOnClickListener {
            viewModel.setLocation(location)
        }
    }
}