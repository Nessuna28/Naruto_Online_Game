package com.example.abschlussaufgabe.data.datamodels.modelForFight

import androidx.room.Entity

@Entity
data class Tool(

    val tool: String,
    val damaging: Int
)