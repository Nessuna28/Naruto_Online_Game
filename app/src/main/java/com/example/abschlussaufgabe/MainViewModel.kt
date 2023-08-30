package com.example.abschlussaufgabe

import android.app.Application
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.abschlussaufgabe.data.AppRepository
import com.example.abschlussaufgabe.data.datamodels.modelForFight.fightDataForDatabase.DataPlayer
import com.example.abschlussaufgabe.data.local.GameDatabase
import com.example.abschlussaufgabe.data.remote.CharacterApi
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.launch
import android.widget.TextView
import com.example.abschlussaufgabe.data.datamodels.modelsApi.Character
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class MainViewModel(application: Application): AndroidViewModel(application) {

    private val gameDatabase = GameDatabase.getDatabase(application)
    private val repository = AppRepository(CharacterApi, gameDatabase)


    // LiveData-Variable für Api
    private val _characters = MutableLiveData(repository.characters)
    val characters: LiveData<List<Character>>
        get() = _characters


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


    val _cVImageProfile = MutableLiveData<MaterialCardView>()
    val cvImageProfile: LiveData<MaterialCardView>
        get() = _cVImageProfile

    val imageProfile = MutableLiveData<ImageView>()


    private val _userNameEnemy = MutableLiveData<String>()
    val userNameEnemy: LiveData<String>
        get() = _userNameEnemy



    // für die Spieldaten
    var dataList = repository.dataList



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
            _characters.value = repository.characters
        }
    }

    // sucht einen Charakter anhand des Namens (leider muss man den kompletten Namen eingeben bevor etwas angezeigt wird)
    fun searchCharacter(name: String) {

        viewModelScope.launch {
            repository.getCharacter(name)
            _characters.value = repository.characters
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


    // für die Datenbanken

    // speichert die Spieldaten des Spielers in der Datenbank
    fun insertDataGame(dataPlayer: DataPlayer) {

        viewModelScope.launch {
            repository.insertDataGame(dataPlayer)
        }
    }

    // löscht die üergebenen Daten aus der Datenbank
    fun deleteDataGame(dataPlayer: DataPlayer) {

        viewModelScope.launch {
            repository.deleteDataGame(dataPlayer)
        }
    }

    fun deleteAllDataGame() {

        viewModelScope.launch {
            repository.deleteAllDataGame()
        }
    }

}
