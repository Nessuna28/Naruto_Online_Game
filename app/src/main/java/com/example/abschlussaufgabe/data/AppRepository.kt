package com.example.abschlussaufgabe.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.abschlussaufgabe.data.datamodels.modelsApi.Character
import com.example.abschlussaufgabe.data.remote.CharacterApi

const val TAG = "AppRepositoryTAG"

class AppRepository(private val api: CharacterApi) {

    private val _characters = MutableLiveData<List<Character>>()
    val characters: MutableLiveData<List<Character>>
        get() = _characters

    suspend fun getCharacters(name: String) {

        try {
            _characters.value = api.retrofitService.getCharacters(name).characters
            _characters.value = api.retrofitService.getAllCharacters().characters
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
}