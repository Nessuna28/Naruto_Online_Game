package com.example.abschlussaufgabe.data.datamodels.modelForKniffel.dataList

import com.example.abschlussaufgabe.R
import com.example.abschlussaufgabe.data.datamodels.modelForKniffel.Dice
import com.example.abschlussaufgabe.data.datamodels.modelForKniffel.DiceSide

class DiceList {

    val diceList = listOf(
        Dice("Team Kakashi",
            DiceSide(R.drawable.kakashi_face, 1),
            DiceSide(R.drawable.naruto_face, 2),
            DiceSide(R.drawable.sasuke_face, 3),
            DiceSide(R.drawable.sakura_face, 4),
            DiceSide(R.drawable.sai_face, 5),
            DiceSide(R.drawable.kurama, 6)
        ),

        Dice("Team Gaara",
            DiceSide(R.drawable.gaara_face, 1),
            DiceSide(R.drawable.temari_face, 2),
            DiceSide(R.drawable.kankuro_face, 3),
            DiceSide(R.drawable.marionette, 4),
            DiceSide(R.drawable.subjects, 5),
            DiceSide(R.drawable.subjects, 5)
        )
    )
}