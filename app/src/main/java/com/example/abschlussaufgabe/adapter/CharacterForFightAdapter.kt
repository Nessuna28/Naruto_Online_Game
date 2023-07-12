package com.example.abschlussaufgabe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.RecyclerView
import com.example.abschlussaufgabe.data.datamodels.modelForFight.CharacterForFight
import com.example.abschlussaufgabe.databinding.JutsuComItemBinding
import com.example.abschlussaufgabe.databinding.JutsuPlayerItemBinding
import com.example.abschlussaufgabe.databinding.SelectionCharacterItemBinding


class CharacterForFightAdapter(private var dataset: List<CharacterForFight>
): RecyclerView.Adapter<CharacterForFightAdapter.ItemViewHolder>() {


    inner class ItemViewHolderCharacter(val bindingCharacter: SelectionCharacterItemBinding) :
        RecyclerView.ViewHolder(bindingCharacter.root)

    inner class ItemViewHolderJutsuPlayer(val bindingJutsuPlayer: JutsuPlayerItemBinding) :
        RecyclerView.ViewHolder(bindingJutsuPlayer.root)

    inner class ItemViewHolderJutsuCom(val bindingJutsuCom: JutsuComItemBinding) :
        RecyclerView.ViewHolder(bindingJutsuCom.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = SelectionCharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val characterForFight = dataset[position]

        holder.binding.ivCharacter.setImageResource(characterForFight.image)

        holder.binding.ivCharacter.setOnClickListener {

        }
    }
}
