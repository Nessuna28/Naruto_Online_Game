package com.example.abschlussaufgabe.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.abschlussaufgabe.data.datamodels.modelForFight.UniqueTrait
import com.example.abschlussaufgabe.databinding.TraitPlayerItemBinding
import com.example.abschlussaufgabe.ui.FightViewModel

class TraitPlayerAdapter(private var dataset: List<UniqueTrait>,
                         private var fightViewModel: FightViewModel
): RecyclerView.Adapter<TraitPlayerAdapter.ItemViewHolder>() {


    inner class ItemViewHolder(val binding: TraitPlayerItemBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            TraitPlayerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val trait = dataset[position]

        holder.binding.tvTraitsPlayer.text = trait.name

        holder.binding.tvTraitsPlayer.setOnClickListener {
            fightViewModel.setAttackPlayer(trait)
        }
    }
}