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
import android.os.Handler
import com.example.abschlussaufgabe.R
import com.example.abschlussaufgabe.data.datamodels.modelForFight.Attack
import com.example.abschlussaufgabe.data.datamodels.modelForFight.Jutsu
import com.example.abschlussaufgabe.data.datamodels.modelForFight.UniqueTrait
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale



const val TAGVIEWMODEL = "MainViewModel"


class MainViewModel(application: Application): AndroidViewModel(application) {

    private val database = PlayerDatabase.getDatabase(application)
    private val repository = AppRepository(CharacterApi, database)

    private var mediaPlayer: MediaPlayer? = null

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


    private val _jutsuListForPlayer = MutableLiveData<List<Jutsu>>()
    val jutsuListForPlayer: LiveData<List<Jutsu>>
        get() = _jutsuListForPlayer


    private val _uniqueTraitsListForPlayer = MutableLiveData<List<UniqueTrait>>()
    val uniqueTraitsListForPlayer: LiveData<List<UniqueTrait>>
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


    private val _jutsuListForEnemy = MutableLiveData<List<Jutsu>>()
    val jutsuListForEnemy: LiveData<List<Jutsu>>
        get() = _jutsuListForEnemy


    private val _uniqueTraitsListForEnemy = MutableLiveData<List<UniqueTrait>>()
    val uniqueTraitsListForEnemy: LiveData<List<UniqueTrait>>
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



    private val _attackPlayer = MutableLiveData<Attack>()
    val attackPlayer: LiveData<Attack>
        get() = _attackPlayer


    private val _attackEnemy = MutableLiveData<Attack>()
    val attackEnemy: LiveData<Attack>
        get() = _attackEnemy


    // für das Ergebnis (ResultFragment)

    private val _result = MutableLiveData<String>("")
    val result: LiveData<String>
        get() = _result


    private val _resultEnemy = MutableLiveData("")
    val resultEnemy: LiveData<String>
        get() = _resultEnemy


    private val _roundsWonPlayer = MutableLiveData(0)
    val roundsWonPlayer: LiveData<Int>
        get() = _roundsWonPlayer


    private val _roundsWonEnemy = MutableLiveData(0)
    val roundsWonEnemy: LiveData<Int>
        get() = _roundsWonEnemy


    private val _rounds = MutableLiveData(0)
    val rounds: LiveData<Int>
        get() = _rounds


    private val _gameEnd = MutableLiveData(false)
    val gameEnd: LiveData<Boolean>
        get() = _gameEnd



    // für die Datenbank

    private val _userNameEnemy = MutableLiveData<String>()
    val userNameEnemy: LiveData<String>
        get() = _userNameEnemy


    private val _victory = MutableLiveData(0)
    val victory: LiveData<Int>
        get() = _victory


    private val _defeat = MutableLiveData(0)
    val defeat: LiveData<Int>
        get() = _defeat



    // für die Statistik
    val dataList: LiveData<List<DataPlayer>>
        get() = repository.dataList



    // für Toast-Messages
    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String>
    get() = _toastMessage



    // Initialisierung
    init {
        loadCharacters()
        searchCharacter("")
        _characterForFight.value = CharacterListForFight().characterList
        _locationList.value = LocationList().locationList
        _location.value = locationList.value?.get(0)
        repository.dataList

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

    fun setJutsuForPlayer(jutsus: List<Jutsu>) {

        _jutsuListForPlayer.value = jutsus
    }

    fun setUniqueTraitForPlayer(uniqueTraits: List<UniqueTrait>) {

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

    fun setJutsuForEnemy(jutsus: List<Jutsu>) {

        _jutsuListForEnemy.value = jutsus
    }

    fun setUniqueTraitForEnemy(uniqueTraits: List<UniqueTrait>) {

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

        setImageForPlayer(
            randomCharacter!!.image,
            randomCharacter.imagePose,
            randomCharacter.imageFace,
            randomCharacter.imageAttack
        )
        setCharacterNameForPlayer(randomCharacter.name)
        setJutsuForPlayer(randomCharacter.jutsus)
        setUniqueTraitForPlayer(randomCharacter.uniqueTraits)
        setPlayer(randomCharacter)
    }

    fun randomCharacterForEnemy() {

        val randomCharacter = characterForFight.value?.random()

        setImageForEnemy(
            randomCharacter!!.image,
            randomCharacter.imagePose,
            randomCharacter.imageFace,
            randomCharacter.imageAttack
        )
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


    // setzt die im Adapter ausgewählte Attacke
    fun setAttackPlayer(attack: Attack) {

        _attackPlayer.value = attack
    }

    // es wird erst eine Liste aller möglichen Attacken des Charackters erstellt
    // und aus der Liste eine zufällige Attacke ausgewählt
    fun setAttackEnemy() {

        val jutsuList = enemy.value!!.jutsus
        val toolList = enemy.value!!.tools
        val traitsList = enemy.value!!.uniqueTraits
        val defenseList = enemy.value!!.defense

        var listOfAllAttacks = mutableListOf<Attack>()

        for (attack in jutsuList) {
            listOfAllAttacks.add(attack)
        }

        for (attack in toolList) {
            listOfAllAttacks.add(attack)
        }

        for (attack in traitsList) {
            listOfAllAttacks.add(attack)
        }

        for (attack in defenseList) {
            listOfAllAttacks.add(attack)
        }

        val currentAttackEnemy = listOfAllAttacks.random()

        _attackEnemy.value = currentAttackEnemy
    }

    // sorgt dafür dass nach 5 Sekunden alle Funktionen für den Computer wiederholt werden
    val runnable: Runnable = object : Runnable {
        override fun run() {

            setAttackEnemy()

            // jede nächste Ausführung nach 5 Sekunden
            Handler().postDelayed(this, 5000)
        }
    }

    fun playRound() {

        setAttackEnemy()
        Handler().postDelayed(runnable, 5000)

        if (attackPlayer.value != null && attackEnemy.value != null) {
            if (player.value!!.lifePoints > 0 || enemy.value!!.lifePoints > 0) {
                attackPlayer.value!!.loadChakra(_player.value!!, player.value!!)
                attackPlayer.value!!.subtractChakra(_player.value!!, player.value!!, ::showToast)
                attackPlayer.value!!.subtractLifePoints(player.value!!, _player.value!!, attackEnemy.value!!, _enemy.value!!, enemy.value!!, ::showToast)
                attackEnemy.value!!.loadChakra(_enemy.value!!, enemy.value!!)
                attackEnemy.value!!.subtractChakra(_enemy.value!!, enemy.value!!, ::showToast)
                attackEnemy.value!!.subtractLifePoints(enemy.value!!, _enemy.value!!, attackPlayer.value!!, _player.value!!, player.value!!, ::showToast)

                _player.value = _player.value
                _enemy.value = _enemy.value
            }
        }
    }

    // wenn einer der Spieler keine Lebenspunkte mehr hat wird die Runde beendet und
    // es wird festgelegt ob der Spieler gewonnen oder verloren hat
    fun endRound() {

        if (player.value!!.lifePoints <= 0 || enemy.value!!.lifePoints <= 0) {
            _rounds.value = rounds.value!!.plus(1)
            Handler().removeCallbacks(runnable)
        }

        if (player.value!!.lifePoints > 0) {
            _roundsWonPlayer.value = roundsWonPlayer.value!!.plus(1)
        } else if (enemy.value!!.lifePoints > 0) {
            _roundsWonEnemy.value = roundsWonEnemy.value!!.plus(1)
        }
    }

    // Funktion um den Toast anzuzeigen
    fun showToast(message: String) {
        _toastMessage.value = message
    }

    // setzt die Lebens- und Chakrapunkte wieder auf die Ursprungswerte außer
    // wenn man noch Lebenspunkte übrig hat dann nimmt man diese mit in die nächste Runde
    fun resetPoints() {

        if (player.value!!.lifePoints <= 0) {
            _player.value!!.lifePoints = player.value!!.lifePointsStart
        } else {
            _enemy.value!!.lifePoints = enemy.value!!.lifePointsStart
        }

        _player.value!!.chakraPoints = player.value!!.chakraPointsStart
        _enemy.value!!.chakraPoints = enemy.value!!.chakraPointsStart
    }

    fun playRounds(round: Unit) {

        if (rounds.value!! < 3) {
            if (roundsWonPlayer.value!! < 1 && roundsWonEnemy.value!! < 1) {
                round
            } else if (roundsWonPlayer.value!! < 2 && roundsWonEnemy.value!! < 2) {
                resetPoints()
                round
            }
        }

        if (rounds.value!! == 3 || roundsWonPlayer.value!! == 2 || roundsWonEnemy.value!! == 2) {
            _gameEnd.value = true
        }
    }

    fun resetToDefaultRounds() {

        _rounds.value = 0
        _roundsWonPlayer.value = 0
        _roundsWonEnemy.value = 0
    }



    // ändert den Wert des Ergebniss (gewonnen oder verloren)
    fun setResult(check: Boolean) {

        if (check) {
            _result.value = "Sieg"
            _resultEnemy.value = "Niederlage"
        } else {
            _result.value = "Niederlage"
            _resultEnemy.value = "Sieg"
        }
    }


    // zählt die Siege und Niederlagen
    fun countVictorysAndDefeats(result: String) {

        if (result == "Sieg") {
            _victory.value = victory.value!!.plus(1)
        } else {
            _defeat.value = defeat.value!!.plus(1)
        }

        _victory.value = _victory.value
        _defeat.value = _defeat.value
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

        viewModelScope.launch {
            try {
                repository.insertData(dataPlayer)
            } catch (e: Exception) {
                Log.e(TAGVIEWMODEL, "Error loading Data from Database: $e")
            }
        }
    }
}
