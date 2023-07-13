package com.example.abschlussaufgabe.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.abschlussaufgabe.data.AppRepository
import com.example.abschlussaufgabe.data.datamodels.modelForFight.CharacterForFight
import com.example.abschlussaufgabe.data.datamodels.modelForFight.FightDataForDatabase.Player
import com.example.abschlussaufgabe.data.local.PlayerDatabase
import com.example.abschlussaufgabe.data.remote.CharacterApi
import kotlinx.coroutines.launch

const val TAGVIEWMODEL = "MainViewModel"


class MainViewModel(application: Application): AndroidViewModel(application) {

    private val database = PlayerDatabase.getDatabase(application)
    private val repository = AppRepository(CharacterApi, database)

    var characters = repository.characters



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

    private val _characterForFight = MutableLiveData<CharacterForFight>()
    val characterForFight: MutableLiveData<CharacterForFight>
        get() = _characterForFight

    fun updateDatabase(player: Player) {

        viewModelScope.launch {
            try {
                repository.insertData(player)
            } catch (e: Exception) {
                Log.e(TAGVIEWMODEL, "Error loading Data from Database: $e")
            }
        }
    }
}