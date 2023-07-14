package com.example.abschlussaufgabe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.abschlussaufgabe.databinding.JutsuEnemyItemBinding


class JutsuEnemyAdapter(private var dataset: Map<String, Int>
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
        val jutsuEnemy = dataset.keys.elementAt(position)

        holder.binding.tvJutsusEnemy.setText(jutsuEnemy)
    }
}