package com.example.abschlussaufgabe.data.datamodels.modelForFight

class Jutsu(name: String, value: Int, imagePlayer: Int, imageEnemy: Int): Attack(name, value, imagePlayer, imageEnemy) {

    init {
        isJutsu = true
    }
}