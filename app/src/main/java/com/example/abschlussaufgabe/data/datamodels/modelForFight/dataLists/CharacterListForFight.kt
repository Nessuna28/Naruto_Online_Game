package com.example.abschlussaufgabe.data.datamodels.modelForFight.dataLists

import com.example.abschlussaufgabe.R
import com.example.abschlussaufgabe.data.datamodels.modelForFight.CharacterForFight

class CharacterListForFight {

    val characterList = mutableListOf(
        CharacterForFight(
            R.drawable.naruto,
            R.drawable.naruto_pose,
            R.drawable.naruto_face,
            R.drawable.naruto_attack,
            R.drawable.naruto_sad,
            R.raw.song_naruto,
            R.drawable.naruto_pose,
    "Naruto",
        mapOf("Rasengan" to 50, "Rasenshuriken" to 70, "Jutsu des vertrauten Geistes" to 80, "Taijutsu" to 20),
        mapOf("Shuriken" to 20, "Kunai" to 15),
        mapOf("Weisenmodus" to 80, "Kiubimodus" to 100),
        mapOf("Jutsu des Tausches" to 20, "Schattendoppelgänger" to 35),
            500, 500
    ),

    CharacterForFight(
        R.drawable.sasuke,
        R.drawable.sasuke_pose,
        R.drawable.sasuke_face,
        R.drawable.sasuke_attack,
        R.drawable.sasuke_sad,
        R.raw.song_sasuke,
        R.drawable.sasuke_pose,
        "Sasuke",
        mapOf("Chidori" to 50, "Feuerversteck" to 40, "Jutsu des vertrauten Geistes" to 80, "Taijutsu" to 30),
        mapOf("Shuriken" to 20, "Schwert" to 30),
        mapOf("Susanoo" to 100),
        mapOf("Jutsu des Tausches" to 20, "Schattendoppelgänger" to 35),
        500, 500
    ),

    CharacterForFight(
        R.drawable.sakura,
        R.drawable.sakura_pose,
        R.drawable.sakura_face,
        R.drawable.sakura_attack,
        R.drawable.sakura_sad,
        R.raw.song_sakura,
        R.raw.video_sakura,
        "Sakura",
        mapOf("große Sakura" to 20, "Kirschblütenschlag" to 50, "Kirschblütenformation" to 70, "Jutsu des vertrauten Geistes" to 80, "Taijutsu" to 15),
        mapOf("Shuriken" to 20, "Kunai" to 15),
        mapOf("Jahundertstärke" to 90),
        mapOf("Jutsu des Tausches" to 20, "Heilung" to 100),
        500, 500
    ),

        CharacterForFight(
            R.drawable.shikamaru,
            R.drawable.shikamaru_pose,
            R.drawable.shikamaru_face,
            R.drawable.shikamaru_attack,
            R.drawable.shikamaru_sad,
            R.raw.song_shikamaru,
            R.raw.video_shikamaru,
            "Shikamaru",
            mapOf("Schattenfesseln" to 70, "Taijutsu" to 30, "Schattenwürg" to 85, "PillePalle" to 40),
            mapOf("Kunai" to 20, "Explosionskunai" to 30),
            mapOf("Strategie" to 70),
            mapOf("Jutsu des Tausches" to 20, "Schattendoppelgänger" to 35),
            500, 500
        )
    )
}