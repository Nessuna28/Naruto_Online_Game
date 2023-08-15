package com.example.abschlussaufgabe.ui

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
import com.example.abschlussaufgabe.data.datamodels.modelForFight.CharacterForFight
import com.example.abschlussaufgabe.data.datamodels.modelForFight.fightDataForDatabase.DataPlayer
import com.example.abschlussaufgabe.data.datamodels.modelForFight.dataLists.CharacterListForFight
import com.example.abschlussaufgabe.data.datamodels.modelForFight.dataLists.LocationList
import com.example.abschlussaufgabe.data.datamodels.modelForFight.Location
import com.example.abschlussaufgabe.data.local.PlayerDatabase
import com.example.abschlussaufgabe.data.remote.CharacterApi
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.launch
import android.content.Context
import android.os.CountDownTimer
import android.os.Handler
import android.widget.TextView
import com.example.abschlussaufgabe.R
import com.example.abschlussaufgabe.data.datamodels.modelForFight.Attack
import com.example.abschlussaufgabe.data.datamodels.modelForFight.Jutsu
import com.example.abschlussaufgabe.data.datamodels.modelForFight.UniqueTrait
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale



class MainViewModel(application: Application): AndroidViewModel(application) {

    private val database = PlayerDatabase.getDatabase(application)
    private val repository = AppRepository(CharacterApi, database)

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


    val _imageSettings = MutableLiveData<ImageView>()
    val imageSettings: LiveData<ImageView>
        get() = _imageSettings


    val _imageBackground = MutableLiveData<ImageView>()
    val imageBackground: LiveData<ImageView>
        get() = _imageBackground


    val _materialCard = MutableLiveData<MaterialCardView>()
    val materialCard: LiveData<MaterialCardView>
        get() = _materialCard


    val _userName = MutableLiveData<TextView>()
    val userName: LiveData<TextView>
        get() = _userName


    val _imageProfile = MutableLiveData<MaterialCardView>()
    val imageProfile: LiveData<MaterialCardView>
        get() = _imageProfile



    // für die Datenbank
    private val _userNameEnemy = MutableLiveData<String>()
    val userNameEnemy: LiveData<String>
        get() = _userNameEnemy



    // für die Statistik
    private val _dataList = MutableLiveData<List<DataPlayer>>(repository.dataList.value)
    val dataList: LiveData<List<DataPlayer>>
        get() = _dataList




    // Initialisierung
    init {
        loadCharacters()
        searchCharacter("")
        dataList

        setProfile(Profile(
            R.drawable.profile_picture,
            "Freier",
            "Angelique",
            "Nessuna",
            "13.02.1985",
            "sag ich nicht",
            "pittiplatsch@web.de",
            "123456") )
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

    fun setSound(context: Context, sound: Int) {

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


    // gibt das heutige Datum zurück
    fun getTodayDate(): String {

        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val todayDate = Date()

        return dateFormat.format(todayDate)
    }


    // speichert Daten des Spielers in der Datenbank
    fun updateDatabase(dataPlayer: DataPlayer) {

        Log.e("MainViewModel", "$dataPlayer")
        viewModelScope.launch {
            try {
                repository.insertData(dataPlayer)
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error loading Data to Database: $e")
            }
        }
    }

    fun loadData() {

        viewModelScope.launch {
            try {
                repository.getAllData()
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error loading Data: $e")
            }
        }
    }
}
