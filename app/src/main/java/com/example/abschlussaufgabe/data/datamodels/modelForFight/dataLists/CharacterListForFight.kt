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
            R.drawable.naruto_face2,
            R.drawable.naruto_attack,
            R.drawable.naruto_sad,
            R.raw.song_naruto,
            R.raw.video_naruto,
    "Naruto",
        listOf(Jutsu("Taijutsu", 20, R.drawable.naruto_attack), Jutsu("Rasengan", 50, R.drawable.no_picture), Jutsu("Rasenshuriken", 70, R.drawable.no_picture), Jutsu("Jutsu des vertrauten Geistes", 80, R.drawable.no_picture)),
        listOf(Tool("Kunai", 15, R.drawable.kunai_player), Tool("Shuriken", 20, R.drawable.shuriken)),
        listOf(UniqueTrait("Weisenmodus", 80, R.drawable.no_picture), UniqueTrait("Kiubimodus", 100, R.drawable.no_picture)),
        listOf(Defense("Jutsu des Tausches", 20, R.drawable.baumstamm), Defense("Schattendoppelgänger", 35, R.drawable.naruto)),),

    CharacterForFight(
        R.drawable.sasuke,
        R.drawable.sasuke_pose,
        R.drawable.sasuke_face2,
        R.drawable.sasuke_attack,
        R.drawable.sasuke_sad,
        R.raw.song_sasuke,
        R.raw.video_naruto,
        "Sasuke",
        listOf(Jutsu("Taijutsu", 30, R.drawable.sasuke_attack), Jutsu("Feuerversteck", 40, R.drawable.no_picture), Jutsu("Chidori", 50, R.drawable.no_picture), Jutsu("Jutsu des vertrauten Geistes", 80, R.drawable.no_picture)),
        listOf(Tool("Shuriken", 20, R.drawable.shuriken), Tool("Schwert", 30, R.drawable.no_picture)),
        listOf(
            UniqueTrait("Susanoo", 100, R.drawable.no_picture)),
        listOf(
            Defense("Jutsu des Tausches", 20, R.drawable.baumstamm), Defense("Schattendoppelgänger", 35, R.drawable.sasuke_attack)),),

    CharacterForFight(
        R.drawable.sakura,
        R.drawable.sakura_pose,
        R.drawable.sakura_face2,
        R.drawable.sakura_attack,
        R.drawable.sakura_sad,
        R.raw.song_sakura,
        R.raw.video_sakura,
        "Sakura",
        listOf(Jutsu("Taijutsu", 15, R.drawable.sakura_attack), Jutsu("große Sakura", 20, R.drawable.sakura_attack), Jutsu("Kirschblütenschlag", 50, R.drawable.sakura_attack), Jutsu("Kirschblütenformation", 70, R.drawable.sakura_attack), Jutsu("Jutsu des vertrauten Geistes", 80, R.drawable.no_picture)),
        listOf(Tool("Kunai", 15, R.drawable.no_picture), Tool("Shuriken", 20, R.drawable.shuriken)),
        listOf(UniqueTrait("Jahundertstärke", 90, R.drawable.sakura_attack), UniqueTrait("Heilung", 100, R.drawable.heal)),
        listOf(Defense("Jutsu des Tausches", 20, R.drawable.baumstamm), Defense("Schattendoppelgänger", 35, R.drawable.sakura)),),

        CharacterForFight(
            R.drawable.shikamaru,
            R.drawable.shikamaru_pose,
            R.drawable.shikamaru_face2,
            R.drawable.shikamaru_attack,
            R.drawable.shikamaru_sad,
            R.raw.song_shikamaru,
            R.raw.video_shikamaru,
            "Shikamaru",
            listOf(Jutsu("Taijutsu", 30, R.drawable.no_picture), Jutsu("Schattenfesseln", 55, R.drawable.shikamaru_attack), Jutsu("Schattenwürg", 65, R.drawable.shikamaru_attack), Jutsu("Schattenzug", 70, R.drawable.shikamaru_attack)),
            listOf(Tool("Kunai", 15, R.drawable.kunai_player), Tool("Explosionskunai", 30, R.drawable.no_picture)),
            listOf(UniqueTrait("Strategie", 70, R.drawable.no_picture)),
            listOf(Defense("Jutsu des Tausches", 20, R.drawable.baumstamm), Defense("Schattendoppelgänger", 35, R.drawable.shikamaru)),),

        CharacterForFight(
            R.drawable.choji,
            R.drawable.choji_pose,
            R.drawable.choji_face2,
            R.drawable.choji_attack,
            R.drawable.choji_sad,
            R.raw.song_theme,
            R.raw.video_choji,
            "Choji",
            listOf(Jutsu("Taijutsu", 40, R.drawable.choji_attack), Jutsu("Jutsu der Teilentfaltung", 50, R.drawable.choji_attack), Jutsu("Fleischbombenpanzer", 60, R.drawable.no_picture)),
            listOf(Tool("Kunai", 15, R.drawable.kunai_player), Tool("Shuriken", 20, R.drawable.shuriken)),
            listOf(UniqueTrait("Schmetterlingsflügel", 80, R.drawable.no_picture)),
            listOf(Defense("Jutsu des Tausches", 20, R.drawable.baumstamm), Defense("Schattendoppelgänger", 35, R.drawable.choji)),),

        CharacterForFight(
            R.drawable.ino,
            R.drawable.ino_pose,
            R.drawable.ino_face2,
            R.drawable.ino_attack,
            R.drawable.ino_sad,
            R.raw.song_theme,
            R.raw.video_ino,
            "Ino",
            listOf(Jutsu("Taijutsu", 40, R.drawable.ino_attack), Jutsu("Schlafbomben-Jutsu", 50, R.drawable.no_picture), Jutsu("Gedankenkontroll-Jutsu", 70, R.drawable.no_picture)),
            listOf(Tool("Kunai", 15, R.drawable.kunai_player), Tool("Shuriken", 20, R.drawable.shuriken)),
            listOf(UniqueTrait("Heilung", 80, R.drawable.no_picture)),
            listOf(Defense("Jutsu des Tausches", 20, R.drawable.baumstamm), Defense("Schattendoppelgänger", 35, R.drawable.ino)),),

        CharacterForFight(
            R.drawable.neji,
            R.drawable.neji_pose,
            R.drawable.neji_face2,
            R.drawable.neji_attack,
            R.drawable.neji_sad,
            R.raw.song_neji,
            R.raw.video_neji,
            "Neji",
            listOf(Jutsu("Taijutsu", 50, R.drawable.neji_attack), Jutsu("Juuken", 50, R.drawable.neji_attack), Jutsu("64 Hände", 75, R.drawable.neji_attack)),
            listOf(Tool("Kunai", 15, R.drawable.kunai_player), Tool("Shuriken", 20, R.drawable.shuriken)),
            listOf(UniqueTrait("Byakugan", 70, R.drawable.neji_byakugan)),
            listOf(Defense("Jutsu des Tausches", 20, R.drawable.baumstamm), Defense("Schattendoppelgänger", 35, R.drawable.neji), Defense("Rotation", 60, R.drawable.neji_eight_trigrams)),),

        CharacterForFight(
            R.drawable.tenten,
            R.drawable.tenten_pose,
            R.drawable.tenten_face2,
            R.drawable.tenten_attack,
            R.drawable.tenten_sad,
            R.raw.song_theme,
            R.raw.video_naruto,
            "Tenten",
            listOf(Jutsu("Taijutsu", 20, R.drawable.tenten_attack_jutsu), Jutsu("Riesiger Eisenball", 60, R.drawable.tenten_attack), Jutsu("Chaos der zehntausend Himmelsklingen", 60, R.drawable.tenten_attack)),
            listOf(Tool("Kunai", 15, R.drawable.kunai_player), Tool("Shuriken", 20, R.drawable.shuriken)),
            listOf(UniqueTrait("", 0, R.drawable.no_picture)),
            listOf(Defense("Jutsu des Tausches", 20, R.drawable.baumstamm), Defense("Schattendoppelgänger", 35, R.drawable.tenten)),),

        CharacterForFight(
            R.drawable.rocklee,
            R.drawable.rocklee_pose,
            R.drawable.rocklee_face2,
            R.drawable.rocklee_attack,
            R.drawable.rocklee_sad,
            R.raw.song_theme,
            R.raw.video_naruto,
            "Rock Lee",
            listOf(Jutsu("Taijutsu", 50, R.drawable.rocklee_attack), Jutsu("Konah Wirbelwind", 60, R.drawable.rocklee_attack), Jutsu("Blüte der Jugend volle Kraft", 65, R.drawable.rocklee_attack), Jutsu("Frontallotus", 75, R.drawable.rocklee_attack_lotus)),
            listOf(Tool("Kunai", 15, R.drawable.kunai_player), Tool("Shuriken", 20, R.drawable.shuriken)),
            listOf(UniqueTrait("sechs inneren Tore", 80, R.drawable.rocklee_trait)),
            listOf(Defense("blocken", 10, R.drawable.rocklee_defense)),),


        CharacterForFight(
            R.drawable.gaara,
            R.drawable.gaara_pose,
            R.drawable.gaara_face2,
            R.drawable.gaara_attack,
            R.drawable.gaara_sad,
            R.raw.song_gaara,
            R.raw.video_naruto,
            "Gaara",
            listOf(Jutsu("Taijutsu", 20, R.drawable.no_picture), Jutsu("Jiton", 60, R.drawable.gaara_attack), Jutsu("Wüstensarg", 80, R.drawable.gaara_attack)),
            listOf(Tool("Kunai", 15, R.drawable.kunai_player), Tool("Shuriken", 20, R.drawable.shuriken), Tool("Eisensand", 40, R.drawable.gaara_attack2)),
            listOf(UniqueTrait("Shukaku erwecken", 100, R.drawable.no_picture)),
            listOf(Defense("Jutsu des Tausches", 20, R.drawable.baumstamm), Defense("Sanddoppelgänger", 35, R.drawable.gaara), Defense("Sandschild", 60, R.drawable.sandschild)),),
    )
}