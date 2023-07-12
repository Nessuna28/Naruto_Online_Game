package com.example.abschlussaufgabe.data.datamodels.modelForFight

import androidx.room.Entity

@Entity
data class UniqueTrait(

    val uniqueTrait: String,
    val damaging: Int
)