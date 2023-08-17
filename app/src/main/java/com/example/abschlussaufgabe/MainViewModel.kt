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
import com.example.abschlussaufgabe.data.local.ProfileDatabase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class MainViewModel(application: Application): AndroidViewModel(application) {

    private val gameDatabase = GameDatabase.getDatabase(application)
    private val profileDatabase = ProfileDatabase.getDatabase(application)
    private val repository = AppRepository(CharacterApi, gameDatabase, profileDatabase)

    private var mediaPlayer: MediaPlayer? = null

    val characters = repository.characters


    private val _profile = MutableLiveData<Profile>()
    val profile: LiveData<Profile>
        get() = _profile




    // für den Homescreen

    val _imageTitle = MutableLiveData<ImageView>()
    val imageTitle: LiveData<ImageView>
        get() = _imageTitle


    val _imageHome = MutableLiveData<ImageView>()
    val imageHome: LiveData<ImageView>
        get() = _imageHome


    val _imageLogout = MutableLiveData<ImageView>()
    val imageLogout: LiveData<ImageView>
        get() = _imageLogout


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



    // für die Datenbank
    private val _dataList = MutableLiveData<List<DataPlayer>>()
    val dataList: LiveData<List<DataPlayer>>
        get() = _dataList


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
        dataList
    }


    // Profil initialisieren
    fun setProfile(profile: Profile) {

        _profile.value = profile
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
                _dataList.value = repository.getAllDataGame()
                countVictorysAndDefeats()
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error loading Data to Database: $e")
            }
        }
    }

    // läd die Daten aus der Datenbank
    fun loadDataGame() {

        viewModelScope.launch {
            try {
                _dataList.value = repository.getAllDataGame()
                countVictorysAndDefeats()
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error loading Data: $e")
            }
        }
    }

    // speichert die Profildaten des Users in der Datenbank
    fun insertDatabaseProfile(profile: Profile) {

        viewModelScope.launch {
            try {
                repository.insertDataProfile(profile)
                _profile.value = repository.getAllDataProfile()
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error loading Data to Database: $e")
            }
        }
    }

    // läd die Daten aus der Datenbank
    fun loadDataProfile() {

        viewModelScope.launch {
            try {
                _profile.value = repository.getAllDataProfile()
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error loading Data: $e")
            }
        }
    }

}