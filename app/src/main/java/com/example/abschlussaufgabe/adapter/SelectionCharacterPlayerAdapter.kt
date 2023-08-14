package com.example.abschlussaufgabe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.abschlussaufgabe.data.datamodels.modelForFight.CharacterForFight
import com.example.abschlussaufgabe.databinding.SelectionCharacterPlayerItemBinding
import com.example.abschlussaufgabe.ui.FightViewModel


class SelectionCharacterPlayerAdapter(
    private var dataset: List<CharacterForFight>,
    private var fightViewModel: FightViewModel
): RecyclerView.Adapter<SelectionCharacterPlayerAdapter.ItemViewHolder>() {


    inner class ItemViewHolder(val binding: SelectionCharacterPlayerItemBinding) :
        RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = SelectionCharacterPlayerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val characterForFight = dataset[position]

        holder.binding.ivCharacter.setImageResource(characterForFight.imageFace)

        holder.binding.ivCharacter.setOnClickListener {
            fightViewModel.setPlayer(characterForFight)
        }
    }
}
