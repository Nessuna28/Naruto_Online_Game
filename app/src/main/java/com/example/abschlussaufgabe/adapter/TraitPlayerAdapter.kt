package com.example.abschlussaufgabe.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.abschlussaufgabe.databinding.TraitPlayerItemBinding
import com.example.abschlussaufgabe.ui.MainViewModel

class TraitPlayerAdapter(private var dataset: Map<String, Int>,
                         private var viewModel: MainViewModel
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
        val traitString = dataset.keys.elementAt(position)
        val traitValue = dataset.values.elementAt(position)

        holder.binding.tvTraitsPlayer.text = traitString

        holder.binding.tvTraitsPlayer.setOnClickListener {
            viewModel.setAttackPlayer(traitString, traitValue)
        }
    }
}