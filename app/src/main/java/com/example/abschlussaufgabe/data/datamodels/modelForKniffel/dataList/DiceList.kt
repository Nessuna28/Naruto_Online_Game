package com.example.abschlussaufgabe.data.datamodels.modelForKniffel.dataList

import com.example.abschlussaufgabe.R
import com.example.abschlussaufgabe.data.datamodels.modelForKniffel.Dice
import com.example.abschlussaufgabe.data.datamodels.modelForKniffel.DiceSide

class DiceList {

    val diceList = listOf(
        Dice("Team Kakashi",
            DiceSide(R.drawable.kakashi_face, 1, false),
            DiceSide(R.drawable.naruto_face, 2, false),
            DiceSide(R.drawable.sasuke_face, 3, false),
            DiceSide(R.drawable.sakura_face, 4, false),
            DiceSide(R.drawable.sai_face, 5, false),
            DiceSide(R.drawable.kurama, 6, false)
        ),

        Dice("Team Gaara",
            DiceSide(R.drawable.gaara_face, 1,false),
            DiceSide(R.drawable.temari_face, 2, false),
            DiceSide(R.drawable.kankuro_face, 3, false),
            DiceSide(R.drawable.marionette, 4, false),
            DiceSide(R.drawable.subjects, 5, false),
            DiceSide(R.drawable.shukaku, 6, false)
        )
    )
}