package com.example.abschlussaufgabe.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.abschlussaufgabe.data.datamodels.modelForFight.CharacterForFight
import com.example.abschlussaufgabe.data.datamodels.modelForFight.characterData.CharacterListForFight
import com.example.abschlussaufgabe.data.datamodels.modelsApi.Character
import com.example.abschlussaufgabe.data.local.CharacterDatabase
import com.example.abschlussaufgabe.data.remote.CharacterApi


const val TAG = "AppRepository"

class AppRepository(private val api: CharacterApi, private val database: CharacterDatabase) {

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


    val characterListForFight: LiveData<List<CharacterForFight>> = database.characterDao.getAllCharacters()
    suspend fun fillUpDB() {

        if (characterListForFight.value?.isEmpty() == true) {
            try {
                database.characterDao.insertCharacter(CharacterListForFight.naruto)
            } catch (e: Exception) {
                Log.e(TAG, "Failed to insert into database: $e")
            }
        }
    }


}