package com.example.abschlussaufgabe.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.abschlussaufgabe.data.datamodels.modelForKniffel.Dice
import com.example.abschlussaufgabe.data.datamodels.modelForKniffel.DiceSide
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


    private val _one = MutableLiveData(0)
    val one: LiveData<Int>
        get() = _one


    private val _two = MutableLiveData(0)
    val two: LiveData<Int>
        get() = _two


    private val _three = MutableLiveData(0)
    val three: LiveData<Int>
        get() = _three


    private val _four = MutableLiveData(0)
    val four: LiveData<Int>
        get() = _four


    private val _five = MutableLiveData(0)
    val five: LiveData<Int>
        get() = _five


    private val _six = MutableLiveData(0)
    val six: LiveData<Int>
        get() = _six


    private val _threesome = MutableLiveData(0)
    val threesome: LiveData<Int>
        get() = _threesome


    private val _foursome = MutableLiveData(0)
    val foursome: LiveData<Int>
        get() = _foursome


    private val _fullHouse = MutableLiveData(0)
    val fullHouse: LiveData<Int>
        get() = _fullHouse


    private val _bigStreet = MutableLiveData(0)
    val bigStreet: LiveData<Int>
        get() = _bigStreet


    private val _littleStreet = MutableLiveData(0)
    val littleStreet: LiveData<Int>
        get() = _littleStreet


    private val _kniffel = MutableLiveData(0)
    val kniffel: LiveData<Int>
        get() = _kniffel


    private val _chance = MutableLiveData(0)
    val chance: LiveData<Int>
        get() = _chance


    private val _checkDice1 = MutableLiveData(false)
    val checkDice1: LiveData<Boolean>
        get() = _checkDice1


    private val _checkDice2 = MutableLiveData(false)
    val checkDice2: LiveData<Boolean>
        get() = _checkDice2


    private val _checkDice3 = MutableLiveData(false)
    val checkDice3: LiveData<Boolean>
        get() = _checkDice3


    private val _checkDice4 = MutableLiveData(false)
    val checkDice4: LiveData<Boolean>
        get() = _checkDice4


    private val _checkDice5 = MutableLiveData(false)
    val checkDice5: LiveData<Boolean>
        get() = _checkDice5


    private val _randomList = MutableLiveData<List<DiceSide>>()
    val randomList: LiveData<List<DiceSide>>
        get() = _randomList


    private val _leftColumn = MutableLiveData<Int>()
    val leftColumn: LiveData<Int>
        get() = _leftColumn


    private val _rightColumn = MutableLiveData<Int>()
    val rightColumn: LiveData<Int>
        get() = _rightColumn


    private val valueList: MutableList<Int> = mutableListOf()



    // Liste mit Würfeln die der Spieler behalten möchte
    var diceToKeep: MutableList<DiceSide> = mutableListOf()


    private var liveDataList = mutableListOf(
        _one, _two, _three, _four, _five, _six,
        _threesome, _foursome, _fullHouse, _bigStreet, _littleStreet, _kniffel,
        _chance
    )


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

        _randomList.value = list


        _randomDice1.value = randomList.value!!.random()
        _randomDice2.value = randomList.value!!.random()
        _randomDice3.value = randomList.value!!.random()
        _randomDice4.value = randomList.value!!.random()
        _randomDice5.value = randomList.value!!.random()

        valueList.add(randomDice1.value!!.value)
        valueList.add(randomDice2.value!!.value)
        valueList.add(randomDice3.value!!.value)
        valueList.add(randomDice4.value!!.value)
        valueList.add(randomDice5.value!!.value)
    }

    // speichert die Werte in den LiveData-Variablen
    fun setValueDice() {

        // die einzelnen Augen zusammen zählen
        for (i in valueList) {
            if (i == 1) {
                _one.value = one.value!!.plus(i)
            } else if (i == 2) {
                _two.value = two.value!!.plus(i)
            } else if (i == 3) {
                _three.value = three.value!!.plus(i)
            } else if (i == 4) {
                _four.value = four.value!!.plus(i)
            } else if (i == 5) {
                _five.value = five.value!!.plus(i)
            } else if (i == 6) {
                _six.value = six.value!!.plus(i)
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
                _fullHouse.value = 25
            }
        }

        // den 3er zählen
        if (numberFrequency.any { it.value >= 3 }) {
            _threesome.value = valueList.sum()
        }

        // den 4er zählen
        if (numberFrequency.any { it.value >= 4 }) {
            _threesome.value = valueList.sum()
        }

        // die kleine Straße zählen
        val numbersForLittleStreet1 = listOf(2, 3, 4, 5)
        val numbersForLittleStreet2 = listOf(1, 2, 3, 4)
        val numbersForLittleStreet3 = listOf(3, 4, 5, 6)

        val littleStreet1 = valueList.containsAll(numbersForLittleStreet1)
        val littleStreet2 = valueList.containsAll(numbersForLittleStreet2)
        val litlleStreet3 = valueList.containsAll(numbersForLittleStreet3)

        if (littleStreet1 || littleStreet2 || litlleStreet3) {
            _littleStreet.value = 30
        }

        // die große Straße zählen
        val numbersForBigStreet1 = listOf(1, 2, 3, 4, 5)
        val numbersForBigStreet2 = listOf(2, 3, 4, 5, 6)

        val bigStreet1 = valueList.containsAll(numbersForBigStreet1)
        val bigStreet2 = valueList.containsAll(numbersForBigStreet2)

        if (bigStreet1 || bigStreet2) {
            _littleStreet.value = 40
        }

        // einen Kniffel zählen
        val allNumbersSame = valueList.all { it == valueList[0] }

        if (allNumbersSame) {
            _kniffel.value = 50
        }

        // alle Augen für die Chance zählen
        _chance.value = valueList.sum()
    }

    fun resetValues(nr : Int) {

        if (nr == 1) {
            liveDataList.remove(_one)
        }

        if (nr == 2) {
            liveDataList.remove(_two)
        }

        if (nr == 3) {
            liveDataList.remove(_three)
        }

        if (nr == 4) {
            liveDataList.remove(_four)
        }

        if (nr == 5) {
            liveDataList.remove(_five)
        }

        if (nr == 6) {
            liveDataList.remove(_six)
        }

        if (nr == 7) {
            liveDataList.remove(_threesome)
        }

        if (nr == 8) {
            liveDataList.remove(_foursome)
        }

        if (nr == 9) {
            liveDataList.remove(_fullHouse)
        }

        if (nr == 10) {
            liveDataList.remove(_bigStreet)
        }

        if (nr == 11) {
            liveDataList.remove(_littleStreet)
        }

        if (nr == 12) {
            liveDataList.remove(_kniffel)
        }

        if (nr == 13) {
            liveDataList.remove(_chance)
        }


        liveDataList.forEach { liveData ->
            liveData.value = 0
        }
    }

    // rechnet die Punkte für den Spieler zusammen
    fun calculatePoints() {

        val left = one.value!! + two.value!! + three.value!! + four.value!! + five.value!! + six.value!!
        val right = threesome.value!! + foursome.value!! + fullHouse.value!! + bigStreet.value!! + littleStreet.value!! + kniffel.value!! + chance.value!!

        _leftColumn.value = left
        _rightColumn.value = right

        var total = 0

        if (leftColumn.value!! >= 65) {
             total += leftColumn.value!! + rightColumn.value!! + 35
        } else if (leftColumn.value!! < 65) {
            total += leftColumn.value!! + rightColumn.value!!
        }

        _points.value = total
    }
}