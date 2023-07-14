package com.example.abschlussaufgabe.ui

import android.app.Application
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.abschlussaufgabe.data.AppRepository
import com.example.abschlussaufgabe.data.datamodels.modelForFight.CharacterForFight
import com.example.abschlussaufgabe.data.datamodels.modelForFight.FightDataForDatabase.Player
import com.example.abschlussaufgabe.data.datamodels.modelForFight.characterData.CharacterListForFight
import com.example.abschlussaufgabe.data.local.PlayerDatabase
import com.example.abschlussaufgabe.data.remote.CharacterApi
import kotlinx.coroutines.launch

const val TAGVIEWMODEL = "MainViewModel"


class MainViewModel(application: Application): AndroidViewModel(application) {

    private val database = PlayerDatabase.getDatabase(application)
    private val repository = AppRepository(CharacterApi, database)

    var characters = repository.characters


    private val _characterForFight = MutableLiveData<List<CharacterForFight>>()
    val characterForFight: LiveData<List<CharacterForFight>>
        get() = _characterForFight


    private val _jutsuListForPlayer = MutableLiveData<Map<String, Int>>()
    val jutsuListForPlayer: LiveData<Map<String, Int>>
        get() = _jutsuListForPlayer


    private val _jutsuListForCom = MutableLiveData<Map<String, Int>>()
    val jutsuListForCom: LiveData<Map<String, Int>>
        get() = _jutsuListForCom


    val _player = MutableLiveData<CharacterForFight>()
    val player: LiveData<CharacterForFight>
        get() = _player


    val _imageTitle = MutableLiveData<ImageView>()
    val imageTitle: LiveData<ImageView>
        get() = _imageTitle


    val _imageHome = MutableLiveData<ImageView>()
    val imageHome: LiveData<ImageView>
        get() = _imageHome


    init {
        loadCharacters()
        searchCharacter("")
        _characterForFight.value = CharacterListForFight().characterList
    }


    fun hideImages(image: ImageView) {

        image.visibility = View.GONE
    }

    fun showImages(image: ImageView) {

        image.visibility = View.VISIBLE
    }

    // Alles für die Charakterinformationen

    fun loadCharacters() {

        viewModelScope.launch {
            try {
                repository.getAllCharacters()
            } catch (e: Exception) {
                Log.e(TAGVIEWMODEL, "Error loading Data: $e")
            }
        }
    }

    fun searchCharacter(name: String) {

        viewModelScope.launch {
            try {
                repository.getCharacter(name)
                println(characters)
            } catch (e: Exception) {
                Log.e(TAGVIEWMODEL, "Error loading Data: $e")
            }
        }
    }


    // Alles für das Kampfgeschehen

    fun updateDatabase(player: Player) {

        viewModelScope.launch {
            try {
                repository.insertData(player)
            } catch (e: Exception) {
                Log.e(TAGVIEWMODEL, "Error loading Data from Database: $e")
            }
        }
    }

    fun setJutsuForPlayer(jutsu: Map<String, Int>) {

        _jutsuListForPlayer.value = jutsu
    }
}