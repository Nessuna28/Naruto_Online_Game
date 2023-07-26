package com.example.abschlussaufgabe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.abschlussaufgabe.databinding.JutsuPlayerItemBinding
import com.example.abschlussaufgabe.ui.MainViewModel


class JutsuPlayerAdapter(private var dataset: Map<String, Int>,
                         private var viewModel: MainViewModel
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
        val jutsuString = dataset.keys.elementAt(position)
        val jutsuValue = dataset.values.elementAt(position)

        holder.binding.tvJutsuPlayer.text = jutsuString

        holder.binding.tvJutsuPlayer.setOnClickListener {
            viewModel.setAttackPlayer(jutsuString, jutsuValue)
        }
    }
}