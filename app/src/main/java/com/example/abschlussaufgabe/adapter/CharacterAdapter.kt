package com.example.abschlussaufgabe.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.abschlussaufgabe.R
import com.example.abschlussaufgabe.data.datamodels.modelsApi.Character
import com.example.abschlussaufgabe.databinding.CharacterItemBinding
import com.example.abschlussaufgabe.ui.AboutTheCharactersFragmentDirections

const val TAG = "CharacterAdapter"

class CharacterAdapter(private var dataset: List<Character>
): RecyclerView.Adapter<CharacterAdapter.ItemViewHolder>() {


    inner class ItemViewHolder(val binding: CharacterItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = CharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val character = dataset[position]

        try {
            val imgUri = character.images.first().toUri().buildUpon().scheme("https").build()
            holder.binding.ivImageCharacter.load(imgUri)
        } catch (e: Exception) {
            Log.e(TAG, "Error: ${e.message} im CharacterAdapter")
            holder.binding.ivImageCharacter.setImageResource(R.drawable.no_picture)
        }

        holder.binding.ivName.setText(character.name)

        holder.binding.mcCharacter.setOnClickListener {
            holder.itemView.findNavController().navigate(AboutTheCharactersFragmentDirections
                .actionAboutTheCharactersFragmentToCharacterDetailFragment(
                    character.name,
                    character.images[0],
                    character.natureType.toTypedArray(),
                    character.jutsu.toTypedArray(),
                    character.tools.toTypedArray()
            ))
        }
    }
}

