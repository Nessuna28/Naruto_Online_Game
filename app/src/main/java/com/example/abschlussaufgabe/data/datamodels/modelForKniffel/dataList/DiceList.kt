package com.example.abschlussaufgabe.data.datamodels.modelForKniffel.dataList

import com.example.abschlussaufgabe.R
import com.example.abschlussaufgabe.data.datamodels.modelForKniffel.Dice
import com.example.abschlussaufgabe.data.datamodels.modelForKniffel.DiceSide

class DiceList {

    val diceList = listOf(
        Dice("Team Kakashi",
            listOf(DiceSide(R.drawable.kakashi_face, 1, false),
                DiceSide(R.drawable.naruto_face, 2, false),
                DiceSide(R.drawable.sasuke_face, 3, false),
                DiceSide(R.drawable.sakura_face, 4, false),
                DiceSide(R.drawable.sai_face, 5, false),
                DiceSide(R.drawable.kurama, 6, false))
        ),

        Dice("Team Asuma",
            listOf(DiceSide(R.drawable.asuma_face, 1,false),
                DiceSide(R.drawable.shikamaru_face, 2, false),
                DiceSide(R.drawable.ino_face, 3, false),
                DiceSide(R.drawable.choji_face, 4, false),
                DiceSide(R.drawable.chakraklinge, 5, false),
                DiceSide(R.drawable.explosion_map, 6, false))
        ),

        Dice("Team Kurenai",
            listOf(DiceSide(R.drawable.kurenai_face, 1,false),
                DiceSide(R.drawable.shino_face, 2, false),
                DiceSide(R.drawable.kiba_face, 3, false),
                DiceSide(R.drawable.hinata_face, 4, false),
                DiceSide(R.drawable.insects, 5, false),
                DiceSide(R.drawable.akamaru, 6, false))
        ),

        Dice("Team Maito Gai",
            listOf(DiceSide(R.drawable.gai_face, 1,false),
                DiceSide(R.drawable.neji_face, 2, false),
                DiceSide(R.drawable.tenten_face, 3, false),
                DiceSide(R.drawable.rocklee_face, 4, false),
                DiceSide(R.drawable.scrolls, 5, false),
                DiceSide(R.drawable.green_beast, 6, false))
        ),

        Dice("Team Gaara",
            listOf(DiceSide(R.drawable.gaara_face, 1,false),
                DiceSide(R.drawable.temari_face, 2, false),
                DiceSide(R.drawable.kankuro_face, 3, false),
                DiceSide(R.drawable.marionette, 4, false),
                DiceSide(R.drawable.subjects, 5, false),
                DiceSide(R.drawable.shukaku, 6, false))
        ),


    )
}