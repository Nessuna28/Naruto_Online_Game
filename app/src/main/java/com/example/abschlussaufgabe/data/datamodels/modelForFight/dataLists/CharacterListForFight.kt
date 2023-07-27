package com.example.abschlussaufgabe.data.datamodels.modelForFight.dataLists

import com.example.abschlussaufgabe.R
import com.example.abschlussaufgabe.data.datamodels.modelForFight.CharacterForFight
import com.example.abschlussaufgabe.data.datamodels.modelForFight.Defense
import com.example.abschlussaufgabe.data.datamodels.modelForFight.Jutsu
import com.example.abschlussaufgabe.data.datamodels.modelForFight.Tool
import com.example.abschlussaufgabe.data.datamodels.modelForFight.UniqueTrait

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
        listOf(Jutsu("Taijutsu", 20, R.drawable.no_picture), Jutsu("Rasengan", 50, R.drawable.no_picture), Jutsu("Rasenshuriken", 70, R.drawable.no_picture), Jutsu("Jutsu des vertrauten Geistes", 80, R.drawable.no_picture)),
        listOf(Tool("Kunai", 15, R.drawable.no_picture), Tool("Shuriken", 20, R.drawable.no_picture)),
        listOf(UniqueTrait("Weisenmodus", 80, R.drawable.no_picture), UniqueTrait("Kiubimodus", 100, R.drawable.no_picture)),
        listOf(Defense("Jutsu des Tausches", 20, R.drawable.no_picture), Defense("Schattendoppelgänger", 35, R.drawable.no_picture)),
            500, 500),

    CharacterForFight(
        R.drawable.sasuke,
        R.drawable.sasuke_pose,
        R.drawable.sasuke_face,
        R.drawable.sasuke_attack,
        R.drawable.sasuke_sad,
        R.raw.song_sasuke,
        R.drawable.sasuke_attack,
        "Sasuke",
        listOf(Jutsu("Taijutsu", 30, R.drawable.no_picture), Jutsu("Feuerversteck", 40, R.drawable.no_picture), Jutsu("Chidori", 50, R.drawable.no_picture), Jutsu("Jutsu des vertrauten Geistes", 80, R.drawable.no_picture)),
        listOf(Tool("Shuriken", 20, R.drawable.no_picture), Tool("Schwert", 30, R.drawable.no_picture)),
        listOf(
            UniqueTrait("Susanoo", 100, R.drawable.no_picture)),
        listOf(
            Defense("Jutsu des Tausches", 20, R.drawable.no_picture), Defense("Schattendoppelgänger", 35, R.drawable.no_picture)),
        500, 500),

    CharacterForFight(
        R.drawable.sakura,
        R.drawable.sakura_pose,
        R.drawable.sakura_face,
        R.drawable.sakura_attack,
        R.drawable.sakura_sad,
        R.raw.song_sakura,
        R.raw.video_sakura,
        "Sakura",
        listOf(Jutsu("Taijutsu", 15, R.drawable.no_picture), Jutsu("große Sakura", 20, R.drawable.no_picture), Jutsu("Kirschblütenschlag", 50, R.drawable.no_picture), Jutsu("Kirschblütenformation", 70, R.drawable.no_picture), Jutsu("Jutsu des vertrauten Geistes", 80, R.drawable.no_picture)),
        listOf(Tool("Kunai", 15, R.drawable.no_picture), Tool("Shuriken", 20, R.drawable.no_picture)),
        listOf(UniqueTrait("Jahundertstärke", 90, R.drawable.no_picture), UniqueTrait("Heilung", 100, R.drawable.no_picture)),
        listOf(Defense("Jutsu des Tausches", 20, R.drawable.no_picture), Defense("Schattendoppelgänger", 35, R.drawable.no_picture)),
        500, 500),

        CharacterForFight(
            R.drawable.shikamaru,
            R.drawable.shikamaru_pose,
            R.drawable.shikamaru_face,
            R.drawable.shikamaru_attack,
            R.drawable.shikamaru_sad,
            R.raw.song_shikamaru,
            R.raw.video_shikamaru,
            "Shikamaru",
            listOf(Jutsu("Taijutsu", 30, R.drawable.no_picture), Jutsu("Schattenfesseln", 55, R.drawable.no_picture), Jutsu("Schattenwürg", 65, R.drawable.no_picture), Jutsu("Schattenzug", 70, R.drawable.no_picture)),
            listOf(Tool("Kunai", 15, R.drawable.no_picture), Tool("Explosionskunai", 30, R.drawable.no_picture)),
            listOf(UniqueTrait("Strategie", 70, R.drawable.no_picture)),
            listOf(Defense("Jutsu des Tausches", 20, R.drawable.no_picture), Defense("Schattendoppelgänger", 35, R.drawable.no_picture)),
            500, 500),

        CharacterForFight(
            R.drawable.choji,
            R.drawable.choji_pose,
            R.drawable.choji_face,
            R.drawable.choji_attack,
            R.drawable.choji_sad,
            R.raw.song_theme,
            R.raw.video_choji,
            "Choji",
            listOf(Jutsu("Taijutsu", 40, R.drawable.no_picture), Jutsu("Jutsu der Teilentfaltung", 50, R.drawable.no_picture), Jutsu("Fleischbombenpanzer", 60, R.drawable.no_picture)),
            listOf(Tool("Kunai", 15, R.drawable.no_picture), Tool("Shuriken", 20, R.drawable.no_picture)),
            listOf(UniqueTrait("Schmetterlingsflügel", 80, R.drawable.no_picture)),
            listOf(Defense("Jutsu des Tausches", 20, R.drawable.no_picture), Defense("Schattendoppelgänger", 35, R.drawable.no_picture)),
            500, 500),

        CharacterForFight(
            R.drawable.ino,
            R.drawable.ino_pose,
            R.drawable.ino_face,
            R.drawable.ino_attack,
            R.drawable.ino_sad,
            R.raw.song_theme,
            R.raw.video_ino,
            "Ino",
            listOf(Jutsu("Taijutsu", 40, R.drawable.no_picture), Jutsu("Schlafbomben-Jutsu", 50, R.drawable.no_picture), Jutsu("Gedankenkontroll-Jutsu", 70, R.drawable.no_picture)),
            listOf(Tool("Kunai", 15, R.drawable.no_picture), Tool("Shuriken", 20, R.drawable.no_picture)),
            listOf(UniqueTrait("Heilung", 80, R.drawable.no_picture)),
            listOf(Defense("Jutsu des Tausches", 20, R.drawable.no_picture), Defense("Schattendoppelgänger", 35, R.drawable.no_picture)),
            500, 500),

        CharacterForFight(
            R.drawable.neji,
            R.drawable.neji_pose,
            R.drawable.neji_face,
            R.drawable.neji_attack,
            R.drawable.neji_sad,
            R.raw.song_neji,
            R.raw.video_neji,
            "Neji",
            listOf(Jutsu("Taijutsu", 50, R.drawable.no_picture), Jutsu("Juuken", 50, R.drawable.no_picture), Jutsu("64 Hände", 75, R.drawable.no_picture)),
            listOf(Tool("Kunai", 15, R.drawable.no_picture), Tool("Shuriken", 20, R.drawable.no_picture)),
            listOf(UniqueTrait("Byakugan", 70, R.drawable.no_picture)),
            listOf(Defense("Jutsu des Tausches", 20, R.drawable.no_picture), Defense("Schattendoppelgänger", 35, R.drawable.no_picture), Defense("Rotation", 60, R.drawable.no_picture)),
            500, 50),

        CharacterForFight(
            R.drawable.gaara,
            R.drawable.gaara_pose,
            R.drawable.gaara_face,
            R.drawable.gaara_attack,
            R.drawable.gaara_sad,
            R.raw.song_gaara,
            R.drawable.gaara_attack,
            "Gaara",
            listOf(Jutsu("Taijutsu", 20, R.drawable.no_picture), Jutsu("Jiton", 60, R.drawable.no_picture), Jutsu("Wüstensarg", 80, R.drawable.no_picture)),
            listOf(Tool("Kunai", 15, R.drawable.no_picture), Tool("Shuriken", 20, R.drawable.no_picture), Tool("Eisensand", 40, R.drawable.no_picture)),
            listOf(UniqueTrait("Shukaku erwecken", 100, R.drawable.no_picture)),
            listOf(Defense("Jutsu des Tausches", 20, R.drawable.no_picture), Defense("Sanddoppelgänger", 35, R.drawable.no_picture), Defense("Sandschild", 60, R.drawable.no_picture)),
            500, 500),
    )
}