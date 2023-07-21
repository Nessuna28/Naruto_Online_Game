package com.example.abschlussaufgabe.data.datamodels.modelForFight.dataLists

import com.example.abschlussaufgabe.R
import com.example.abschlussaufgabe.data.datamodels.modelForFight.CharacterForFight

class CharacterListForFight {

    val characterList = mutableListOf(
        CharacterForFight(
            R.drawable.naruto, R.drawable.naruto_pose, R.drawable.naruto_face, R.drawable.naruto_attack,
    "Naruto",
        mapOf("Rasengan" to 50, "Rasenshuriken" to 70, "Jutsu des vertrauten Geistes" to 80, "Taijutsu" to 20),
        mapOf("Shuriken" to 20, "Kunai" to 15),
        mapOf("Weisenmodus" to 80, "Kiubimodus" to 100),
        mapOf("Jutsu des Tausches" to 20, "Schattendoppelgänger" to 35),
        R.raw.song_naruto, R.drawable.naruto_pose,
            500, 500
    ),

    CharacterForFight(
        R.drawable.sasuke, R.drawable.sasuke_pose, R.drawable.sasuke_face, R.drawable.sasuke_attack,
        "Sasuke",
        mapOf("Chidori" to 50, "Feuerversteck" to 40, "Jutsu des vertrauten Geistes" to 80, "Taijutsu" to 30),
        mapOf("Shuriken" to 20, "Schwert" to 30),
        mapOf("Susanoo" to 100),
        mapOf("Jutsu des Tausches" to 20, "Schattendoppelgänger" to 35),
        R.raw.song_sasuke, R.drawable.sasuke_pose,
        500, 500
    ),

    CharacterForFight(
        R.drawable.sakura, R.drawable.sakura_pose, R.drawable.sakura_face, R.drawable.sakura_attack,
        "Sakura",
        mapOf("große Sakura" to 20, "Kirschblütenschlag" to 50, "Kirschblütenformation" to 70, "Jutsu des vertrauten Geistes" to 80, "Taijutsu" to 15),
        mapOf("Shuriken" to 20, "Kunai" to 15),
        mapOf("Jahundertstärke" to 90),
        mapOf("Jutsu des Tausches" to 20, "Heilung" to 100),
        R.raw.song_sakura, R.raw.video_sakura,
        500, 500
    )
    )
}