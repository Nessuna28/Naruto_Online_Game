package com.example.abschlussaufgabe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.abschlussaufgabe.data.datamodels.modelForFight.CharacterForFight
import com.example.abschlussaufgabe.databinding.SelectionCharacterEnemyItemBinding
import com.example.abschlussaufgabe.FightViewModel


class SelectionCharacterEnemyAdapter(
    private var dataset: List<CharacterForFight>,
    private var fightViewModel: FightViewModel
): RecyclerView.Adapter<SelectionCharacterEnemyAdapter.ItemViewHolder>() {


    inner class ItemViewHolder(val binding: SelectionCharacterEnemyItemBinding) :
        RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = SelectionCharacterEnemyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val characterForFight = dataset[position]

        holder.binding.ivCharacter.setImageResource(characterForFight.imageFace)

        holder.binding.ivCharacter.setOnClickListener {
            fightViewModel.setEnemy(characterForFight)
        }
    }
}