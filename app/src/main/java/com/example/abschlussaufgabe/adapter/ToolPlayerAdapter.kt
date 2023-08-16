package com.example.abschlussaufgabe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.abschlussaufgabe.data.datamodels.modelForFight.Tool
import com.example.abschlussaufgabe.databinding.ToolPlayerItemBinding
import com.example.abschlussaufgabe.FightViewModel

class ToolPlayerAdapter(private var dataset: List<Tool>,
                        private var fightViewModel: FightViewModel
): RecyclerView.Adapter<ToolPlayerAdapter.ItemViewHolder>() {


    inner class ItemViewHolder(val binding: ToolPlayerItemBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            ToolPlayerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val tool = dataset[position]

        holder.binding.tvTool.text = tool.name

        holder.binding.tvTool.setOnClickListener {
            fightViewModel.setAttackPlayer(tool)
        }
    }
}