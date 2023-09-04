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
            R.drawable.naruto_player,
            R.drawable.naruto_pose,
            R.drawable.naruto_face2,
            R.drawable.naruto_sad,
            R.raw.song_naruto,
            R.raw.video_naruto,
    "Naruto",
        listOf(Jutsu("Taijutsu", 20, R.drawable.naruto_attack_player, R.drawable.naruto_attack_enemy), Jutsu("Rasengan", 50, R.drawable.naruto_attack_rasengan_player, R.drawable.naruto_attack_rasengan_enemy), Jutsu("Rasenshuriken", 70, R.drawable.naruto_attack_rasenshuriken_player, R.drawable.naruro_attack_rasenshuriken_enemy), Jutsu("Jutsu des vertrauten Geistes", 80, R.drawable.naruto_attack_vertrauter_geist, R.drawable.naruto_attack_vertrauter_geist)),
        listOf(Tool("Kunai", 15, R.drawable.kunai_player, R.drawable.kunai_enemy), Tool("Shuriken", 20, R.drawable.shuriken, R.drawable.shuriken)),
        listOf(UniqueTrait("Weisenmodus", 80, R.drawable.naruto_trait_weisenmodus, R.drawable.naruto_trait_weisenmodus), UniqueTrait("Kiubimodus", 100, R.drawable.naruto_trait_kiubimodus_player, R.drawable.naruto_trait_kiubimodus_enemy)),
        listOf(Defense("Jutsu des Tausches", 20, R.drawable.baumstamm, R.drawable.baumstamm), Defense("Schattendoppelgänger", 35, R.drawable.naruto_defense_schattendoppel_player, R.drawable.naruto_defense_schattendoppel_enemy)),),

    CharacterForFight(
        R.drawable.sasuke_player,
        R.drawable.sasuke_pose,
        R.drawable.sasuke_face2,
        R.drawable.sasuke_sad,
        R.raw.song_sasuke,
        R.raw.video_naruto,
        "Sasuke",
        listOf(Jutsu("Taijutsu", 30, R.drawable.sasuke_attack2_player, R.drawable.sasuke_attack2_enemy), Jutsu("Feuerversteck", 40, R.drawable.sasuke_attack_jutsu_feuer_player, R.drawable.sasuke_attack_jutsu_feuer_enemy), Jutsu("Chidori", 50, R.drawable.sasuke_attack_chidori_player, R.drawable.sasuke_attack_chidori_enemy), Jutsu("Jutsu des vertrauten Geistes", 80, R.drawable.sasuke_attack_vertrauter_geist, R. drawable.sasuke_attack_vertrauter_geist)),
        listOf(Tool("Kunai", 15, R.drawable.kunai_player, R.drawable.kunai_enemy), Tool("Shuriken", 20, R.drawable.shuriken, R.drawable.shuriken), Tool("Schwert", 30, R.drawable.sasuke_attack_sword_player, R.drawable.sasuke_attack_sword_enemy)),
        listOf(UniqueTrait("Sharingan", 100, R.drawable.sasuke_trait_sharingan_player, R.drawable.sasuke_trait_sharingan_enemy), UniqueTrait("Susanoo", 100, R.drawable.sasuke_trait_soosano_player, R.drawable.sasuke_trait_soosano_enemy)),
        listOf(Defense("Jutsu des Tausches", 20, R.drawable.baumstamm, R.drawable.baumstamm), Defense("Schattendoppelgänger", 35, R.drawable.sasuke_attack_player, R.drawable.sasuke_attack_enemy)),),

    CharacterForFight(
        R.drawable.sakura_player,
        R.drawable.sakura_pose,
        R.drawable.sakura_face2,
        R.drawable.sakura_sad,
        R.raw.song_sakura,
        R.raw.video_sakura,
        "Sakura",
        listOf(Jutsu("Taijutsu", 15, R.drawable.sakura_attack_player, R.drawable.sakura_attack_enemy), Jutsu("große Sakura", 20, R.drawable.sakura_attack_player, R.drawable.sakura_attack_enemy), Jutsu("Kirschblütenschlag", 50, R.drawable.sakura_attack2_player, R.drawable.sakura_attack2_enemy), Jutsu("Kirschblütenformation", 70, R.drawable.sakura_attack2_player, R.drawable.sakura_attack2_enemy), Jutsu("Jutsu des vertrauten Geistes", 80, R.drawable.sakura_attack_vertrauter_geist, R.drawable.sakura_attack_vertrauter_geist)),
        listOf(Tool("Kunai", 15, R.drawable.kunai_player, R.drawable.kunai_enemy), Tool("Shuriken", 20, R.drawable.shuriken, R.drawable.shuriken)),
        listOf(UniqueTrait("Jahundertstärke", 90, R.drawable.sakura_trait_hundertstark, R.drawable.sakura_trait_hundertstark), UniqueTrait("Heilung", 100, R.drawable.heal, R.drawable.heal)),
        listOf(Defense("Jutsu des Tausches", 20, R.drawable.baumstamm, R.drawable.baumstamm), Defense("Schattendoppelgänger", 35, R.drawable.sakura_player, R.drawable.sakura_enemy)),),

        CharacterForFight(
            R.drawable.shikamaru_player,
            R.drawable.shikamaru_pose,
            R.drawable.shikamaru_face2,
            R.drawable.shikamaru_sad,
            R.raw.song_shikamaru,
            R.raw.video_shikamaru,
            "Shikamaru",
            listOf(Jutsu("Taijutsu", 30, R.drawable.shikamaru_attack_player, R.drawable.shikamaru_attack_enemy), Jutsu("Schattenfesseln", 55, R.drawable.shikamaru_attack_jutsu_player, R.drawable.shikamaru_attack_jutsu_enemy), Jutsu("Schattenwürg", 65, R.drawable.shikamaru_attack_jutsu2_player, R.drawable.shikamaru_attack_jutsu2_enemy), Jutsu("Schattenzug", 70, R.drawable.shikamaru_attack_jutsu2_player, R.drawable.shikamaru_attack_jutsu2_enemy)),
            listOf(Tool("Kunai", 15, R.drawable.kunai_player, R.drawable.kunai_enemy), Tool("Shuriken", 20, R.drawable.shuriken, R.drawable.shuriken), Tool("Explosionskunai", 30, R.drawable.explosion_map, R.drawable.explosion_map)),
            listOf(UniqueTrait("Strategie", 70, R.drawable.shikamaru_trait_player, R.drawable.shikamaru_trait_enemy)),
            listOf(Defense("Jutsu des Tausches", 20, R.drawable.baumstamm, R.drawable.baumstamm), Defense("Schattendoppelgänger", 35, R.drawable.shikamaru_player, R.drawable.shikamaru_enemy)),),

        CharacterForFight(
            R.drawable.choji_player,
            R.drawable.choji_pose,
            R.drawable.choji_face2,
            R.drawable.choji_sad,
            R.raw.song_theme,
            R.raw.video_choji,
            "Choji",
            listOf(Jutsu("Taijutsu", 40, R.drawable.choji_attack_player, R.drawable.choji_attack_enemy), Jutsu("Jutsu der Teilentfaltung", 50, R.drawable.choji_attack_jutsu_player, R.drawable.choji_attack_jutsu_enemy), Jutsu("Fleischbombenpanzer", 60, R.drawable.choji_attack_jutsu2, R.drawable.choji_attack_jutsu2)),
            listOf(Tool("Kunai", 15, R.drawable.kunai_player, R.drawable.kunai_enemy), Tool("Shuriken", 20, R.drawable.shuriken, R.drawable.shuriken)),
            listOf(UniqueTrait("Schmetterlingsflügel", 80, R.drawable.choji_trait, R.drawable.choji_trait)),
            listOf(Defense("Jutsu des Tausches", 20, R.drawable.baumstamm, R.drawable.baumstamm), Defense("Schattendoppelgänger", 35, R.drawable.choji_player, R.drawable.choji_enemy)),),

        CharacterForFight(
            R.drawable.ino_player,
            R.drawable.ino_pose,
            R.drawable.ino_face2,
            R.drawable.ino_sad,
            R.raw.song_theme,
            R.raw.video_ino,
            "Ino",
            listOf(Jutsu("Taijutsu", 40, R.drawable.ino_attack_player, R.drawable.ino_attack_enemy), Jutsu("Schlafbomben-Jutsu", 50, R.drawable.ino_attack_jutsu_player, R.drawable.ino_attack_jutsu_enemy), Jutsu("Gedankenkontroll-Jutsu", 70, R.drawable.ino_attack_jutsu2_player, R.drawable.ino_attack_jutsu2_enemy)),
            listOf(Tool("Kunai", 15, R.drawable.kunai_player, R.drawable.kunai_enemy), Tool("Shuriken", 20, R.drawable.shuriken, R.drawable.shuriken)),
            listOf(UniqueTrait("Heilung", 80, R.drawable.heal, R.drawable.heal)),
            listOf(Defense("Jutsu des Tausches", 20, R.drawable.baumstamm, R.drawable.baumstamm), Defense("Schattendoppelgänger", 35, R.drawable.ino_player, R.drawable.ino_enemy)),),

        CharacterForFight(
            R.drawable.neji_player,
            R.drawable.neji_pose,
            R.drawable.neji_face2,
            R.drawable.neji_sad,
            R.raw.song_neji,
            R.raw.video_neji,
            "Neji",
            listOf(Jutsu("Taijutsu", 50, R.drawable.neji_attack_player, R.drawable.neji_attack_enemy), Jutsu("Juuken", 50, R.drawable.neji_attack_player, R.drawable.neji_attack_enemy), Jutsu("64 Hände", 75, R.drawable.neji_attack_jutsu_player, R.drawable.neji_attack_jutsu_enemy)),
            listOf(Tool("Kunai", 15, R.drawable.kunai_player, R.drawable.kunai_enemy), Tool("Shuriken", 20, R.drawable.shuriken, R.drawable.shuriken)),
            listOf(UniqueTrait("Byakugan", 70, R.drawable.neji_byakugan, R.drawable.neji_byakugan)),
            listOf(Defense("Jutsu des Tausches", 20, R.drawable.baumstamm, R.drawable.baumstamm), Defense("Schattendoppelgänger", 35, R.drawable.neji_player, R.drawable.neji_enemy), Defense("Rotation", 60, R.drawable.neji_defense_rotation_player, R.drawable.neji_defense_rotation_enemy)),),

        CharacterForFight(
            R.drawable.tenten_player,
            R.drawable.tenten_pose,
            R.drawable.tenten_face2,
            R.drawable.tenten_sad,
            R.raw.song_theme,
            R.raw.video_naruto,
            "Tenten",
            listOf(Jutsu("Taijutsu", 20, R.drawable.tenten_attack_jutsu_player, R.drawable.tenten_attack_jutsu_enemy), Jutsu("Riesiger Eisenball", 60, R.drawable.tenten_attack_jutsu2_player, R.drawable.tenten_attack_jutsu2_enemy), Jutsu("Chaos der zehntausend Himmelsklingen", 60, R.drawable.tenten_attack_jutsu2_player, R.drawable.tenten_attack_jutsu2_enemy)),
            listOf(Tool("Kunai", 15, R.drawable.kunai_player, R.drawable.kunai_enemy), Tool("Shuriken", 20, R.drawable.shuriken, R.drawable.shuriken)),
            listOf(UniqueTrait("", 0, R.drawable.tenten_trait_player, R.drawable.tenten_trait_enemy)),
            listOf(Defense("Jutsu des Tausches", 20, R.drawable.baumstamm, R.drawable.baumstamm), Defense("Schattendoppelgänger", 35, R.drawable.tenten_player, R.drawable.tenten_enemy)),),

        CharacterForFight(
            R.drawable.rocklee_player,
            R.drawable.rocklee_pose,
            R.drawable.rocklee_face2,
            R.drawable.rocklee_sad,
            R.raw.song_theme,
            R.raw.video_naruto,
            "Rock Lee",
            listOf(Jutsu("Taijutsu", 50, R.drawable.rocklee_attack_player, R.drawable.rocklee_attack_enemy), Jutsu("Konah Wirbelwind", 60, R.drawable.rocklee_attack2_player, R.drawable.rocklee_attack2_enemy), Jutsu("Blüte der Jugend volle Kraft", 65, R.drawable.rocklee_attack_player, R.drawable.rocklee_attack_enemy), Jutsu("Frontallotus", 75, R.drawable.rocklee_attack_lotus_player, R.drawable.rocklee_attack_lotus_enemy)),
            listOf(Tool("Kunai", 15, R.drawable.kunai_player, R.drawable.kunai_enemy), Tool("Shuriken", 20, R.drawable.shuriken, R.drawable.shuriken)),
            listOf(UniqueTrait("sechs inneren Tore", 80, R.drawable.rocklee_trait_player, R.drawable.rocklee_trait_enemy)),
            listOf(Defense("blocken", 10, R.drawable.rocklee_defense_player, R.drawable.rocklee_defense_enemy)),),


        CharacterForFight(
            R.drawable.gaara_player,
            R.drawable.gaara_pose,
            R.drawable.gaara_face2,
            R.drawable.gaara_sad,
            R.raw.song_gaara,
            R.raw.video_naruto,
            "Gaara",
            listOf(Jutsu("Taijutsu", 20, R.drawable.gaara_attack_player, R.drawable.gaara_attack_enemy), Jutsu("Jiton", 60, R.drawable.gaara_attack_jutsu_player, R.drawable.gaara_attack_jutsu_enemy), Jutsu("Wüstensarg", 80, R.drawable.gaara_attack_jutsu2_player, R.drawable.gaara_attack_jutsu2_enemy)),
            listOf(Tool("Kunai", 15, R.drawable.kunai_player, R.drawable.kunai_enemy), Tool("Shuriken", 20, R.drawable.shuriken, R.drawable.shuriken)),
            listOf(UniqueTrait("Eisensand", 40, R.drawable.gaara_attack_eisensand_player, R.drawable.gaara_attack_eisensand_enemy)),
            listOf(Defense("Jutsu des Tausches", 20, R.drawable.baumstamm, R.drawable.baumstamm), Defense("Sanddoppelgänger", 35, R.drawable.gaara_player, R.drawable.gaara_enemy), Defense("Sandschild", 60, R.drawable.gaara_defense, R.drawable.gaara_defense)),),
    )
}