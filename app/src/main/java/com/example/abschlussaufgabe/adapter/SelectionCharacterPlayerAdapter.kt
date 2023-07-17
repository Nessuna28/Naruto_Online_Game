package com.example.abschlussaufgabe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.abschlussaufgabe.data.datamodels.modelForFight.CharacterForFight
import com.example.abschlussaufgabe.databinding.SelectionCharacterPlayerItemBinding
import com.example.abschlussaufgabe.ui.MainViewModel


class SelectionCharacterPlayerAdapter(
    private var dataset: List<CharacterForFight>,
    private var viewModel: MainViewModel
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

        holder.binding.ivCharacter.setImageResource(characterForFight.image)

        holder.binding.ivCharacter.setOnClickListener {
            viewModel.setImageForPlayer(characterForFight.image, characterForFight.image2)
            viewModel.setCharacterNameForPlayer(characterForFight.name)
            viewModel.setJutsuForPlayer(characterForFight.jutsus)
            viewModel.setUniqueTraitForPlayer(characterForFight.uniqueTraits)
        }
    }
}