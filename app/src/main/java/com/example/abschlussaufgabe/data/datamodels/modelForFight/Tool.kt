package com.example.abschlussaufgabe.data.datamodels.modelForFight

class Tool(name: String, value: Int, imagePlayer: Int, imageEnemy: Int): Attack(name, value, imagePlayer, imageEnemy) {

    init {
        isTool = true
    }
}