package com.example.abschlussaufgabe.ui

import android.app.Application
import android.util.Log
import android.view.View
import android.widget.ImageView
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
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.DeclaredMemberIndex.Empty

const val TAGVIEWMODEL = "MainViewModel"


class MainViewModel(application: Application): AndroidViewModel(application) {

    private val database = PlayerDatabase.getDatabase(application)
    private val repository = AppRepository(CharacterApi, database)

    var characters = repository.characters


    // für die Datenbank
    private val _player = MutableLiveData<CharacterForFight>()
    val player: LiveData<CharacterForFight>
        get() = _player


    // für die Charakterauswahl (CharacterSelectionFragment)

    private val _characterForFight = MutableLiveData<List<CharacterForFight>>()
    val characterForFight: LiveData<List<CharacterForFight>>
        get() = _characterForFight


    private val _imageForPlayer = MutableLiveData<Int>()
    val imageForPlayer: LiveData<Int>
        get() = _imageForPlayer


    private val _image2ForPlayer = MutableLiveData<Int>()
    val image2ForPlayer: LiveData<Int>
        get() = _image2ForPlayer


    private val _characterNameForPlayer = MutableLiveData<String>()
    val characterNameForPlayer: LiveData<String>
        get() = _characterNameForPlayer


    private val _jutsuListForPlayer = MutableLiveData<Map<String, Int>>()
    val jutsuListForPlayer: LiveData<Map<String, Int>>
        get() = _jutsuListForPlayer


    private val _enemy = MutableLiveData<CharacterForFight>()
    val enemy: LiveData<CharacterForFight>
        get() = _enemy


    private val _imageForEnemy = MutableLiveData<Int>()
    val imageForEnemy: LiveData<Int>
        get() = _imageForEnemy


    private val _image2ForEnemy = MutableLiveData<Int>()
    val image2ForEnemy: LiveData<Int>
        get() = _image2ForEnemy


    private val _characterNameForEnemy = MutableLiveData<String>()
    val characterNameForEnemy: LiveData<String>
        get() = _characterNameForEnemy


    private val _jutsuListForEnemy = MutableLiveData<Map<String, Int>>()
    val jutsuListForEnemy: LiveData<Map<String, Int>>
        get() = _jutsuListForEnemy




    val _imageTitle = MutableLiveData<ImageView>()
    val imageTitle: LiveData<ImageView>
        get() = _imageTitle


    val _imageHome = MutableLiveData<ImageView>()
    val imageHome: LiveData<ImageView>
        get() = _imageHome


    val _imageBackground = MutableLiveData<ImageView>()
    val imageBackground: LiveData<ImageView>
        get() = _imageBackground


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


    // Funktionen für das Kampfgeschehen


    // speichert Daten des Spielers in der Datenbank
    fun updateDatabase(player: Player) {

        viewModelScope.launch {
            try {
                repository.insertData(player)
            } catch (e: Exception) {
                Log.e(TAGVIEWMODEL, "Error loading Data from Database: $e")
            }
        }
    }

    fun setImageForPlayer(image: Int) {

        _imageForPlayer.value = image
    }

    fun setCharacterNameForPlayer(characterName: String) {

        _characterNameForPlayer.value = characterName
    }

    fun setJutsuForPlayer(jutsu: Map<String, Int>) {

        _jutsuListForPlayer.value = jutsu
    }

    fun setImageForEnemy(image: Int) {

        _imageForEnemy.value = image
    }


    fun setCharacterNameForEnemy(characterName: String) {

        _characterNameForEnemy.value = characterName
    }

    fun setJutsuForEnemy(jutsu: Map<String, Int>) {

        _jutsuListForEnemy.value = jutsu
    }

    fun randomCharacterForPlayer() {

        val randomCharacter = characterForFight.value?.random()

        setImageForPlayer(randomCharacter!!.image2)
        setCharacterNameForPlayer(randomCharacter.name)
        setJutsuForPlayer(randomCharacter.jutsus)
    }

    fun randomCharacterForEnemy() {

        val randomCharacter = characterForFight.value?.random()

        setImageForEnemy(randomCharacter!!.image2)
        setCharacterNameForEnemy(randomCharacter.name)
        setJutsuForEnemy(randomCharacter.jutsus)
    }

    fun resetSelectionData() {

        _imageForPlayer.value = 0
        _image2ForPlayer.value = 0
        _imageForEnemy.value = 0
        _image2ForEnemy.value = 0
        _characterNameForPlayer.value = ""
        _characterNameForEnemy.value = ""
        _jutsuListForPlayer.value = mapOf()
        _jutsuListForEnemy.value = mapOf()
    }
}