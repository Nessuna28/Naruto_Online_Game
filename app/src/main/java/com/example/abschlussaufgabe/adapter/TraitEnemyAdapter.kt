package com.example.abschlussaufgabe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.abschlussaufgabe.data.datamodels.modelForFight.UniqueTrait
import com.example.abschlussaufgabe.databinding.TraitEnemyItemBinding

class TraitEnemyAdapter(private var dataset: List<UniqueTrait>
): RecyclerView.Adapter<TraitEnemyAdapter.ItemViewHolder>() {


    inner class ItemViewHolder(val binding: TraitEnemyItemBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            TraitEnemyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val traitsEnemy = dataset[position]

        holder.binding.tvTraitsEnemy.text = traitsEnemy.name
    }
}