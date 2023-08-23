package com.example.abschlussaufgabe

import android.app.Application
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.abschlussaufgabe.data.AppRepository
import com.example.abschlussaufgabe.data.datamodels.Profile
import com.example.abschlussaufgabe.data.datamodels.modelForFight.fightDataForDatabase.DataPlayer
import com.example.abschlussaufgabe.data.local.GameDatabase
import com.example.abschlussaufgabe.data.remote.CharacterApi
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.launch
import android.widget.TextView
import com.example.abschlussaufgabe.data.datamodels.modelsApi.Character
import com.google.firebase.auth.FirebaseUser
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class MainViewModel(application: Application): AndroidViewModel(application) {

    private val gameDatabase = GameDatabase.getDatabase(application)
    private val repository = AppRepository(CharacterApi, gameDatabase)
    private val authViewModel = AuthViewModel()


    // LiveData-Variable für Api
    private val _characters = MutableLiveData(repository.characters)
    val characters: LiveData<List<Character>>
        get() = _characters


    // LiveData-Variable für Firebase
    private val _currentUser = MutableLiveData<FirebaseUser>(authViewModel.currentUser.value)
    val currentUser: LiveData<FirebaseUser>
        get() = _currentUser


    // für den Homescreen

    val _imageTitle = MutableLiveData<ImageView>()
    val imageTitle: LiveData<ImageView>
        get() = _imageTitle


    val _imageHome = MutableLiveData<ImageView>()
    val imageHome: LiveData<ImageView>
        get() = _imageHome


    val _imageSettings = MutableLiveData<ImageView>()
    val imageSettings: LiveData<ImageView>
        get() = _imageSettings


    val _imageBackground = MutableLiveData<ImageView>()
    val imageBackground: LiveData<ImageView>
        get() = _imageBackground


    val _materialCard = MutableLiveData<MaterialCardView>()
    val materialCard: LiveData<MaterialCardView>
        get() = _materialCard


    val _tvUserName = MutableLiveData<TextView>()
    val tvUserName: LiveData<TextView>
        get() = _tvUserName


    val _imageProfile = MutableLiveData<MaterialCardView>()
    val imageProfile: LiveData<MaterialCardView>
        get() = _imageProfile


    private val _userNameEnemy = MutableLiveData<String>()
    val userNameEnemy: LiveData<String>
        get() = _userNameEnemy



    // Variablen der Datenbank

    // für das Profil
    private val currentUserEmail: String?
        get() = authViewModel.currentUser.value?.email?.toString()

    val profile = currentUserEmail?.let { email ->
        repository.getProfileByEmail(email)
    }


    // für die Spieldaten
    val dataList = repository.dataList


    // LiveData-Variable für Siege und Niederlagen für die Datenbank

    private val _victory = MutableLiveData(0)
    val victory: LiveData<Int>
        get() = _victory


    private val _defeat = MutableLiveData(0)
    val defeat: LiveData<Int>
        get() = _defeat




    // Initialisierung
    init {
        loadCharacters()
        searchCharacter("")
    }


    // Anzeigen und Ausblenden gewisser Images, CardViews oder TextViews

    fun hideImages(image: ImageView) {

        image.visibility = View.GONE
    }

    fun showImages(image: ImageView) {

        image.visibility = View.VISIBLE
    }

    fun hideMaterialCard(card: MaterialCardView) {

        card.visibility = View.INVISIBLE
    }

    fun showMaterialCard(card: MaterialCardView) {

        card.visibility = View.VISIBLE
    }

    fun hideTextView(textView: TextView) {

        textView.visibility = View.INVISIBLE
    }

    fun showTextView(textView: TextView) {

        textView.visibility = View.VISIBLE
    }


    // Alles für die Charakterinformationen

    // zeigt alle Charaktere aus der Liste an
    fun loadCharacters() {

        viewModelScope.launch {
            repository.getAllCharacters()
        }
    }

    // sucht einen Charakter anhand des Namens (leider muss man den kompletten Namen eingeben bevor etwas angezeigt wird)
    fun searchCharacter(name: String) {

        viewModelScope.launch {
            repository.getCharacter(name)
            println(characters)
        }
    }


    // speichert den Usernamen des Gegners
    fun setUserNameEnemy(userName: String) {

        _userNameEnemy.value = userName
    }


    // gibt das heutige Datum mit Uhrzeit zurück
    fun getTodayDate(): String {

        val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
        val todayDate = Date()

        return dateFormat.format(todayDate)
    }


    // zählt die Siege und Niederlagen
    fun countVictorysAndDefeats() {

        if (victory.value!! + defeat.value!! != dataList.value!!.size) {
            Log.e("ViewModel", "${dataList.value!!.size}")
            dataList.value!!.forEach {
                if (it.result == "Sieg") {
                    _victory.value = _victory.value?.plus(1)
                } else if (it.result == "Niederlage") {
                    _defeat.value = _defeat.value?.plus(1)
                }
            }
        }
    }

    // für die Datenbanken

    // speichert die Spieldaten des Spielers in der Datenbank
    fun insertDatabaseGame(dataPlayer: DataPlayer) {

        viewModelScope.launch {
            repository.insertDataGame(dataPlayer)
            countVictorysAndDefeats()
        }
    }

    // löscht die üergebenen Daten aus der Datenbank
    fun deleteDataGame(dataPlayer: DataPlayer) {

        viewModelScope.launch {
            repository.deleteDataGame(dataPlayer)
            countVictorysAndDefeats()
        }
    }

    // speichert die übergebenen Profildaten des Users in der Datenbank
    fun insertDatabaseProfile(profile: Profile) {

        viewModelScope.launch {
                repository.insertDataProfile(profile)
        }
    }

    // ändert die Daten des übergebenen Profils
    fun changeDataProfile(profile: Profile) {

        viewModelScope.launch {
            repository.updateDataProfile(profile)
        }
        Log.e("Profile", "$profile")
    }

    // löscht die Profildaten des übergebenen Profils aus der Datenbank
    fun deleteDataProfile(profile: Profile) {

        viewModelScope.launch {
            repository.deleteAllDataProfile(profile)
        }
    }

}
