package com.example.abschlussaufgabe.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.abschlussaufgabe.data.datamodels.modelForKniffel.Dice
import com.example.abschlussaufgabe.data.datamodels.modelForKniffel.DiceSide
import com.example.abschlussaufgabe.data.datamodels.modelForKniffel.KniffelValue
import com.example.abschlussaufgabe.data.datamodels.modelForKniffel.dataList.DiceList
import kotlin.collections.MutableList



class KniffelViewModel(application: Application): AndroidViewModel(application) {

    // LiveData-Variablen

    private val _diceList = MutableLiveData<List<Dice>>()
    val diceList: LiveData<List<Dice>>
        get() = _diceList


    private val _selectedDice = MutableLiveData<Dice>()
    val selectedDice: LiveData<Dice>
        get() = _selectedDice


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


    // Variablen für die zufälligen Würfelseiten der 5 Würfel zum würfeln

    private lateinit var diceSideRolledDice1: DiceSide
    private lateinit var diceSideRolledDice2: DiceSide
    private lateinit var diceSideRolledDice3: DiceSide
    private lateinit var diceSideRolledDice4: DiceSide
    private lateinit var diceSideRolledDice5: DiceSide

    // Liste der Variablen der Würfelseiten
    private val diceSideList = listOf(diceSideRolledDice1, diceSideRolledDice2, diceSideRolledDice3, diceSideRolledDice4, diceSideRolledDice5)


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
        list.add(selectedDice.value!!)
        list.add(selectedDice.value!!)
        list.add(selectedDice.value!!)
        list.add(selectedDice.value!!)
        list.add(selectedDice.value!!)


        // jedem Würfel zum würfeln wird ein Würfel aus der obrigen Liste zugewiesen
        _rolledDice1.value = list[0]
        _rolledDice2.value = list[1]
        _rolledDice3.value = list[2]
        _rolledDice4.value = list[3]
        _rolledDice5.value = list[4]
    }

    // sucht eine zufällige Würfelseite aus für alle 5 Würfel
    fun rollTheDice() {

        diceSideRolledDice1 = rolledDice1.value!!.diceSideList.random()
        diceSideRolledDice2 = rolledDice2.value!!.diceSideList.random()
        diceSideRolledDice3 = rolledDice3.value!!.diceSideList.random()
        diceSideRolledDice4 = rolledDice4.value!!.diceSideList.random()
        diceSideRolledDice5 = rolledDice5.value!!.diceSideList.random()
    }

    // setzt den Boolean der Würfel in der Liste auf false
    fun setRolledDiceOfFalse() {

        diceSideList.forEach { diceSide ->
                diceSide.toKeep = false
        }
    }

    // ändert den Wert des übergeben Würfels auf true oder false, je nach dem was übergeben wird
    fun diceToKeep(dice: Dice, check: Boolean) {

        when (dice) {
            rolledDice1.value -> diceSideRolledDice1.toKeep = check
            rolledDice2.value -> diceSideRolledDice2.toKeep = check
            rolledDice3.value -> diceSideRolledDice3.toKeep = check
            rolledDice4.value -> diceSideRolledDice4.toKeep = check
            rolledDice5.value -> diceSideRolledDice5.toKeep = check
        }
    }

    // speichert die Werte in den LiveData-Variablen
    fun setValueDice() {

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


            _values.value = _values.value
    }

    // setzt das übergebene Paar auf true
    fun setCheckTextViews(pair: Pair<Int, Boolean>) {

        val updatedValue = _values.value!!

        when (pair) {
            updatedValue.one -> updatedValue.one = pair.copy(second = true)
            updatedValue.two -> updatedValue.two = pair.copy(second = true)
            updatedValue.three -> updatedValue.three = pair.copy(second = true)
            updatedValue.four -> updatedValue.four = pair.copy(second = true)
            updatedValue.five -> updatedValue.five = pair.copy(second = true)
            updatedValue.six -> updatedValue.six = pair.copy(second = true)
            updatedValue.threesome -> updatedValue.threesome = pair.copy(second = true)
            updatedValue.foursome -> updatedValue.foursome = pair.copy(second = true)
            updatedValue.fullHouse -> updatedValue.fullHouse = pair.copy(second = true)
            updatedValue.bigStreet -> updatedValue.bigStreet = pair.copy(second = true)
            updatedValue.littleStreet -> updatedValue.littleStreet = pair.copy(second = true)
            updatedValue.kniffel -> updatedValue.kniffel = pair.copy(second = true)
            updatedValue.chance -> updatedValue.chance = pair.copy(second = true)
        }
        _values.value = updatedValue
    }

    // ändert die Werte der Paare auf 0 wenn sie false sind
    fun resetValues() {

        val kniffelValue = _values.value ?: KniffelValue()

        if (!kniffelValue.one.second) one = 0
        if (!kniffelValue.two.second) two = 0
        if (!kniffelValue.three.second) three = 0
        if (!kniffelValue.four.second) four = 0
        if (!kniffelValue.five.second) five = 0
        if (!kniffelValue.six.second) six = 0
        if (!kniffelValue.threesome.second) threesome = 0
        if (!kniffelValue.foursome.second) foursome = 0
        if (!kniffelValue.fullHouse.second) fullHouse = 0
        if (!kniffelValue.bigStreet.second) bigStreet = 0
        if (!kniffelValue.littleStreet.second) littleStreet = 0
        if (kniffelValue.kniffel.second) kniffel = 0
        if (!kniffelValue.chance.second) chance = 0

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

}