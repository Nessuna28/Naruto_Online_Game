package com.example.abschlussaufgabe.data.datamodels.modelForFight.fightDataForDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.auth.FirebaseAuth

@Entity(tableName = "player_table")
data class DataPlayer(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val date: String,
    val userName: String,
    val characterName: String,
    val characterImage: Int,
    val result: String,

    val userNameEnemy: String,
    val characterNameEnemy: String,
    val characterImageEnemy: Int,
    val resultEnemy: String,
)