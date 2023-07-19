package com.example.abschlussaufgabe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.abschlussaufgabe.data.datamodels.modelForFight.fightDataForDatabase.DataPlayer
import com.example.abschlussaufgabe.databinding.StatisticItemBinding


class StatisticAdapter(
    private var dataset: List<DataPlayer>,
): RecyclerView.Adapter<StatisticAdapter.ItemViewHolder>() {


    inner class ItemViewHolder(val binding: StatisticItemBinding) :
        RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = StatisticItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val statistic = dataset[position]

        holder.binding.tvDate.text = statistic.date
        holder.binding.ivCharacterImagePlayer.setImageResource(statistic.characterImage)
        holder.binding.tvCharacterNamePlayer.text = statistic.characterName
        holder.binding.tvLifeValuePlayer.text = statistic.lifePoints.toString()
        holder.binding.tvResultPlayer.text = statistic.result
        holder.binding.tvUserNamePlayer.text = statistic.userName
        holder.binding.ivCharacterImageEnemy.setImageResource(statistic.characterImageEnemy)
        holder.binding.tvCharacterNameEnemy.text = statistic.characterNameEnemy
        holder.binding.tvLifeValueEnemy.text = statistic.lifePointsEnemy.toString()
        holder.binding.tvResultEnemy.text = statistic.resultEnemy
        holder.binding.tvUserNameEnemy.text = statistic.userNameEnemy
    }
}