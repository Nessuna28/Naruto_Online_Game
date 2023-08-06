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

    // LiveData

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


    private val _leftColumn = MutableLiveData<Int>()
    val leftColumn: LiveData<Int>
        get() = _leftColumn


    private val _rightColumn = MutableLiveData<Int>()
    val rightColumn: LiveData<Int>
        get() = _rightColumn



    // Liste mit Würfeln die der Spieler behalten möchte
    private val _diceToKeep = MutableLiveData<List<DiceSide>>()
    val diceToKeep: LiveData<List<DiceSide>>
        get() = _diceToKeep


    val listOfRandomDice = mutableListOf(_randomDice1, _randomDice2, _randomDice3, _randomDice4, _randomDice5)



    var checkOne = false
    var checkTwo = false
    var checkThree = false
    var checkFour = false
    var checkFive = false
    var checkSix = false
    var checkThreesome = false
    var checkFoursome = false
    var checkFullHouse = false
    var checkBigStreet = false
    var checkLittleStreet = false
    var checkKniffel = false
    var checkChance = false


    // Initialisierung
    init {
        _diceList.value = DiceList().diceList
    }

    // Funktionen

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

        val valueList: List<Int> = listOfRandomDice.flatMap { diceLiveData -> diceLiveData.value?.let { listOf(it.value) } ?: emptyList() }


        if (_values.value == null) {
            _values.value = KniffelValue(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
        }

        // die einzelnen Augen zusammen zählen
        for (i in valueList) {
            if (i == 1 && !checkOne) {
                _values.value?.one = values.value!!.one.plus(i)
            } else if (i == 2 && !checkTwo) {
                _values.value?.two = values.value!!.two.plus(i)
            } else if (i == 3 && !checkThree) {
                _values.value?.three = values.value!!.three.plus(i)
            } else if (i == 4 && !checkFour) {
                _values.value?.four = values.value!!.four.plus(i)
            } else if (i == 5 && !checkFive) {
                _values.value?.five = values.value!!.five.plus(i)
            } else if (i == 6 && !checkSix) {
                _values.value?.six = values.value!!.six.plus(i)
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
                _values.value?.fullHouse = 25
            }
        }

        // den 3er zählen
        if (numberFrequency.any { it.value >= 3 }) {
            _values.value?.threesome = valueList.sum()
        }

        // den 4er zählen
        if (numberFrequency.any { it.value >= 4 }) {
            _values.value?.foursome = valueList.sum()
        }

        // die kleine Straße zählen
        val numbersForLittleStreet1 = listOf(2, 3, 4, 5)
        val numbersForLittleStreet2 = listOf(1, 2, 3, 4)
        val numbersForLittleStreet3 = listOf(3, 4, 5, 6)

        val littleStreet1 = valueList.containsAll(numbersForLittleStreet1)
        val littleStreet2 = valueList.containsAll(numbersForLittleStreet2)
        val litlleStreet3 = valueList.containsAll(numbersForLittleStreet3)

        if (littleStreet1 || littleStreet2 || litlleStreet3) {
            _values.value?.littleStreet = 30
        }

        // die große Straße zählen
        val numbersForBigStreet1 = listOf(1, 2, 3, 4, 5)
        val numbersForBigStreet2 = listOf(2, 3, 4, 5, 6)

        val bigStreet1 = valueList.containsAll(numbersForBigStreet1)
        val bigStreet2 = valueList.containsAll(numbersForBigStreet2)

        if (bigStreet1 || bigStreet2) {
            _values.value?.bigStreet = 40
        }

        // einen Kniffel zählen
        val allNumbersSame = valueList.all { it == valueList[0] }

        if (allNumbersSame) {
            _values.value?.kniffel= 50
        }

        // alle Augen für die Chance zählen
        _values.value?.chance = valueList.sum()


        _values.value = _values.value
    }

    fun resetValues(nr : Int) {

        //TODO: Wie?
    }

    // rechnet die Punkte für den Spieler zusammen
    fun calculatePoints() {

        val left = values.value!!.one + values.value!!.two + values.value!!.three + values.value!!.four + values.value!!.five + values.value!!.six
        val right = values.value!!.threesome + values.value!!.foursome + values.value!!.fullHouse + values.value!!.bigStreet + values.value!!.littleStreet + values.value!!.kniffel + values.value!!.chance

        _leftColumn.value = left
        _rightColumn.value = right

        var total = 0

        if (leftColumn.value!! >= 65) {
            _values.value!!.bonus = 35
             total += leftColumn.value!! + rightColumn.value!! + values.value!!.bonus
        } else if (leftColumn.value!! < 65) {
            total += leftColumn.value!! + rightColumn.value!!
        }

        _points.value = total
    }

    fun keepDice(dice: DiceSide) {

        _diceToKeep.value?.get(dice.value)
    }
}