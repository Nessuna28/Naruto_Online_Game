package com.example.abschlussaufgabe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.abschlussaufgabe.databinding.TraitsPlayerItemBinding

class TraitsPlayerAdapter(private var dataset: Map<String, Int>
): RecyclerView.Adapter<TraitsPlayerAdapter.ItemViewHolder>() {


    inner class ItemViewHolder(val binding: TraitsPlayerItemBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            TraitsPlayerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val traitsPlayer = dataset.keys.elementAt(position)

        holder.binding.tvTraitsPlayer.text = traitsPlayer
    }
}