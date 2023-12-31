package com.example.abschlussaufgabe

import android.app.Application
import android.content.Context
import android.media.MediaPlayer
import android.os.CountDownTimer
import android.os.Handler
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.abschlussaufgabe.data.datamodels.modelForFight.Attack
import com.example.abschlussaufgabe.data.datamodels.modelForFight.CharacterForFight
import com.example.abschlussaufgabe.data.datamodels.modelForFight.Jutsu
import com.example.abschlussaufgabe.data.datamodels.modelForFight.Location
import com.example.abschlussaufgabe.data.datamodels.modelForFight.UniqueTrait
import com.example.abschlussaufgabe.data.datamodels.modelForFight.dataLists.CharacterListForFight
import com.example.abschlussaufgabe.data.datamodels.modelForFight.dataLists.LocationList
import com.example.abschlussaufgabe.data.datamodels.modelForFight.Team

class FightViewModel(application: Application): AndroidViewModel(application) {

    private var mediaPlayer: MediaPlayer? = null

    private val _volume = MutableLiveData(0.9F)
    val volume: LiveData<Float>
        get() = _volume

    // für die Kampfeinstellungen

    private val _selectFight = MutableLiveData<String>()
    val selectFight: LiveData<String>
        get() = _selectFight


    private val _selectRounds = MutableLiveData<String>()
    val selectRounds: LiveData<String>
        get() = _selectRounds


    private val _selectTimer = MutableLiveData<String>()
    val selectTimer: LiveData<String>
        get() = _selectTimer


    private val _selectDifficultyLevel = MutableLiveData<String>()
    val selectDifficultyLevel: LiveData<String>
        get() = _selectDifficultyLevel


    // für die Charakterauswahl (CharacterSelectionFragment)

    private val _characterForFight = MutableLiveData<List<CharacterForFight>>()
    val characterForFight: LiveData<List<CharacterForFight>>
        get() = _characterForFight


    private val _imageForPlayer = MutableLiveData<Int>()
    val imageForPlayer: LiveData<Int>
        get() = _imageForPlayer


    private val _characterNameForPlayer = MutableLiveData<String>()
    val characterNameForPlayer: LiveData<String>
        get() = _characterNameForPlayer


    private val _jutsuListForPlayer = MutableLiveData<List<Jutsu>>()
    val jutsuListForPlayer: LiveData<List<Jutsu>>
        get() = _jutsuListForPlayer


    private val _uniqueTraitsListForPlayer = MutableLiveData<List<UniqueTrait>>()
    val uniqueTraitsListForPlayer: LiveData<List<UniqueTrait>>
        get() = _uniqueTraitsListForPlayer


    private val _imageForEnemy = MutableLiveData<Int>()
    val imageForEnemy: LiveData<Int>
        get() = _imageForEnemy


    private val _characterNameForEnemy = MutableLiveData<String>()
    val characterNameForEnemy: LiveData<String>
        get() = _characterNameForEnemy


    private val _jutsuListForEnemy = MutableLiveData<List<Jutsu>>()
    val jutsuListForEnemy: LiveData<List<Jutsu>>
        get() = _jutsuListForEnemy


    private val _uniqueTraitsListForEnemy = MutableLiveData<List<UniqueTrait>>()
    val uniqueTraitsListForEnemy: LiveData<List<UniqueTrait>>
        get() = _uniqueTraitsListForEnemy


    private val _selectionConfirmedPlayer = MutableLiveData<Boolean>()
    val selectionConfirmedPlayer: LiveData<Boolean>
        get() = _selectionConfirmedPlayer


    private val _selectionConfirmedEnemy = MutableLiveData<Boolean>()
    val selectionConfirmedEnemy: LiveData<Boolean>
        get() = _selectionConfirmedEnemy


    private val _teammate1Player = MutableLiveData<CharacterForFight>()
    val teammate1Player: LiveData<CharacterForFight>
        get() = _teammate1Player


    private val _teammate2Player = MutableLiveData<CharacterForFight>()
    val teammate2Player: LiveData<CharacterForFight>
        get() = _teammate2Player


    private val _teammate1Enemy = MutableLiveData<CharacterForFight>()
    val teammate1Enemy: LiveData<CharacterForFight>
        get() = _teammate1Enemy


    private val _teammate2Enemy = MutableLiveData<CharacterForFight>()
    val teammate2Enemy: LiveData<CharacterForFight>
        get() = _teammate2Enemy



    // für die Auswahl der Location (LocationSelectionFragment)

    private val _locationList = MutableLiveData<List<Location>>()
    val locationList: LiveData<List<Location>>
        get() = _locationList


    private val _location = MutableLiveData<Location>()
    val location: LiveData<Location>
        get() = _location


    private val _selectionConfirmLocation = MutableLiveData<Boolean>()
    val selectionConfirmLocation: LiveData<Boolean>
        get() = _selectionConfirmLocation


    // für den Kampf (FightFragment)

    private val _player = MutableLiveData<CharacterForFight>()
    val player: LiveData<CharacterForFight>
        get() = _player


    private val _enemy = MutableLiveData<CharacterForFight>()
    val enemy: LiveData<CharacterForFight>
        get() = _enemy


    private val _attackPlayer = MutableLiveData<Attack>()
    val attackPlayer: LiveData<Attack>
        get() = _attackPlayer


    private val _attackEnemy = MutableLiveData<Attack>()
    val attackEnemy: LiveData<Attack>
        get() = _attackEnemy


    // für den Timer

    var timer: CountDownTimer? = null


    private val _remainingTime = MutableLiveData<Int>()
    val remainingTime: LiveData<Int>
        get() = _remainingTime


    // für Toast-Messages
    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String>
        get() = _toastMessage


    // für das Ergebnis (ResultFragment)

    private val _result = MutableLiveData("")
    val result: LiveData<String>
        get() = _result


    private val _resultEnemy = MutableLiveData("")
    val resultEnemy: LiveData<String>
        get() = _resultEnemy


    private val _roundsWonPlayer = MutableLiveData(0)
    val roundsWonPlayer: LiveData<Int>
        get() = _roundsWonPlayer


    private val _roundsWonEnemy = MutableLiveData(0)
    val roundsWonEnemy: LiveData<Int>
        get() = _roundsWonEnemy


    private val _rounds = MutableLiveData(0)
    val rounds: LiveData<Int>
        get() = _rounds


    private val _roundBegan = MutableLiveData(false)
    val roundBegan: LiveData<Boolean>
        get() = _roundBegan


    private val _gameEnd = MutableLiveData(false)
    val gameEnd: LiveData<Boolean>
        get() = _gameEnd




    init {
        _characterForFight.value = CharacterListForFight().characterList
        _locationList.value = LocationList().locationList
        _location.value = locationList.value?.get(0)
    }



    // setzt die jeweiligen Sounds und spielt sie ab

    fun playSound(context: Context, sound: Int) {

        mediaPlayer?.release()

        mediaPlayer = MediaPlayer.create(context, sound)

        mediaPlayer?.setOnCompletionListener {
            releaseMediaPlayer()
        }

        setVolume()
        mediaPlayer?.start()
    }

    fun releaseMediaPlayer() {

        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun setVolume() {

        mediaPlayer?.setVolume(volume.value!!, volume.value!!)
    }

    fun setValueForVolume(value: Float) {

        _volume.value = value
    }

    // beendet den Sound
    fun stopSound() {

        mediaPlayer?.stop()
    }


    // diese Funktionen speichern die Auswahl in den LiveData-Variablen

    fun selectFight(selection: String) {

        _selectFight.value = selection
    }

    fun selectRounds(selection: String) {

        _selectRounds.value = selection
    }

    fun selectTimer(selection: String) {

        _selectTimer.value = selection
    }

    fun selectDifficultyLevel(selection: String) {

        _selectDifficultyLevel.value = selection
    }


    // speichert den ausgewählten Charakter für den Kampf in den LiveData-Variablen

    fun setPlayer(character: CharacterForFight) {

        _player.value = character
    }

    fun setTeammate1Player(teammate: CharacterForFight) {

        _teammate1Player.value = teammate
    }

    fun setTeammate2Player(teammate: CharacterForFight) {

        _teammate2Player.value = teammate
    }

    fun setEnemy(character: CharacterForFight) {

        _enemy.value = character
    }

    fun setTeammate1Enemy(teammate: CharacterForFight) {

        _teammate1Enemy.value = teammate
    }

    fun setTeammate2Enemy(teammate: CharacterForFight) {

        _teammate1Enemy.value = teammate
    }


    // speichert die ausgewählte Location in der LiveData-Variable
    fun setLocation(location: Location) {

        _location.value = location
    }


    // für die Prüfung ob der Nutzer den Weiter-Button drücken darf

    fun confirmSelectionPlayer(check: Boolean) {

        _selectionConfirmedPlayer.value = check
    }

    fun confirmSelectionEnemy(check: Boolean) {

        _selectionConfirmedEnemy.value = check
    }

    fun confirmSelectionLocation(check: Boolean) {

        _selectionConfirmLocation.value = check
    }


    // hier wird eine zufällige Auswahl getroffen und die dazugehörigen Daten werden gesetzt

    fun randomCharacterForPlayer() {

        val randomCharacter = characterForFight.value!!.random()

        setPlayer(randomCharacter)
    }

    fun randomTeamForPlayer() {

        val randomTeam = mutableListOf<CharacterForFight>()
        val randomTeammate1 = characterForFight.value!!.random()
        val randomTeammate2 = characterForFight.value!!.random()

        randomTeam.add(randomTeammate1)

        if (randomTeam.contains(randomTeammate2)) {
            randomTeamForPlayer()
        } else {
            randomTeam.add(randomTeammate2)
        }

        _teammate1Player.value = randomTeam[0]
        _teammate2Player.value = randomTeam[1]
    }

    fun randomCharacterForEnemy() {

        val randomCharacter = characterForFight.value!!.random()

        setEnemy(randomCharacter)
    }

    fun randomTeamForEnemy() {

        val randomTeam = mutableListOf<CharacterForFight>()
        val randomTeammate1 = characterForFight.value!!.random()
        val randomTeammate2 = characterForFight.value!!.random()

        randomTeam.add(randomTeammate1)

        if (randomTeam.contains(randomTeammate2)) {
            randomTeamForEnemy()
        } else {
            randomTeam.add(randomTeammate2)
        }

        _teammate1Enemy.value = randomTeam[0]
        _teammate2Enemy.value = randomTeam[1]
    }


    fun randomLocation() {

        val randomLocation = locationList.value!!.random()

        _location.value = randomLocation
    }


    // setzt die Daten wieder zurück zum Ursprung
    fun resetSelectionData() {

        _imageForPlayer.value = characterForFight.value?.get(0)?.imagePlayer
        _imageForEnemy.value = characterForFight.value?.get(0)?.imageEnemy
        _characterNameForPlayer.value = characterForFight.value?.get(0)?.name
        _characterNameForEnemy.value = characterForFight.value?.get(0)?.name
        _jutsuListForPlayer.value = characterForFight.value?.get(0)?.jutsus
        _uniqueTraitsListForPlayer.value = characterForFight.value?.get(0)?.uniqueTraits
        _jutsuListForEnemy.value = characterForFight.value?.get(0)?.jutsus
        _uniqueTraitsListForEnemy.value = characterForFight.value?.get(0)?.uniqueTraits
    }


    // setzt die Attacke mit der im Adapter ausgewählten Attacke für eine Sekunde
    fun setAttackPlayer(attack: Attack) {

        _attackPlayer.value = attack
        Handler().postDelayed({
            _attackPlayer.value = Attack("", 0, player.value!!.imagePlayer, enemy.value!!.imageEnemy)
        }, 1000)
    }

    // es wird erst eine Liste aller möglichen Attacken des Charackters erstellt
    // und aus der Liste eine zufällige Attacke ausgewählt für eine Sekunde
    fun setAttackEnemy() {

        val jutsuList = enemy.value!!.jutsus
        val toolList = enemy.value!!.tools
        val traitsList = enemy.value!!.uniqueTraits
        val defenseList = enemy.value!!.defense

        val listOfAllAttacks = mutableListOf<Attack>()

        for (attack in jutsuList) {
            listOfAllAttacks.add(attack)
        }

        for (attack in toolList) {
            listOfAllAttacks.add(attack)
        }

        for (attack in traitsList) {
            listOfAllAttacks.add(attack)
        }

        for (attack in defenseList) {
            listOfAllAttacks.add(attack)
        }

        val currentAttackEnemy = listOfAllAttacks.random()

        _attackEnemy.value = currentAttackEnemy
        Handler().postDelayed({
            _attackEnemy.value = Attack("", 0, player.value!!.imagePlayer, enemy.value!!.imageEnemy)
        }, 1000)
    }

    // sorgt dafür dass nach je nach gewählten Schwierigkeitsgrad in einem gewissen Abstand alle Funktionen für den Computer wiederholt werden
    val runnable: Runnable = object : Runnable {
        override fun run() {

            setAttackEnemy()

            if (selectDifficultyLevel.value == "mittel") {
                Handler().postDelayed(this, 3000)
            } else if (selectDifficultyLevel.value == "schwer") {
                Handler().postDelayed(this, 1000)
            } else if (selectDifficultyLevel.value == "leicht") {
                Handler().postDelayed(this, 5000)
            }

        }
    }

    // startet den Timer je nach dem welche Zeit ausgewählt wurde
    fun startTimer() {

        stopTimer()

        var time: Long = 60000
        if (selectTimer.value == "30 Sekunden") {
            time = 30000
        } else if (selectTimer.value == "60 Sekunden") {
            time = 60000
        } else if (selectTimer.value == "90 Sekunden") {
            time = 90000
        }

        timer = object : CountDownTimer(time, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Hier wird der Timer aktualisiert, während er herunterzählt
                // Du kannst den verbleibenden Millisekunden nutzen, um die Zeit im UI anzuzeigen
                val secondsRemaining = millisUntilFinished / 1000
                _remainingTime.value = secondsRemaining.toInt()
            }

            override fun onFinish() {
                // Hier wird der Timer auf null gesetzt, wenn die Zeit abgelaufen ist
                endRound()
            }
        }
        timer?.start()
    }

    fun setRemainingTimeToStart() {

        if (selectTimer.value == "30 Sekunden") {
            _remainingTime.value = 30
        } else if (selectTimer.value == "60 Sekunden") {
            _remainingTime.value = 60
        } else if (selectTimer.value == "90 Sekunden") {
            _remainingTime.value = 90
        }
    }

    // stopt den Timer
    fun stopTimer() {
        timer?.cancel()
    }

    // ändert die LiveData-Variable auf true oder false je nach dem ob das Spiel beginnt oder nicht
    fun startRound(check: Boolean) {

        _roundBegan.value = check
    }

    // in diesen Funktionen werden die Punkte berechnet
    // wenn der Spieler angreift
    fun playRoundPlayer() {

        if (selectRounds.value == "1") {
            if (rounds.value!! == 1) {
                _gameEnd.value = true
            } else {
                if (attackPlayer.value != null && attackEnemy.value != null) {
                    if (player.value!!.lifePoints > 0 || enemy.value!!.lifePoints > 0) {
                        attackPlayer.value!!.loadChakra(_player.value!!, player.value!!)
                        attackPlayer.value!!.subtractChakra(
                            _player.value!!,
                            player.value!!,
                            ::setMessage
                        )
                        attackPlayer.value!!.subtractLifePoints(
                            player.value!!,
                            _player.value!!,
                            attackEnemy.value!!,
                            _enemy.value!!,
                            enemy.value!!,
                            ::setMessage
                        )
                    }
                }
            }
        } else if (selectRounds.value == "3") {
            if (rounds.value!! == 3 || roundsWonPlayer.value!! == 2 || roundsWonEnemy.value!! == 2) {
                _gameEnd.value = true

            } else {
                if (attackPlayer.value != null && attackEnemy.value != null) {
                    if (player.value!!.lifePoints > 0 || enemy.value!!.lifePoints > 0) {
                        attackPlayer.value!!.loadChakra(_player.value!!, player.value!!)
                        attackPlayer.value!!.subtractChakra(
                            _player.value!!,
                            player.value!!,
                            ::setMessage
                        )
                        attackPlayer.value!!.subtractLifePoints(
                            player.value!!,
                            _player.value!!,
                            attackEnemy.value!!,
                            _enemy.value!!,
                            enemy.value!!,
                            ::setMessage
                        )
                    }
                }
            }
        }


        _player.value = _player.value
        _enemy.value = _enemy.value
    }

    // wenn der Gegner angreift
    fun playRoundEnemy() {

        if (selectRounds.value == "1") {
            if (rounds.value!! == 1) {
                _gameEnd.value = true

            } else {
                if (attackPlayer.value != null && attackEnemy.value != null) {
                    if (player.value!!.lifePoints > 0 || enemy.value!!.lifePoints > 0) {
                        attackEnemy.value!!.loadChakra(_enemy.value!!, enemy.value!!)
                        attackEnemy.value!!.subtractChakra(
                            _enemy.value!!,
                            enemy.value!!,
                            ::setMessage
                        )
                        attackEnemy.value!!.subtractLifePoints(
                            enemy.value!!,
                            _enemy.value!!,
                            attackPlayer.value!!,
                            _player.value!!,
                            player.value!!,
                            ::setMessage
                        )
                    }
                }
            }
        } else if (selectRounds.value == "3") {
            if (rounds.value!! == 3 || roundsWonPlayer.value!! == 2 || roundsWonEnemy.value!! == 2) {
                _gameEnd.value = true

            } else {
                if (attackPlayer.value != null && attackEnemy.value != null) {
                    if (player.value!!.lifePoints > 0 || enemy.value!!.lifePoints > 0) {
                        attackEnemy.value!!.loadChakra(_enemy.value!!, enemy.value!!)
                        attackEnemy.value!!.subtractChakra(
                            _enemy.value!!,
                            enemy.value!!,
                            ::setMessage
                        )
                        attackEnemy.value!!.subtractLifePoints(
                            enemy.value!!,
                            _enemy.value!!,
                            attackPlayer.value!!,
                            _player.value!!,
                            player.value!!,
                            ::setMessage
                        )
                    }
                }
            }
        }

        _player.value = _player.value
        _enemy.value = _enemy.value
    }


    // je nach dem ob der Spieler ein Zeitlimit eingestellt hat, wird wenn die Zeit abgelaufen ist
    // der die Runde gewinnen der noch die meisten Lebenspunkte hat oder
    // wenn einer der Spieler keine Lebenspunkte mehr hat wird die Runde beendet und
    // es wird festgelegt ob der Spieler gewonnen oder verloren hat
    fun endRound() {

        if (selectTimer.value == "kein Zeitlimit") {
                if (player.value!!.lifePoints > 0) {
                    _roundsWonPlayer.value = roundsWonPlayer.value!!.plus(1)
                } else if (enemy.value!!.lifePoints > 0) {
                    _roundsWonEnemy.value = roundsWonEnemy.value!!.plus(1)
                }
        } else {
            if (remainingTime.value == 0) {
                if (player.value!!.lifePoints < enemy.value!!.lifePoints) {
                    _roundsWonEnemy.value = roundsWonEnemy.value!!.plus(1)
                } else if (player.value!!.lifePoints > enemy.value!!.lifePoints) {
                    _roundsWonPlayer.value = roundsWonPlayer.value!!.plus(1)
                }
            } else if (remainingTime.value!! > 0) {
                    if (player.value!!.lifePoints > 0) {
                        _roundsWonPlayer.value = roundsWonPlayer.value!!.plus(1)
                    } else if (enemy.value!!.lifePoints > 0) {
                        _roundsWonEnemy.value = roundsWonEnemy.value!!.plus(1)
                    }
            }

            _roundsWonPlayer.value = _roundsWonPlayer.value
            _roundsWonEnemy.value = _roundsWonEnemy.value
        }

        _rounds.value = rounds.value!!.plus(1)
        stopTimer()
        Handler().removeCallbacks(runnable)
        _roundBegan.value = false

        _rounds.value = _rounds.value
        _roundsWonPlayer.value = _roundsWonPlayer.value
        _roundsWonEnemy.value = _roundsWonEnemy.value
    }

    // setzt die Lebens- und Chakrapunkte wieder auf die Ursprungswerte außer
    // wenn man noch Lebenspunkte übrig hat dann nimmt man diese mit in die nächste Runde
    // und startet den Timer neu
    fun resetPointsForNewRound() {

        if (player.value!!.lifePoints <= 0) {
            _player.value!!.lifePoints = player.value!!.lifePointsStart
        } else {
            _enemy.value!!.lifePoints = enemy.value!!.lifePointsStart
        }

        _player.value!!.chakraPoints = player.value!!.chakraPointsStart
        _enemy.value!!.chakraPoints = enemy.value!!.chakraPointsStart
    }

    // setzt die Lebens- und Chakrapunkte zurück auf den Anfangswert und startet den Timer neu
    fun resetPointsForNewGame() {

        _player.value!!.lifePoints = player.value!!.lifePointsStart
        _player.value!!.chakraPoints = player.value!!.chakraPointsStart

        _enemy.value!!.lifePoints = enemy.value!!.lifePointsStart
        _enemy.value!!.chakraPoints = enemy.value!!.chakraPointsStart

        _player.value = _player.value
        _enemy.value = _enemy.value
    }

    // setzt alle Werte zurück auf 0
    fun resetToDefaultRounds() {

        _gameEnd.value = false
        _rounds.value = 0
        _result.value = ""
        _resultEnemy.value = ""
        _roundsWonPlayer.value = 0
        _roundsWonEnemy.value = 0
        _attackPlayer.value = Attack("", 0, player.value!!.imagePlayer, enemy.value!!.imageEnemy)
        _attackEnemy.value = Attack("", 0, player.value!!.imagePlayer, enemy.value!!.imageEnemy)
    }

    // ändert den Wert des Ergebniss (gewonnen oder verloren)
    fun setResult(check: Boolean) {

        if (check) {
            _result.value = "Sieg"
            _resultEnemy.value = "Niederlage"
        } else {
            _result.value = "Niederlage"
            _resultEnemy.value = "Sieg"
        }
    }

    // Funktion um den Toast anzuzeigen
    fun setMessage(message: String) {

        _toastMessage.value = message
    }
}