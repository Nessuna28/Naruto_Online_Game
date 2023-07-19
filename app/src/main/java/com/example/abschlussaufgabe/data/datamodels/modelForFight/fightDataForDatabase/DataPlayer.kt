package com.example.abschlussaufgabe.data.datamodels.modelForFight.fightDataForDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player_table")
data class DataPlayer(

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val date: String,
    val userName: String,
    val characterName: String,
    val characterImage: Int,
    val lifePoints: Int,
    val result: String,

    val userNameEnemy: String,
    val characterNameEnemy: String,
    val characterImageEnemy: Int,
    val lifePointsEnemy: Int,
    val resultEnemy: String,

    val victories: Int,
    val defeats: Int
)