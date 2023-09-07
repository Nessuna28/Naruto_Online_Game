package com.example.abschlussaufgabe

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ScissorsRockPaperViewModel(application: Application): AndroidViewModel(application) {

    private val _playerPoints = MutableLiveData(0)
    val playerPoints: LiveData<Int>
        get() = _playerPoints


    private val _enemyPoints = MutableLiveData(0)
    val enemyPoints: LiveData<Int>
        get() = _enemyPoints


    private val _randomHand = MutableLiveData<String>()
    val randomHand: LiveData<String>
        get() = _randomHand


    private val _playerHand = MutableLiveData<String>()
    val playerHand: LiveData<String>
        get() = _playerHand


    private var playerWon = 0



    // Funktionen

    fun setPlayerWon(result: Int) {

        playerWon = result
    }

    fun calculatePoints() {

        if (playerWon == 2) {
            _playerPoints.value = playerPoints.value!!.plus(1)
        } else if (playerWon == 1){
            _enemyPoints.value = enemyPoints.value!!.plus(1)
        }
    }

    fun resetPoints() {

        _playerPoints.value = 0
        _enemyPoints.value = 0
    }

    fun setPlayerHand(hand: String) {

        _playerHand.value = hand
    }

    fun randomHandEnemy() {

        val handList = listOf("paper", "rock", "scissors")
        _randomHand.value = handList.random()
    }

    fun checkHands(playerHand: String, enemyHand: String) {

        if (playerHand == "paper" && enemyHand == "scissors") {
            playerWon = 1
        } else if (playerHand == "paper" && enemyHand == "rock") {
            playerWon = 2
        } else if (playerHand == "rock" && enemyHand == "scissors") {
            playerWon = 2
        } else if (playerHand == "rock" && enemyHand == "paper") {
            playerWon = 1
        } else if (playerHand == "scissors" && enemyHand == "rock") {
            playerWon = 1
        } else if (playerHand == "scissors" && enemyHand == "paper") {
            playerWon = 2
        } else if (
            playerHand == "paper" && enemyHand == "paper" ||
            playerHand == "rock" && enemyHand == "rock" ||
            playerHand == "scissors" && enemyHand == "scissors"){
            playerWon = 0
        }
    }
}