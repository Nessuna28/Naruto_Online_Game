package com.example.abschlussaufgabe.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.abschlussaufgabe.R


const val TAGKNIFFELVIEWMODEL = "KniffelViewModel"

class KniffelViewModel(application: Application): AndroidViewModel(application) {

    // Listen der Teams

    val teamKakashi = listOf(R.drawable.kakashi_face, R.drawable.naruto_face, R.drawable.sasuke_face, R.drawable.sakura_face, R.drawable.sai_face, R.drawable.kurama)
    val teamGaara = listOf(R.drawable.gaara_face, R.drawable.temari_face, R.drawable.kankuro_face, R.drawable.marionette, R.drawable.subjects, R.drawable.shukaku)

    // LiveData

    private val _selectionTeam = MutableLiveData<List<Int>>()
    val selectionTeam: LiveData<List<Int>>
        get() = _selectionTeam


    private val _randomTeam = MutableLiveData<List<Int>>()
    val randomTeam: LiveData<List<Int>>
        get() = _randomTeam


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


    // Funktionen

    // speichert die Auswahl eines Teams in der LiveData-Variable
    fun selectTeam(selection: List<Int>) {

        _selectionTeam.value = selection
    }

    fun setAttempts(attempts: Int) {

        _attempts.value = attempts
    }

    fun calculateAttempts() {

        _attempts.value = attempts.value!!.minus(1)

        _attempts.value = attempts.value
    }

    fun rollTheDice() {

        _randomTeam.value = selectionTeam.value!!.shuffled()
    }

    fun calculatePoints() {

        val leftColumn = one.value!! + two.value!! + three.value!! + four.value!! + five.value!! + six.value!!
        val rightColumn = threesome.value!! + foursome.value!! + fullHouse.value!! + bigStreet.value!! + littleStreet.value!! + kniffel.value!! + chance.value!!

        val total = leftColumn + rightColumn

        _points.value = total
    }
}