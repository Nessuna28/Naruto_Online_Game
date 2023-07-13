package com.example.abschlussaufgabe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.abschlussaufgabe.data.datamodels.modelForFight.CharacterForFight
import com.example.abschlussaufgabe.databinding.JutsuPlayerItemBinding


class JutsuPlayerAdapter(private var dataset: List<CharacterForFight>
): RecyclerView.Adapter<JutsuPlayerAdapter.ItemViewHolder>() {


    inner class ItemViewHolder(val binding: JutsuPlayerItemBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            JutsuPlayerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val characterPlayer = dataset[position]

        holder.binding.tvJutsusPlayer.setText(characterPlayer.jutsus)
    }
}