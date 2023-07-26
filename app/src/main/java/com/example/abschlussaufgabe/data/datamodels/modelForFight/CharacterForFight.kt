package com.example.abschlussaufgabe.data.datamodels.modelForFight


data class CharacterForFight(

    val image: Int,
    val imagePose: Int,
    val imageFace: Int,
    val imageAttack: Int,
    val imageSad: Int,
    val sound: Int,
    val video: Int,
    val name: String,
    var jutsus: Map<String, Int>,
    val tools: Map<String, Int>,
    val uniqueTraits: Map<String, Int>,
    val defense: Map<String, Int>,
    var lifePoints: Int,
    var chakraPoints: Int
)

