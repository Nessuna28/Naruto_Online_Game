package com.example.abschlussaufgabe.data.datamodels.modelForFight.FightDataForDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player_table")
data class Player(

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val characterName: String,
    val characterImage: Int,
    val victories: Int,
    val defeats: Int,
)