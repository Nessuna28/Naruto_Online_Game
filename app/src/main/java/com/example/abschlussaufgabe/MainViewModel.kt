package com.example.abschlussaufgabe

import android.app.Application
import android.media.MediaPlayer
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
import android.content.Context
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

    private var mediaPlayer: MediaPlayer? = null

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
    val profile = repository.getProfileByEmail(currentUser.value!!.email.toString())

    // für die Spieldaten
    val dataList = repository.dataList

    // LiveData-Variable für die Datenbank

    private val _victory = MutableLiveData<Int>(0)
    val victory: LiveData<Int>
        get() = _victory


    private val _defeat = MutableLiveData<Int>(0)
    val defeat: LiveData<Int>
        get() = _defeat




    // Initialisierung
    init {
        loadCharacters()
        searchCharacter("")
    }


    // Anzeigen und Ausblenden gewisser Images

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

    fun loadCharacters() {

        viewModelScope.launch {
            try {
                repository.getAllCharacters()
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error loading Data: $e")
            }
        }
    }

    fun searchCharacter(name: String) {

        viewModelScope.launch {
            try {
                repository.getCharacter(name)
                println(characters)
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error loading Data: $e")
            }
        }
    }


    // setzt die jeweiligen Sounds und spielt sie ab

    fun playSound(context: Context, sound: Int) {

        mediaPlayer?.release()

        mediaPlayer = MediaPlayer.create(context, sound)

        mediaPlayer?.setOnCompletionListener {
            releaseMediaPlayer()
        }

        // Lautstärke erhöhen (auf 90% der vollen Lautstärke)
        mediaPlayer?.setVolume(0.9f, 0.9f)

        mediaPlayer?.start()
    }

    fun releaseMediaPlayer() {

        mediaPlayer?.release()
        mediaPlayer = null
    }

    fun stopSound() {

        mediaPlayer?.stop()
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

        Log.e("MainViewModel", "$dataPlayer")
        viewModelScope.launch {
            try {
                repository.insertDataGame(dataPlayer)
                countVictorysAndDefeats()
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error loading Data to Database: $e")
            }
        }
    }


    // löscht alle Daten aus der Datenbank
    fun deleteDataGame(dataPlayer: DataPlayer) {

        viewModelScope.launch {
                repository.deleteAllDataGame(dataPlayer)
                _victory.value = 0
                _defeat.value = 0
        }
    }

    // speichert die Profildaten des Users in der Datenbank
    fun insertDatabaseProfile(profile: Profile) {

        viewModelScope.launch {
                repository.insertDataProfile(profile)
        }
    }

    fun changeDataProfile(profile: Profile) {

        viewModelScope.launch {
            repository.updateDataProfile(profile)
        }
        Log.e("Profile", "$profile")
    }

    // löscht alle Profildaten aus der Datenbank
    fun deleteDataProfile(profile: Profile) {

        viewModelScope.launch {
            repository.deleteAllDataProfile(profile)
        }
    }

}
