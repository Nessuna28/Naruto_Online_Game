package com.example.abschlussaufgabe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.abschlussaufgabe.data.datamodels.modelForFight.Jutsu
import com.example.abschlussaufgabe.databinding.JutsuEnemyItemBinding


class JutsuEnemyAdapter(private var dataset: List<Jutsu>
): RecyclerView.Adapter<JutsuEnemyAdapter.ItemViewHolder>() {


    inner class ItemViewHolder(val binding: JutsuEnemyItemBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            JutsuEnemyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val jutsuEnemy = dataset[position]

        holder.binding.tvJutsuEnemy.text = jutsuEnemy.name
    }
}