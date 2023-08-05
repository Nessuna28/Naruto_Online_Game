package com.example.abschlussaufgabe.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.abschlussaufgabe.data.datamodels.modelForKniffel.Dice
import com.example.abschlussaufgabe.data.datamodels.modelForKniffel.dataList.DiceList


const val TAGKNIFFELVIEWMODEL = "KniffelViewModel"

class KniffelViewModel(application: Application): AndroidViewModel(application) {

    // LiveData

    private val _diceList = MutableLiveData<List<Dice>>()
    val diceList: LiveData<List<Dice>>
        get() = _diceList


    private val _selectionDice = MutableLiveData<Dice>()
    val selectionDice: LiveData<Dice>
        get() = _selectionDice


    private val _randomDice1 = MutableLiveData<Int>()
    val randomDice1: LiveData<Int>
        get() = _randomDice1


    private val _randomDice2 = MutableLiveData<Int>()
    val randomDice2: LiveData<Int>
        get() = _randomDice2


    private val _randomDice3 = MutableLiveData<Int>()
    val randomDice3: LiveData<Int>
        get() = _randomDice3


    private val _randomDice4 = MutableLiveData<Int>()
    val randomDice4: LiveData<Int>
        get() = _randomDice4


    private val _randomDice5 = MutableLiveData<Int>()
    val randomDice5: LiveData<Int>
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


    // Initialisierung
    init {
        _diceList.value = DiceList().diceList
    }

    // Funktionen

    // speichert die Auswahl eines Teams in der LiveData-Variable
    fun selectTeam(selection: Dice) {

        _selectionDice.value = selection
    }

    fun setAttempts(attempts: Int) {

        _attempts.value = attempts
    }

    fun calculateAttempts() {

        _attempts.value = attempts.value!!.minus(1)

        _attempts.value = attempts.value
    }

    fun rollTheDice() {

        val imageList = listOf(
            selectionDice.value!!.image1,
            selectionDice.value!!.image2,
            selectionDice.value!!.image3,
            selectionDice.value!!.image4,
            selectionDice.value!!.image5,
            selectionDice.value!!.image6
        )

        _randomDice1.value = imageList.random()
        _randomDice2.value = imageList.random()
        _randomDice3.value = imageList.random()
        _randomDice4.value = imageList.random()
        _randomDice5.value = imageList.random()
    }

    fun calculatePoints() {

        val leftColumn = one.value!! + two.value!! + three.value!! + four.value!! + five.value!! + six.value!!
        val rightColumn = threesome.value!! + foursome.value!! + fullHouse.value!! + bigStreet.value!! + littleStreet.value!! + kniffel.value!! + chance.value!!

        val total = leftColumn + rightColumn

        _points.value = total
    }
}