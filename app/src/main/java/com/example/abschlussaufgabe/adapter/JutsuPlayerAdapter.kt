package com.example.abschlussaufgabe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.abschlussaufgabe.databinding.JutsuPlayerItemBinding
import com.example.abschlussaufgabe.ui.MainViewModel


class JutsuPlayerAdapter(private var dataset: Map<String, Int>
): RecyclerView.Adapter<JutsuPlayerAdapter.ItemViewHolder>() {


    inner class ItemViewHolder(val binding: JutsuPlayerItemBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            JutsuPlayerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val jutsuPlayer = dataset.keys.elementAt(position)

        holder.binding.tvJutsuPlayer.text = jutsuPlayer
    }
}