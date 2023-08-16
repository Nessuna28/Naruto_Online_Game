package com.example.abschlussaufgabe

import android.app.Application
import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.abschlussaufgabe.data.datamodels.modelForKniffel.Dice
import com.example.abschlussaufgabe.data.datamodels.modelForKniffel.DiceSide
import com.example.abschlussaufgabe.data.datamodels.modelForKniffel.KniffelValue
import com.example.abschlussaufgabe.data.datamodels.modelForKniffel.dataList.DiceList


class KniffelViewModel(application: Application): AndroidViewModel(application) {

    private var mediaPlayer: MediaPlayer? = null
    private var currentIndex = 0


    // LiveData-Variablen

    private val _diceList = MutableLiveData<List<Dice>>()
    val diceList: LiveData<List<Dice>>
        get() = _diceList


    private val _selectedDice = MutableLiveData<Dice?>()
    val selectedDice: LiveData<Dice?>
        get() = _selectedDice


    private var songList = mutableListOf<Int>()


    private val _rolledDice1 = MutableLiveData<Dice>()
    val rolledDice1: LiveData<Dice>
        get() = _rolledDice1


    private val _rolledDice2 = MutableLiveData<Dice>()
    val rolledDice2: LiveData<Dice>
        get() = _rolledDice2


    private val _rolledDice3 = MutableLiveData<Dice>()
    val rolledDice3: LiveData<Dice>
        get() = _rolledDice3


    private val _rolledDice4 = MutableLiveData<Dice>()
    val rolledDice4: LiveData<Dice>
        get() = _rolledDice4


    private val _rolledDice5 = MutableLiveData<Dice>()
    val rolledDice5: LiveData<Dice>
        get() = _rolledDice5


    private val _attempts = MutableLiveData(3)
    val attempts: LiveData<Int>
        get() = _attempts


    private val _points = MutableLiveData<Int>()
    val points: LiveData<Int>
        get() = _points


    private val _values = MutableLiveData<KniffelValue>()
    val values: LiveData<KniffelValue>
        get() = _values


    // Variablen der Punkte für die linke Spalte und für die rechte Spalte

    private var leftColumn: Int = 0

    private var rightColumn: Int = 0

    // Variablen der einzelnen Werte

    private var one: Int = 0
    private var two: Int = 0
    private var three: Int = 0
    private var four: Int = 0
    private var five: Int = 0
    private var six: Int = 0
    private var bonus: Int = 0
    private var threesome: Int = 0
    private var foursome: Int = 0
    private var fullHouse: Int = 0
    private var bigStreet: Int = 0
    private var littleStreet: Int = 0
    private var kniffel: Int = 0
    private var chance: Int = 0


    // LiveData-Variablen für die zufälligen Würfelseiten der 5 Würfel zum würfeln

    private val _diceSideRolledDice1 = MutableLiveData<DiceSide?>()
    val diceSideRolledDice1: LiveData<DiceSide?>
        get() = _diceSideRolledDice1


    private val _diceSideRolledDice2 = MutableLiveData<DiceSide?>()
    val diceSideRolledDice2: LiveData<DiceSide?>
        get() = _diceSideRolledDice2


    private val _diceSideRolledDice3 = MutableLiveData<DiceSide?>()
    val diceSideRolledDice3: LiveData<DiceSide?>
        get() = _diceSideRolledDice3


    private val _diceSideRolledDice4 = MutableLiveData<DiceSide?>()
    val diceSideRolledDice4: LiveData<DiceSide?>
        get() = _diceSideRolledDice4


    private val _diceSideRolledDice5 = MutableLiveData<DiceSide?>()
    val diceSideRolledDice5: LiveData<DiceSide?>
        get() = _diceSideRolledDice5


    // Liste der Variablen der Würfelseiten
    private val diceSideList: List<DiceSide>
        get() = listOf(diceSideRolledDice1.value!!, diceSideRolledDice2.value!!, diceSideRolledDice3.value!!, diceSideRolledDice4.value!!, diceSideRolledDice5.value!!)


    private val _isGameOver = MutableLiveData<Boolean>()
    val isGameOver: LiveData<Boolean>
        get() = _isGameOver


    // Initialisierung
    init {
        _diceList.value = DiceList().diceList
        initValues()
    }


    // Funktionen

    fun initValues() {

        val value = _values.value ?: KniffelValue()

        if (!value.one.second) value.one = Pair(one, false)
        if (!value.two.second) value.two = Pair(two, false)
        if (!value.three.second) value.three = Pair(three, false)
        if (!value.four.second) value.four = Pair(four, false)
        if (!value.five.second) value.five = Pair(five, false)
        if (!value.six.second) value.six = Pair(six, false)
        if (!value.threesome.second) value.threesome = Pair(threesome, false)
        if (!value.foursome.second) value.foursome = Pair(foursome, false)
        if (!value.fullHouse.second) value.fullHouse = Pair(fullHouse, false)
        if (!value.bigStreet.second) value.bigStreet = Pair(bigStreet, false)
        if (!value.littleStreet.second) value.littleStreet = Pair(littleStreet, false)
        if (!value.kniffel.second) value.kniffel = Pair(kniffel, false)
        if (!value.chance.second) value.chance = Pair(chance, false)

        _values.value = value
    }

    // speichert die übergebene Auswahl eines Teams in der LiveData-Variable
    fun selectTeam(selection: Dice) {

        _selectedDice.value = selection
    }

    // speichert die übergebenen Versuche in der LiveDta-Variable
    fun setAttempts(attempts: Int) {

        _attempts.value = attempts
    }

    // zählt die Versuche runter
    fun calculateAttempts() {

        _attempts.value = attempts.value!!.minus(1)

        _attempts.value = attempts.value
    }


    // erstellt eine Liste der gewählten Würfelseiten und
    fun initRollingDice() {

        val list = mutableListOf<Dice>()
        var index = 0

        // es werden je nach dem für welche Würfelbilder sich der Spieler entschieden hat,
        // 5 Würfel mit der getroffenen Auswahl der Liste zugefügt
        when(selectedDice.value!!.teamName) {
            "Team Kakashi" -> index = 0
            "Team Asuma" -> index = 1
            "Team Kurenai" -> index = 2
            "Team Maito Gai" -> index = 3
            "Team Gaara" -> index = 4
            "Team legendäre Sannin" -> index = 5
            "Team Hokage" -> index = 6
            "Team Akazuki" -> index = 7
            "Team Gehilfen" -> index = 8
        }

        list.add(DiceList().diceList[index])
        list.add(DiceList().diceList[index])
        list.add(DiceList().diceList[index])
        list.add(DiceList().diceList[index])
        list.add(DiceList().diceList[index])


        // jedem Würfel zum würfeln wird ein Würfel aus der obrigen Liste zugewiesen
        _rolledDice1.value = list[0]
        _rolledDice2.value = list[1]
        _rolledDice3.value = list[2]
        _rolledDice4.value = list[3]
        _rolledDice5.value = list[4]
    }

    fun setSongs() {

        if (selectedDice.value != null) {
            when(selectedDice.value!!.teamName) {
                "Team Kakashi" -> {
                    songList.add(R.raw.song_kakashi)
                    songList.add(R.raw.song_naruto)
                    songList.add(R.raw.song_sasuke)
                    songList.add(R.raw.song_sakura)
                }

                "Team Asuma" -> {
                    songList.add(R.raw.song_asuma)
                    songList.add(R.raw.song_shikamaru)
                }

                "Team Kurenai" -> {
                    songList.add(R.raw.song_kiba)
                    songList.add(R.raw.song_hinata)
                }

                "Team Maito Gai" -> {
                    songList.add(R.raw.song_maitogai)
                    songList.add(R.raw.song_neji)
                    songList.add(R.raw.song_rocklee)
                }

                "Team Gaara" -> {
                    songList.add(R.raw.song_gaara)
                }

                "Team legendäre Sannin" -> {
                    songList.add(R.raw.song_tsunade)
                    songList.add(R.raw.song_jiraiya)
                    songList.add(R.raw.song_orochimaru)
                }

                "Team Hokage" -> {
                    songList.add(R.raw.song_minato)
                    songList.add(R.raw.song_tsunade)
                    songList.add(R.raw.song_kakashi)
                }

                "Team Akazuki" -> {
                    songList.add(R.raw.song_deidara)
                }

                "Team Gehilfen" -> {
                    songList.add(R.raw.song_pain)
                }
            }
        }

        Log.e("Kniffel", "$songList")
    }

    // spielt eine Liste an Songs ab
    private fun prepareMediaPlayer(context: Context) {
        mediaPlayer = MediaPlayer.create(context, songList[currentIndex])
        mediaPlayer?.setOnCompletionListener {
            // Wenn die Wiedergabe beendet ist, spielen Sie das nächste Lied ab
            playNextSong(context)
        }
    }

    fun playFirstSong(context: Context) {
        prepareMediaPlayer(context)
        mediaPlayer?.start()
    }

    private fun playNextSong(context: Context) {
        currentIndex = (currentIndex + 1) % songList.size
        mediaPlayer?.reset()
        prepareMediaPlayer(context)
        mediaPlayer?.start()
    }

    fun stopSound() {

        mediaPlayer?.stop()
    }


    // sucht eine zufällige Würfelseite aus für alle 5 Würfel
    fun rollTheDice() {

        if (diceSideRolledDice1.value == null) {
            _diceSideRolledDice1.value = rolledDice1.value!!.diceSideList.random()
        } else if (!diceSideRolledDice1.value!!.toKeep) {
            _diceSideRolledDice1.value = rolledDice1.value!!.diceSideList.random()
        }

        if (diceSideRolledDice2.value == null) {
            _diceSideRolledDice2.value = rolledDice2.value!!.diceSideList.random()
        } else if (!diceSideRolledDice2.value!!.toKeep) {
            _diceSideRolledDice2.value = rolledDice2.value!!.diceSideList.random()
        }

        if (diceSideRolledDice3.value == null) {
            _diceSideRolledDice3.value = rolledDice3.value!!.diceSideList.random()
        } else if (!diceSideRolledDice3.value!!.toKeep) {
            _diceSideRolledDice3.value = rolledDice3.value!!.diceSideList.random()
        }

        if (diceSideRolledDice4.value == null) {
            _diceSideRolledDice4.value = rolledDice4.value!!.diceSideList.random()
        } else if (!diceSideRolledDice4.value!!.toKeep) {
            _diceSideRolledDice4.value = rolledDice4.value!!.diceSideList.random()
        }

        if (diceSideRolledDice5.value == null) {
            _diceSideRolledDice5.value = rolledDice5.value!!.diceSideList.random()
        } else if (!diceSideRolledDice5.value!!.toKeep) {
            _diceSideRolledDice5.value = rolledDice5.value!!.diceSideList.random()
        }
    }

    // setzt den Boolean der Würfel in der Liste auf false
    fun setRolledDiceOfFalse() {

        diceSideList.forEach { diceSide ->
                diceSide.toKeep = false
        }
    }

    // ändert den Wert des Würfels auf true oder false, je nach dem was übergeben wird

    fun keepDice1(check: Boolean) {

        _diceSideRolledDice1.value!!.toKeep = check
    }

    fun keepDice2(check: Boolean) {

        _diceSideRolledDice2.value!!.toKeep = check
    }

    fun keepDice3(check: Boolean) {

        _diceSideRolledDice3.value!!.toKeep = check
    }

    fun keepDice4(check: Boolean) {

        _diceSideRolledDice4.value!!.toKeep = check
    }

    fun keepDice5(check: Boolean) {

        _diceSideRolledDice5.value!!.toKeep = check
    }

    // speichert die Werte in den LiveData-Variablen
    fun setValues() {

        resetValues()

        // erstellt eine Liste mit nur den Werten der gewürfelten Würfel
        val valueList: List<Int> = diceSideList.map { it.value }


        // die einzelnen Augen zusammen zählen
        for (i in valueList) {
            when (i) {
                1 -> one += i
                2 -> two += i
                3 -> three += i
                4 -> four += i
                5 -> five += i
                6 -> six += i
            }
        }


            // das Full House zählen
            // Gruppieren der Zahlen und ihre Häufigkeit zählen
            val numberFrequency = valueList.groupingBy { it }.eachCount()

            // Überprüfen, ob es genau zwei verschiedene Zahlen gibt
            if (numberFrequency.size == 2) {
                // Überprüfen, ob eine Zahl genau dreimal und die andere genau zweimal vorkommt
                val values = numberFrequency.values
                if (values.contains(3) && values.contains(2)) {
                    fullHouse = 25
                }
            }

            // den 3er zählen
            if (numberFrequency.any { it.value >= 3 }) {
                threesome = valueList.sum()
            }

            // den 4er zählen
            if (numberFrequency.any { it.value >= 4 }) {
                foursome = valueList.sum()
            }

            // die kleine Straße zählen
            val numbersForLittleStreet1 = listOf(2, 3, 4, 5)
            val numbersForLittleStreet2 = listOf(1, 2, 3, 4)
            val numbersForLittleStreet3 = listOf(3, 4, 5, 6)

            val littleStreet1 = valueList.containsAll(numbersForLittleStreet1)
            val littleStreet2 = valueList.containsAll(numbersForLittleStreet2)
            val litlleStreet3 = valueList.containsAll(numbersForLittleStreet3)

            if (littleStreet1 || littleStreet2 || litlleStreet3) {
                littleStreet = 30
            }

            // die große Straße zählen
            val numbersForBigStreet1 = listOf(1, 2, 3, 4, 5)
            val numbersForBigStreet2 = listOf(2, 3, 4, 5, 6)

            val bigStreet1 = valueList.containsAll(numbersForBigStreet1)
            val bigStreet2 = valueList.containsAll(numbersForBigStreet2)

            if (bigStreet1 || bigStreet2) {
                bigStreet = 40
            }

            // einen Kniffel zählen
            val allNumbersSame = valueList.all { it == valueList[0] }

            if (allNumbersSame) {
                kniffel = 50
            }

            // alle Augen für die Chance zählen
            chance = valueList.sum()

            initValues()

            _values.value = _values.value

        Log.e("Values", "${values.value!!.one}")
        Log.e("Values", "${values.value!!.two}")
        Log.e("Values", "${values.value!!.three}")
        Log.e("Values", "${values.value!!.four}")
        Log.e("Values", "${values.value!!.five}")
        Log.e("Values", "${values.value!!.six}")
        Log.e("Values", "${values.value!!.threesome}")
        Log.e("Values", "${values.value!!.foursome}")
        Log.e("Values", "${values.value!!.fullHouse}")
        Log.e("Values", "${values.value!!.bigStreet}")
        Log.e("Values", "${values.value!!.littleStreet}")
        Log.e("Values", "${values.value!!.kniffel}")
        Log.e("Values", "${values.value!!.chance}")

        Log.e("GameOver", "${isGameOver.value}")
    }

    // setzt das übergebene Paar auf true oder false, je nach dem was übergeben wird

    fun changeOneBoolean(pair: Pair<Int, Boolean>) {

        _values.value!!.one = pair
    }

    fun changeTwoBoolean(pair: Pair<Int, Boolean>) {

        _values.value!!.two = pair
    }

    fun changeThreeBoolean(pair: Pair<Int, Boolean>) {

        _values.value!!.three = pair
    }

    fun changeFourBoolean(pair: Pair<Int, Boolean>) {

        _values.value!!.four = pair
    }

    fun changeFiveBoolean(pair: Pair<Int, Boolean>) {

        _values.value!!.five = pair
    }

    fun changeSixBoolean(pair: Pair<Int, Boolean>) {

        _values.value!!.six = pair
    }

    fun changeThreesomeBoolean(pair: Pair<Int, Boolean>) {

        _values.value!!.threesome = pair
    }

    fun changeFoursomeBoolean(pair: Pair<Int, Boolean>) {

        _values.value!!.foursome = pair
    }

    fun changeFullHouseBoolean(pair: Pair<Int, Boolean>) {

        _values.value!!.fullHouse = pair
    }

    fun changeBigStreetBoolean(pair: Pair<Int, Boolean>) {

        _values.value!!.bigStreet = pair
    }

    fun changeLittleStreetBoolean(pair: Pair<Int, Boolean>) {

        _values.value!!.littleStreet = pair
    }

    fun changeKniffelBoolean(pair: Pair<Int, Boolean>) {

        _values.value!!.kniffel = pair
    }

    fun changeChanceBoolean(pair: Pair<Int, Boolean>) {

        _values.value!!.chance = pair
    }

    // ändert die Werte der Paare auf 0 wenn sie false sind
    fun resetValues() {

        val values = _values.value ?: KniffelValue()

        if (!values.one.second) one = 0
        if (!values.two.second) two = 0
        if (!values.three.second) three = 0
        if (!values.four.second) four = 0
        if (!values.five.second) five = 0
        if (!values.six.second) six = 0
        if (!values.bonus.second) six = 0
        if (!values.threesome.second) threesome = 0
        if (!values.foursome.second) foursome = 0
        if (!values.fullHouse.second) fullHouse = 0
        if (!values.bigStreet.second) bigStreet = 0
        if (!values.littleStreet.second) littleStreet = 0
        if (!values.kniffel.second) kniffel = 0
        if (!values.chance.second) chance = 0

        _values.value = values
    }

    // rechnet die Punkte für den Spieler zusammen
    fun calculatePoints() {

        val left = values.value!!.one.first + values.value!!.two.first + values.value!!.three.first + values.value!!.four.first + values.value!!.five.first + values.value!!.six.first
        val right = values.value!!.threesome.first + values.value!!.foursome.first + values.value!!.fullHouse.first + values.value!!.bigStreet.first + values.value!!.littleStreet.first + values.value!!.kniffel.first + values.value!!.chance.first

        leftColumn = left
        rightColumn = right

        var total = 0

        if (leftColumn >= 65) {
            bonus = 35
            _values.value!!.bonus = Pair(bonus, true)
        } else {
            bonus = 0
            _values.value!!.bonus = Pair(bonus, false)
        }

        _values.value = _values.value

        total = leftColumn + rightColumn + bonus


        _points.value = total

        Log.e("Spalten", "$leftColumn, $rightColumn")
        Log.e("Bonus", "${values.value!!.bonus.first}")
        Log.e("Points", "${points.value}")
    }

    // wenn alle Werte in values auf true gesetzt wurden wird die Variable isGameOver auf true gesetzt
    fun gameOver() {

        _isGameOver.value = values.value!!.one.second &&
                values.value!!.two.second &&
                values.value!!.three.second &&
                values.value!!.four.second &&
                values.value!!.five.second &&
                values.value!!.six.second &&
                values.value!!.threesome.second &&
                values.value!!.foursome.second &&
                values.value!!.fullHouse.second &&
                values.value!!.bigStreet.second &&
                values.value!!.littleStreet.second &&
                values.value!!.kniffel.second &&
                values.value!!.chance.second
    }

    // setzt alle Werte zurück auf 0 und false
    fun resetAllPoints() {

        _values.value!!.one = Pair(one, false)
        _values.value!!.two = Pair(one, false)
        _values.value!!.three = Pair(one, false)
        _values.value!!.four = Pair(one, false)
        _values.value!!.five = Pair(one, false)
        _values.value!!.six = Pair(one, false)
        _values.value!!.threesome = Pair(one, false)
        _values.value!!.foursome = Pair(one, false)
        _values.value!!.fullHouse = Pair(one, false)
        _values.value!!.bigStreet = Pair(one, false)
        _values.value!!.littleStreet = Pair(one, false)
        _values.value!!.kniffel = Pair(one, false)
        _values.value!!.chance = Pair(one, false)

        resetValues()

        bonus = 0
        leftColumn = 0
        rightColumn = 0

        _points.value = 0
    }
}