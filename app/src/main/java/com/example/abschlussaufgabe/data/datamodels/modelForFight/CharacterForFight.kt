package com.example.abschlussaufgabe.data.datamodels.modelForFight

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "character_table")
data class CharacterForFight(

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val image: Int,
    val name: String,
    val jutsu1: String,
    val jutsu2: String,
    val jutsu3: String,
    val jutsu4: String,
    val jutsu1Damage: Int,
    val jutsu2Damage: Int,
    val jutsu3Damage: Int,
    val jutsu4Damage: Int,
    val tool1: String,
    val tool2: String,
    val tool1Damage: Int,
    val tool2Damage: Int,
    val uniqueTrait1: String,
    val uniqueTrait2: String,
    val uniqueTrait1Damage: Int,
    val uniqueTrait2Damage: Int,
    val sound: String,
    val video: Int
)