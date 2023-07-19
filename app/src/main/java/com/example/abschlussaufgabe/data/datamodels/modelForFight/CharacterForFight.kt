package com.example.abschlussaufgabe.data.datamodels.modelForFight


data class CharacterForFight(

    val image: Int,
    val image2: Int,
    val name: String,
    var jutsus: Map<String, Int>,
    val tools: Map<String, Int>,
    val uniqueTraits: Map<String, Int>,
    val defense: List<String>,
    val sound: String,
    val video: Int,
    val lifePoints: Int,
    val chakraPoints: Int
)