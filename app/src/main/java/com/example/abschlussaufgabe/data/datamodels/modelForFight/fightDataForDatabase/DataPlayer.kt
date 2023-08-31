package com.example.abschlussaufgabe.data.datamodels.modelForFight.fightDataForDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player_table")
data class DataPlayer(

    @PrimaryKey(autoGenerate = true)
    val localId: Int = 0, //  Room-interne ID

    val firestoreId: String, // Firestore-ID (als String)

    val userId: String,
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