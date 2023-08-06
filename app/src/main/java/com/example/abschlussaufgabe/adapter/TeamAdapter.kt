package com.example.abschlussaufgabe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.abschlussaufgabe.data.datamodels.modelForKniffel.Dice
import com.example.abschlussaufgabe.databinding.TeamItemBinding

class TeamAdapter(
    private var dataset: List<Dice>, private val onTeamClick: (Dice) -> Unit
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

        holder.binding.tvTeamName.text = team.teamName
        holder.binding.ivDice1.setImageResource(team.diceSide1.image)
        holder.binding.ivDice2.setImageResource(team.diceSide2.image)
        holder.binding.ivDice3.setImageResource(team.diceSide3.image)
        holder.binding.ivDice4.setImageResource(team.diceSide4.image)
        holder.binding.ivDice5.setImageResource(team.diceSide5.image)
        holder.binding.ivDice6.setImageResource(team.diceSide6.image)

        holder.itemView.setOnClickListener {
            onTeamClick(team)
        }
    }
}
