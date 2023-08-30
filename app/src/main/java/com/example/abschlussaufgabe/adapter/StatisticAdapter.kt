package com.example.abschlussaufgabe.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.abschlussaufgabe.FirestoreViewModel
import com.example.abschlussaufgabe.MainViewModel
import com.example.abschlussaufgabe.data.datamodels.modelForFight.fightDataForDatabase.DataPlayer
import com.example.abschlussaufgabe.databinding.StatisticItemBinding


class StatisticAdapter(
    private val viewModel: MainViewModel,
    private val storeViewModel: FirestoreViewModel
): RecyclerView.Adapter<StatisticAdapter.ItemViewHolder>() {

    private var dataset: List<DataPlayer> = listOf()

    class ItemViewHolder(val binding: StatisticItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun replaceDataSet(dataList: List<DataPlayer>) {
        this.dataset = dataList
        notifyDataSetChanged()
    }

    fun removeData(position: Int) {
        viewModel.deleteDataGame(dataset[position])
        if (storeViewModel.currentProfile.value != null) {
            storeViewModel.deletePlayerData(dataset[position])
        }
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = StatisticItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val dataset = dataset[position]

        holder.binding.tvDate.text = dataset.date
        holder.binding.ivCharacterImagePlayer.setImageResource(dataset.characterImage)
        holder.binding.tvCharacterNamePlayer.text = dataset.characterName
        holder.binding.tvResultPlayer.text = dataset.result
        holder.binding.tvUserNamePlayer.text = dataset.userName
        holder.binding.ivCharacterImageEnemy.setImageResource(dataset.characterImageEnemy)
        holder.binding.tvCharacterNameEnemy.text = dataset.characterNameEnemy
        holder.binding.tvResultEnemy.text = dataset.resultEnemy
        holder.binding.tvUserNameEnemy.text = dataset.userNameEnemy
    }
}