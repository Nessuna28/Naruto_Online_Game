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
        holder.binding.ivDice1.setImageResource(team.image1)
        holder.binding.ivDice2.setImageResource(team.image2)
        holder.binding.ivDice3.setImageResource(team.image3)
        holder.binding.ivDice4.setImageResource(team.image4)
        holder.binding.ivDice5.setImageResource(team.image5)
        holder.binding.ivDice6.setImageResource(team.image6)

        holder.itemView.setOnClickListener {
            onTeamClick(team)
        }
    }
}
