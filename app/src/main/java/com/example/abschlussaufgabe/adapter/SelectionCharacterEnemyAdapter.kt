package com.example.abschlussaufgabe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.abschlussaufgabe.data.datamodels.modelForFight.CharacterForFight
import com.example.abschlussaufgabe.databinding.SelectionCharacterEnemyItemBinding
import com.example.abschlussaufgabe.FightViewModel


class SelectionCharacterEnemyAdapter(
    private var dataset: List<CharacterForFight>,
    private var fightViewModel: FightViewModel
): RecyclerView.Adapter<SelectionCharacterEnemyAdapter.ItemViewHolder>() {

    private var checkCharacterEnemy = false
    private var checkTeammate1Enemy = false
    private var checkTeammate2Enemy = false

    inner class ItemViewHolder(val binding: SelectionCharacterEnemyItemBinding) :
        RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = SelectionCharacterEnemyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val characterForFight = dataset[position]

        holder.binding.ivCharacter.setImageResource(characterForFight.imageFace)

        holder.binding.ivCharacter.setOnClickListener {
            if (!checkCharacterEnemy && !checkTeammate1Enemy && !checkTeammate2Enemy) {
                fightViewModel.setEnemy(characterForFight)
                checkCharacterEnemy = true
            } else if (checkCharacterEnemy && !checkTeammate1Enemy && !checkTeammate2Enemy) {
                fightViewModel.setTeammateEnemy(characterForFight)
                checkTeammate1Enemy = true
            } else if (checkCharacterEnemy && checkTeammate1Enemy && !checkTeammate2Enemy) {
                fightViewModel.setTeammateEnemy(characterForFight)
                checkTeammate2Enemy = true
            }
        }
    }
}