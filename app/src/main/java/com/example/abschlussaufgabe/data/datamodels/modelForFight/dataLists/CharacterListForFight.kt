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
            R.raw.video_naruto,
    "Naruto",
        mapOf("Taijutsu" to 20, "Rasengan" to 50, "Rasenshuriken" to 70, "Jutsu des vertrauten Geistes" to 80),
        mapOf("Kunai" to 15, "Shuriken" to 20),
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
        R.drawable.sasuke_attack,
        "Sasuke",
        mapOf("Taijutsu" to 30, "Feuerversteck" to 40, "Chidori" to 50, "Jutsu des vertrauten Geistes" to 80),
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
        mapOf( "Taijutsu" to 15, "große Sakura" to 20, "Kirschblütenschlag" to 50, "Kirschblütenformation" to 70, "Jutsu des vertrauten Geistes" to 80),
        mapOf( "Kunai" to 15, "Shuriken" to 20),
        mapOf("Jahundertstärke" to 90, "Heilung" to 100),
        mapOf("Jutsu des Tausches" to 20, "Schattendoppelgänger" to 35),
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
            mapOf("Taijutsu" to 30,"Schattenfesseln" to 55, "Schattenwürg" to 65, "Schattenmessen" to 70),
            mapOf("Kunai" to 15, "Explosionskunai" to 30),
            mapOf("Strategie" to 70),
            mapOf("Jutsu des Tausches" to 20, "Schattendoppelgänger" to 35),
            500, 500
        ),

        CharacterForFight(
            R.drawable.choji,
            R.drawable.choji_pose,
            R.drawable.choji_face,
            R.drawable.choji_attack,
            R.drawable.choji_sad,
            R.raw.song_theme,
            R.raw.video_choji,
            "Choji",
            mapOf("Taijutsu" to 40, "Jutsu der Teilentfaltung" to 50, "Fleischbombenpanzer" to 60),
            mapOf("Kunai" to 15, "Shuriken" to 20),
            mapOf("Schmetterlingsflügel" to 80),
            mapOf("Jutsu des Tausches" to 20, "Schattendoppelgänger" to 35),
            500, 500
        ),

        CharacterForFight(
            R.drawable.ino,
            R.drawable.ino_pose,
            R.drawable.ino_face,
            R.drawable.ino_attack,
            R.drawable.ino_sad,
            R.raw.song_theme,
            R.raw.video_ino,
            "Ino",
            mapOf("Taijutsu" to 40, "Schlafbomben-Jutsu" to 50, "Gedankenkontroll-Jutsu" to 70),
            mapOf("Kunai" to 15, "Shuriken" to 20),
            mapOf("Heilung" to 80),
            mapOf("Jutsu des Tausches" to 20, "Schattendoppelgänger" to 35),
            500, 500
        ),

        CharacterForFight(
            R.drawable.neji,
            R.drawable.neji_pose,
            R.drawable.neji_face,
            R.drawable.neji_attack,
            R.drawable.neji_sad,
            R.raw.song_neji,
            R.raw.video_neji,
            "Neji",
            mapOf("Taijutsu" to 50, "Juuken" to 50, "Rotation" to 60, "64 Hände" to 75),
            mapOf("Kunai" to 15, "Shuriken" to 20),
            mapOf("Byakugan" to 70),
            mapOf("Jutsu des Tausches" to 20, "Schattendoppelgänger" to 35),
            500, 500
        ),

        CharacterForFight(
            R.drawable.gaara,
            R.drawable.gaara_pose,
            R.drawable.gaara_face,
            R.drawable.gaara_attack,
            R.drawable.gaara_sad,
            R.raw.song_gaara,
            R.drawable.gaara_attack,
            "Gaara",
            mapOf("Taijutsu" to 20, "Jiton" to 60, "Wüstensarg" to 80),
            mapOf("Kunai" to 15, "Eisensand" to 40),
            mapOf("Shukaku erwecken" to 100),
            mapOf("Jutsu des Tausches" to 20, "Sanddoppelgänger" to 35, "Sandschild" to 60),
            500, 500
        ),
    )
}