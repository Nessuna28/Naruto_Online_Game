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
    var jutsus: List<Jutsu>,
    val tools: List<Tool>,
    val uniqueTraits: List<UniqueTrait>,
    val defense: List<Defense>,
) {

    val lifePointsStart: Int = 500
    val chakraPointsStart: Int = 500

    var lifePoints: Int = 500
    var chakraPoints: Int = 500
}



