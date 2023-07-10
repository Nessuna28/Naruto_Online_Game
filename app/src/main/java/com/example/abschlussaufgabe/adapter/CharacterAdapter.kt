package com.example.abschlussaufgabe.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.abschlussaufgabe.R
import com.example.abschlussaufgabe.data.datamodels.modelsApi.Character
import com.example.abschlussaufgabe.databinding.CharacterItemBinding
import com.example.abschlussaufgabe.ui.AboutTheCharactersFragmentDirections

class CharacterAdapter(private var dataset: List<Character>
): RecyclerView.Adapter<CharacterAdapter.ItemViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Character>) {
        dataset = list
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(val binding: CharacterItemBinding, val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = CharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.character_item, parent, false)

        return ItemViewHolder(binding, adapterLayout)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val character = dataset[position]

        holder.binding.ivImageCharacter.setImageResource(character.images[0].toInt())
        holder.binding.ivName.setText(character.name)

        holder.binding.mcCharacter.setOnClickListener {
            holder.binding.mcCharacter.findNavController().navigate(AboutTheCharactersFragmentDirections.actionAboutTheCharactersFragmentToCharacterDetailFragment())
        }
    }
}