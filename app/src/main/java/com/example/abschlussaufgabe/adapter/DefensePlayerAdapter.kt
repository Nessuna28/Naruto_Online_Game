package com.example.abschlussaufgabe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.abschlussaufgabe.data.datamodels.modelForFight.Defense
import com.example.abschlussaufgabe.databinding.DefensePlayerItemBinding
import com.example.abschlussaufgabe.ui.FightViewModel


class DefensePlayerAdapter(private var dataset: List<Defense>,
                           private var fightViewModel: FightViewModel
): RecyclerView.Adapter<DefensePlayerAdapter.ItemViewHolder>() {


    inner class ItemViewHolder(val binding: DefensePlayerItemBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            DefensePlayerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val defense = dataset[position]

        holder.binding.tvDefense.text = defense.name

        holder.binding.tvDefense.setOnClickListener {
            fightViewModel.setAttackPlayer(defense)
        }
    }
}