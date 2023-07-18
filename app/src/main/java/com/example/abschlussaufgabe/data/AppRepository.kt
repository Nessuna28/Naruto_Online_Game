package com.example.abschlussaufgabe.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.abschlussaufgabe.R
import com.example.abschlussaufgabe.data.datamodels.modelForFight.dataLists.LocationList
import com.example.abschlussaufgabe.data.datamodels.modelForFight.fightDataForDatabase.Location
import com.example.abschlussaufgabe.data.datamodels.modelForFight.fightDataForDatabase.Player
import com.example.abschlussaufgabe.data.datamodels.modelsApi.Character
import com.example.abschlussaufgabe.data.local.PlayerDatabase
import com.example.abschlussaufgabe.data.remote.CharacterApi


const val TAG = "AppRepository"

class AppRepository(private val api: CharacterApi, private val database: PlayerDatabase) {

    private val _characters = MutableLiveData<List<Character>>()
    val characters: MutableLiveData<List<Character>>
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




    private val _dataList = MutableLiveData<List<Player>>()
            val dataList: MutableLiveData<List<Player>>
                get() = _dataList


    suspend fun insertData(player: Player) {

        if (dataList.value?.isEmpty() == true) {
            try {
                database.playerDao.insertData(player)
            } catch (e: Exception) {
                Log.e(TAG, "Failed to insert into database: $e")
            }
        }
    }
}