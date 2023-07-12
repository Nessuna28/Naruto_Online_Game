package com.example.abschlussaufgabe.data.datamodels.modelForFight

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.abschlussaufgabe.data.datamodels.modelsApi.Character

@Entity(tableName = "character_table")
data class CharacterForFight(

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val image: Int,
    val name: String,
    val jutsus: List<Jutsu>,
    val tools: List<Tool>,
    val uniqueTraits: List<UniqueTrait>,
    val sound: String,
    val video: Int
)