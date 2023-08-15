package com.example.abschlussaufgabe.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.abschlussaufgabe.data.datamodels.modelForFight.fightDataForDatabase.DataPlayer
import com.example.abschlussaufgabe.data.datamodels.modelsApi.Character
import com.example.abschlussaufgabe.data.local.PlayerDatabase
import com.example.abschlussaufgabe.data.local.PlayerDatabaseDao
import com.example.abschlussaufgabe.data.remote.CharacterApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


const val TAG = "AppRepository"

class AppRepository(private val api: CharacterApi, private val database: PlayerDatabase) {

    // für die Charakterinformationen

    private val _characters = MutableLiveData<List<Character>>()
    val characters: LiveData<List<Character>>
        get() = _characters


    suspend fun getCharacter(name: String) {

        try {
            if (name != "") {
                _characters.value = listOf(api.retrofitService.getCharacter(name))
            } else {
                getAllCharacters()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error: ${e.message}")
        }
    }

    suspend fun getAllCharacters() {

        try {
            _characters.value = api.retrofitService.getAllCharacters().characters
        } catch (e: Exception) {
            Log.e(TAG, "Error: ${e.message}")
        }
    }



    suspend fun insertData(dataPlayer: DataPlayer) {

        try {
            database.playerDao.insertData(dataPlayer)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to insert into database: $e")
        }
    }

    suspend fun getAllData(): List<DataPlayer> {

        return try {
            database.playerDao.getAllData()
        } catch (e: Exception) {
            Log.e(TAG, "Failed load database: $e")
            emptyList()
        }
    }
}