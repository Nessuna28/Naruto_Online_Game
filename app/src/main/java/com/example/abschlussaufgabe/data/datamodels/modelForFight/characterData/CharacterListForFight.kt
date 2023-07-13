package com.example.abschlussaufgabe.data.datamodels.modelForFight.characterData

import com.example.abschlussaufgabe.R
import com.example.abschlussaufgabe.data.datamodels.modelForFight.CharacterForFight

class CharacterListForFight {

    val characterList = mutableListOf<CharacterForFight>(
        CharacterForFight(
            R.drawable.naruto_all,
    "Naruto",
    mapOf("Rasengan" to 50, "Rasenshuriken" to 70, "Jutsu des vertrauten Geistes" to 80, "Taijutsu" to 20),
    mapOf("Shuriken" to 20, "Kunai" to 15),
    mapOf("Weisenmodus" to 80, "Kiubimodus" to 100),
    listOf("Schattendoppelgänger", "Jutsu des Tausches"),
    "",
    R.drawable.naruto_all
    ),

    CharacterForFight(
        R.drawable.naruto_all,
        "Sasuke",
        mapOf("Chidori" to 50, "Feuerversteck" to 40, "Jutsu des vertrauten Geistes" to 80, "Taijutsu" to 30),
        mapOf("Shuriken" to 20, "Schwert" to 30),
        mapOf("Susanoo" to 100),
        listOf("Schattendoppelgänger", "Jutsu des Tausches"),
        "",
        R.drawable.naruto_all
    ),

    CharacterForFight(
        R.drawable.naruto_all,
        "Sakura",
        mapOf("große Sakura" to 20, "Kirschblütenschlag" to 50, "Kirschblütenformation" to 70, "Jutsu des vertrauten Geistes" to 80, "Taijutsu" to 15),
        mapOf("Shuriken" to 20, "Kunai" to 15),
        mapOf("Heilung" to 80),
        listOf("Schattendoppelgänger", "Jutsu des Tausches"),
        "",
        R.drawable.naruto_all
    )
    )
}