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


const val TAGVIEWMODEL = "MainViewModel"


class MainViewModel(application: Application): AndroidViewModel(application) {

    private val database = PlayerDatabase.getDatabase(application)
    private val repository = AppRepository(CharacterApi, database)

    var characters = repository.characters


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


    val _imageBackground = MutableLiveData<ImageView>()
    val imageBackground: LiveData<ImageView>
        get() = _imageBackground


    val _materialCard = MutableLiveData<MaterialCardView>()
    val materialCard: LiveData<MaterialCardView>
        get() = _materialCard



    // für die Datenbank



    // für die Charakterauswahl (CharacterSelectionFragment)

    private val _characterForFight = MutableLiveData<List<CharacterForFight>>()
    val characterForFight: LiveData<List<CharacterForFight>>
        get() = _characterForFight


    private val _imageForPlayer = MutableLiveData<Int>()
    val imageForPlayer: LiveData<Int>
        get() = _imageForPlayer


    private val _imagePoseForPlayer = MutableLiveData<Int>()
    val imagePoseForPlayer: LiveData<Int>
        get() = _imagePoseForPlayer


    private val _imageFaceForPlayer = MutableLiveData<Int>()
    val imageFaceForPlayer: LiveData<Int>
        get() = _imageFaceForPlayer


    private val _imageAttackForPlayer = MutableLiveData<Int>()
    val imageAttackForPlayer: LiveData<Int>
        get() = _imageAttackForPlayer


    private val _characterNameForPlayer = MutableLiveData<String>()
    val characterNameForPlayer: LiveData<String>
        get() = _characterNameForPlayer


    private val _jutsuListForPlayer = MutableLiveData<Map<String, Int>>()
    val jutsuListForPlayer: LiveData<Map<String, Int>>
        get() = _jutsuListForPlayer


    private val _uniqueTraitsListForPlayer = MutableLiveData<Map<String, Int>>()
    val uniqueTraitsListForPlayer: LiveData<Map<String, Int>>
        get() = _uniqueTraitsListForPlayer



    private val _imageForEnemy = MutableLiveData<Int>()
    val imageForEnemy: LiveData<Int>
        get() = _imageForEnemy


    private val _imagePoseForEnemy = MutableLiveData<Int>()
    val imagePoseForEnemy: LiveData<Int>
        get() = _imagePoseForEnemy


    private val _imageFaceForEnemy = MutableLiveData<Int>()
    val imageFaceForEnemy: LiveData<Int>
        get() = _imageFaceForEnemy


    private val _imageAttackForEnemy = MutableLiveData<Int>()
    val imageAttackForEnemy: LiveData<Int>
        get() = _imageAttackForEnemy


    private val _characterNameForEnemy = MutableLiveData<String>()
    val characterNameForEnemy: LiveData<String>
        get() = _characterNameForEnemy


    private val _jutsuListForEnemy = MutableLiveData<Map<String, Int>>()
    val jutsuListForEnemy: LiveData<Map<String, Int>>
        get() = _jutsuListForEnemy


    private val _uniqueTraitsListForEnemy = MutableLiveData<Map<String, Int>>()
    val uniqueTraitsListForEnemy: LiveData<Map<String, Int>>
        get() = _uniqueTraitsListForEnemy


    private val _selectionConfirmedPlayer = MutableLiveData<Boolean>()
    val selectionConfirmedPlayer: LiveData<Boolean>
        get() = _selectionConfirmedPlayer


    private val _selectionConfirmedEnemy = MutableLiveData<Boolean>()
    val selectionConfirmedEnemy: LiveData<Boolean>
        get() = _selectionConfirmedEnemy


    // für die Auswahl der Location (LocationSelectionFragment)

    private val _locationList = MutableLiveData<List<Location>>()
    val locationList: LiveData<List<Location>>
        get() = _locationList


    private val _location = MutableLiveData<Location>()
    val location: LiveData<Location>
        get() = _location


    private val _selectionConfirmLocation = MutableLiveData<Boolean>()
    val selectionConfirmLocation: LiveData<Boolean>
        get() = _selectionConfirmLocation



    // für den Kampf (FightFragment)

    private val _player = MutableLiveData<CharacterForFight>()
    val player: LiveData<CharacterForFight>
        get() = _player


    private val _enemy = MutableLiveData<CharacterForFight>()
    val enemy: LiveData<CharacterForFight>
        get() = _enemy


    private val _result = MutableLiveData<String>()
    val result: LiveData<String>
        get() = _result


    private val _roundsWon = MutableLiveData<Int>()
    val roundsWon: LiveData<Int>
        get() = _roundsWon



    // für die Statistik

    val dataList: LiveData<List<DataPlayer>>
        get() = repository.dataList


    // Initialisierung
    init {
        loadCharacters()
        searchCharacter("")
        _characterForFight.value = CharacterListForFight().characterList
        _locationList.value = LocationList().locationList
        _location.value = locationList.value?.get(0)
        repository.dataList
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

    // speichert den ausgewählten Charakter für den Kampf

    fun setPlayer(character: CharacterForFight) {

        _player.value = character
    }

    fun setEnemy(character: CharacterForFight) {

        _enemy.value = character
    }


    // in mehreren Funktionen werden die Daten für die Anzeigen im Screen bei der Charakterauswahl gesetzt

    fun setImageForPlayer(image: Int, imagePose: Int, imageFace: Int, imageAttack: Int) {

        _imageForPlayer.value = image
        _imagePoseForPlayer.value = imagePose
        _imageFaceForPlayer.value = imageFace
        _imageAttackForPlayer.value = imageAttack
    }

    fun setCharacterNameForPlayer(characterName: String) {

        _characterNameForPlayer.value = characterName
    }

    fun setJutsuForPlayer(jutsus: Map<String, Int>) {

        _jutsuListForPlayer.value = jutsus
    }

    fun setUniqueTraitForPlayer(uniqueTraits: Map<String, Int>) {

        _uniqueTraitsListForPlayer.value = uniqueTraits
    }

    fun setImageForEnemy(image: Int, imagePose: Int, imageFace: Int, imageAttack: Int) {

        _imageForEnemy.value = image
        _imagePoseForEnemy.value = imagePose
        _imageFaceForEnemy.value = imageFace
        _imageAttackForEnemy.value = imageAttack
    }

    fun setCharacterNameForEnemy(characterName: String) {

        _characterNameForEnemy.value = characterName
    }

    fun setJutsuForEnemy(jutsus: Map<String, Int>) {

        _jutsuListForEnemy.value = jutsus
    }

    fun setUniqueTraitForEnemy(uniqueTraits: Map<String, Int>) {

        _uniqueTraitsListForEnemy.value = uniqueTraits
    }

    fun setLocation(location: Location) {

        _location.value = location
    }


    // für die Prüfung ob der Nutzer den Weiter-Button drücken darf

    fun confirmSelectionPlayer(check: Boolean) {

        _selectionConfirmedPlayer.value = check
    }

    fun confirmSelectionEnemy(check: Boolean) {

        _selectionConfirmedEnemy.value = check
    }

    fun confirmSelectionLocation(check: Boolean) {

        _selectionConfirmLocation.value = check
    }


    // hier wird per Random eine zufällige Auswahl getroffen und die dazugehörigen Daten werden gesetzt

    fun randomCharacterForPlayer() {

        val randomCharacter = characterForFight.value?.random()

        setImageForPlayer(randomCharacter!!.image, randomCharacter.imagePose, randomCharacter.imageFace, randomCharacter.imageAttack)
        setCharacterNameForPlayer(randomCharacter.name)
        setJutsuForPlayer(randomCharacter.jutsus)
        setUniqueTraitForPlayer(randomCharacter.uniqueTraits)
        setPlayer(randomCharacter)
    }

    fun randomCharacterForEnemy() {

        val randomCharacter = characterForFight.value?.random()

        setImageForEnemy(randomCharacter!!.image, randomCharacter.imagePose, randomCharacter.imageFace, randomCharacter.imageAttack)
        setCharacterNameForEnemy(randomCharacter.name)
        setJutsuForEnemy(randomCharacter.jutsus)
        setUniqueTraitForEnemy(randomCharacter.uniqueTraits)
        setEnemy(randomCharacter)
    }

    fun randomLocation() {

        val randomLocation = locationList.value!!.random()

        _location.value = randomLocation
    }


    // setzt die Daten wieder zurück zum Ursprung
    fun resetSelectionData() {

        _imageForPlayer.value = characterForFight.value?.get(0)?.image
        _imagePoseForPlayer.value = characterForFight.value?.get(0)?.imagePose
        _imagePoseForPlayer.value = characterForFight.value?.get(0)?.imageFace
        _imageForEnemy.value = characterForFight.value?.get(0)?.image
        _imagePoseForEnemy.value = characterForFight.value?.get(0)?.imagePose
        _imagePoseForEnemy.value = characterForFight.value?.get(0)?.imageFace
        _characterNameForPlayer.value = characterForFight.value?.get(0)?.name
        _characterNameForEnemy.value = characterForFight.value?.get(0)?.name
        _jutsuListForPlayer.value = characterForFight.value?.get(0)?.jutsus
        _uniqueTraitsListForPlayer.value = characterForFight.value?.get(0)?.uniqueTraits
        _jutsuListForEnemy.value = characterForFight.value?.get(0)?.jutsus
        _uniqueTraitsListForEnemy.value = characterForFight.value?.get(0)?.uniqueTraits
    }

    // ändert den Wert des Ergebniss (gewonnen oder Verloren)
    fun setResult(check: Boolean) {

        if (check) {
            _result.value = "Sieg"
        } else {
            _result.value = "Niederlage"
        }
    }


    // speichert Daten des Spielers in der Datenbank
    fun updateDatabase(dataPlayer: DataPlayer) {

        viewModelScope.launch {
            try {
                repository.insertData(dataPlayer)
            } catch (e: Exception) {
                Log.e(TAGVIEWMODEL, "Error loading Data from Database: $e")
            }
        }
    }
}