package com.example.abschlussaufgabe.data.datamodels.modelForFight

import androidx.room.Entity

@Entity
data class Jutsu(

    val jutsu: String,
    val damaging: Int
)