package com.example.abschlussaufgabe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.abschlussaufgabe.databinding.TeamItemBinding

class TeamAdapter(
    private var dataset: List<List<Int>>, private val onTeamClick: (List<Int>) -> Unit
): RecyclerView.Adapter<TeamAdapter.ItemViewHolder>() {


    inner class ItemViewHolder(val binding: TeamItemBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = TeamItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val team = dataset[position]

        holder.binding.ivDice1.setImageResource(team[0])
        holder.binding.ivDice2.setImageResource(team[1])
        holder.binding.ivDice3.setImageResource(team[2])
        holder.binding.ivDice4.setImageResource(team[3])
        holder.binding.ivDice5.setImageResource(team[4])
        holder.binding.ivDice6.setImageResource(team[5])

        holder.itemView.setOnClickListener {
            onTeamClick(team)
        }
    }
}
