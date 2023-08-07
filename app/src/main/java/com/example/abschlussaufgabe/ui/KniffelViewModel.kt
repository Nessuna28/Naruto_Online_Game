package com.example.abschlussaufgabe.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.abschlussaufgabe.data.datamodels.modelForKniffel.Dice
import com.example.abschlussaufgabe.data.datamodels.modelForKniffel.DiceSide
import com.example.abschlussaufgabe.data.datamodels.modelForKniffel.KniffelValue
import com.example.abschlussaufgabe.data.datamodels.modelForKniffel.dataList.DiceList


const val TAGKNIFFELVIEWMODEL = "KniffelViewModel"

class KniffelViewModel(application: Application): AndroidViewModel(application) {

    // LiveData-Variablen

    private val _diceList = MutableLiveData<List<Dice>>()
    val diceList: LiveData<List<Dice>>
        get() = _diceList


    private val _selectedDice = MutableLiveData<Dice>()
    val selectedDice: LiveData<Dice>
        get() = _selectedDice


    private val _randomDice1 = MutableLiveData<DiceSide>()
    val randomDice1: LiveData<DiceSide>
        get() = _randomDice1


    private val _randomDice2 = MutableLiveData<DiceSide>()
    val randomDice2: LiveData<DiceSide>
        get() = _randomDice2


    private val _randomDice3 = MutableLiveData<DiceSide>()
    val randomDice3: LiveData<DiceSide>
        get() = _randomDice3


    private val _randomDice4 = MutableLiveData<DiceSide>()
    val randomDice4: LiveData<DiceSide>
        get() = _randomDice4


    private val _randomDice5 = MutableLiveData<DiceSide>()
    val randomDice5: LiveData<DiceSide>
        get() = _randomDice5


    private val _attempts = MutableLiveData(3)
    val attempts: LiveData<Int>
        get() = _attempts


    private val _points = MutableLiveData<Int>()
    val points: LiveData<Int>
        get() = _points


    private val _values = MutableLiveData<KniffelValue>()
    val values: LiveData<KniffelValue>
        get() = _values


    private val _randomList = MutableLiveData<List<DiceSide>>()
    val randomList: LiveData<List<DiceSide>>
        get() = _randomList


    // Variablen der Punkte für die linke Spalte und für die rechte Spalte

    private var leftColumn: Int = 0

    private var rightColumn: Int = 0

    // Variablen der einzelnen Werte

    private var one: Int = 0
    private var two: Int = 0
    private var three: Int = 0
    private var four: Int = values.value?.four?.first ?: 0
    private var five: Int = values.value?.five?.first ?: 0
    private var six: Int = values.value?.six?.first ?: 0
    private var bonus: Int = values.value?.bonus?.first ?: 0
    private var threesome: Int = values.value?.threesome?.first ?: 0
    private var foursome: Int = values.value?.foursome?.first ?: 0
    private var fullHouse: Int = values.value?.fullHouse?.first ?: 0
    private var bigStreet: Int = values.value?.bigStreet?.first ?: 0
    private var littleStreet: Int = values.value?.littleStreet?.first ?: 0
    private var kniffel: Int = values.value?.kniffel?.first ?: 0
    private var chance: Int = values.value?.chance?.first ?: 0

    // Liste mit Würfeln die der Spieler behalten möchte
    private val _diceToKeep = MutableLiveData<List<DiceSide>>()
    val diceToKeep: LiveData<List<DiceSide>>
        get() = _diceToKeep


    private var _listOfRandomDice: MutableList<MutableLiveData<DiceSide>> = mutableListOf(_randomDice1, _randomDice2, _randomDice3, _randomDice4, _randomDice5)
    val listOfRandomDice: MutableList<MutableLiveData<DiceSide>>
        get() = _listOfRandomDice




    // Initialisierung
    init {
        _diceList.value = DiceList().diceList
        initValues()
    }

    // Funktionen

    fun initValues() {

        val kniffelValue = _values.value ?: KniffelValue()

        kniffelValue.one = Pair(one, false)
        kniffelValue.two = Pair(two, false)
        kniffelValue.three = Pair(three, false)
        kniffelValue.four = Pair(four, false)
        kniffelValue.five = Pair(five, false)
        kniffelValue.six = Pair(six, false)
        kniffelValue.threesome = Pair(threesome, false)
        kniffelValue.foursome = Pair(foursome, false)
        kniffelValue.fullHouse = Pair(fullHouse, false)
        kniffelValue.bigStreet = Pair(bigStreet, false)
        kniffelValue.littleStreet = Pair(littleStreet, false)
        kniffelValue.kniffel = Pair(kniffel, false)
        kniffelValue.chance = Pair(chance, false)

        _values.value = kniffelValue
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
    // sucht für die Würfel zufällige Werte aus
    // erstellt danach eine Liste nur mit den Werten der zufälligen Würfel
    fun rollTheDice() {

        val list = mutableListOf<DiceSide>()
        list.add(selectedDice.value!!.diceSide1)
        list.add(selectedDice.value!!.diceSide2)
        list.add(selectedDice.value!!.diceSide3)
        list.add(selectedDice.value!!.diceSide4)
        list.add(selectedDice.value!!.diceSide5)
        list.add(selectedDice.value!!.diceSide6)

        _randomList.value = list

        listOfRandomDice.forEach { liveData ->
            liveData.value = randomList.value!!.random()
        }
    }

    // speichert die Werte in den LiveData-Variablen
    fun setValueDice(listOfRandomDice: MutableList<MutableLiveData<DiceSide>>) {

        val valueList: List<Int> = listOfRandomDice.flatMap { diceLiveData ->
            diceLiveData.value?.let { listOf(it.value) } ?: emptyList()
        }

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


            _values.value = _values.value
    }


    fun setCheckTextViews(pair: Pair<Int, Boolean>) {

        val updatedValue = _values.value!!

        when (pair.first) {
            1 -> updatedValue.one = pair.copy(second = true)
            2 -> updatedValue.two = pair.copy(second = true)
            3 -> updatedValue.three = pair.copy(second = true)
            4 -> updatedValue.four = pair.copy(second = true)
            6 -> updatedValue.five = pair.copy(second = true)
            7 -> updatedValue.six = pair.copy(second = true)
            8 -> updatedValue.threesome = pair.copy(second = true)
            9 -> updatedValue.foursome = pair.copy(second = true)
            10 -> updatedValue.fullHouse = pair.copy(second = true)
            11 -> updatedValue.bigStreet = pair.copy(second = true)
            12 -> updatedValue.littleStreet = pair.copy(second = true)
            13 -> updatedValue.kniffel = pair.copy(second = true)
            14 -> updatedValue.chance = pair.copy(second = true)
        }
        _values.value = updatedValue
    }

    fun resetValues() {

        val kniffelValue = _values.value ?: KniffelValue()

        // ändert die Werte der Paare auf 0 wenn sie false sind
        val updatedOne = if (kniffelValue.one.second) kniffelValue.one else Pair(0, false)
        val updatedTwo = if (kniffelValue.two.second) kniffelValue.two else Pair(0, false)
        val updatedThree = if (kniffelValue.three.second) kniffelValue.three else Pair(0, false)
        val updatedFour = if (kniffelValue.four.second) kniffelValue.four else Pair(0, false)
        val updatedFive = if (kniffelValue.five.second) kniffelValue.five else Pair(0, false)
        val updatedSix = if (kniffelValue.six.second) kniffelValue.six else Pair(0, false)
        val updatedThreesome = if (kniffelValue.threesome.second) kniffelValue.threesome else Pair(0, false)
        val updatedFoursome = if (kniffelValue.foursome.second) kniffelValue.foursome else Pair(0, false)
        val updatedFullHouse = if (kniffelValue.fullHouse.second) kniffelValue.fullHouse else Pair(0, false)
        val updatedBigStreet = if (kniffelValue.bigStreet.second) kniffelValue.bigStreet else Pair(0, false)
        val updatedLittleStreet = if (kniffelValue.littleStreet.second) kniffelValue.littleStreet else Pair(0, false)
        val updatedKniffel = if (kniffelValue.kniffel.second) kniffelValue.kniffel else Pair(0, false)
        val updatedChance = if (kniffelValue.chance.second) kniffelValue.chance else Pair(0, false)


        // Aktualisierte Paare zurück in das KniffelValue-Objekt setzen
        kniffelValue.one = updatedOne
        kniffelValue.two = updatedTwo
        kniffelValue.three = updatedThree
        kniffelValue.four = updatedFour
        kniffelValue.five = updatedFive
        kniffelValue.six = updatedSix
        kniffelValue.threesome = updatedThreesome
        kniffelValue.foursome = updatedFoursome
        kniffelValue.fullHouse = updatedFullHouse
        kniffelValue.bigStreet = updatedBigStreet
        kniffelValue.littleStreet = updatedLittleStreet
        kniffelValue.kniffel = updatedKniffel
        kniffelValue.chance = updatedChance


        _values.value = kniffelValue
    }



    // rechnet die Punkte für den Spieler zusammen
    fun calculatePoints() {

        val left = one + two + three + four + five + six
        val right = threesome + foursome + fullHouse + bigStreet + littleStreet + kniffel + chance

        leftColumn = left
        rightColumn = right

        var total = 0

        if (leftColumn >= 65) {
            bonus = 35
             total += leftColumn + rightColumn + bonus
        } else {
            total += leftColumn + rightColumn
        }

        _points.value = total
    }

    fun keepDice(dice: DiceSide) {

        _diceToKeep.value?.get(dice.value)
    }

}