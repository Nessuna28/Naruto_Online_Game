package com.example.abschlussaufgabe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.abschlussaufgabe.databinding.TraitsEnemyItemBinding

class TraitsEnemyAdapter(private var dataset: Map<String, Int>
): RecyclerView.Adapter<TraitsEnemyAdapter.ItemViewHolder>() {


    inner class ItemViewHolder(val binding: TraitsEnemyItemBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            TraitsEnemyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val traitsEnemy = dataset.keys.elementAt(position)

        holder.binding.tvTraitsEnemy.text = traitsEnemy
    }
}