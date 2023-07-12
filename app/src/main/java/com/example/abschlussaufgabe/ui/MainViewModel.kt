package com.example.abschlussaufgabe.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.abschlussaufgabe.data.AppRepository
import com.example.abschlussaufgabe.data.remote.CharacterApi
import kotlinx.coroutines.launch

const val TAG = "MainViewModel"


class MainViewModel(application: Application): AndroidViewModel(application) {

    private val repository = AppRepository(CharacterApi)
    var characters = repository.characters


    init {
        loadCharacters()
        searchCharacter("")
    }

    fun loadCharacters() {

        viewModelScope.launch {
            try {
                repository.getAllCharacters()
            } catch (e: Exception) {
                Log.e(TAG, "Error loading Data: $e")
            }
        }
    }

    fun searchCharacter(name: String) {

        viewModelScope.launch {
            try {
                repository.getCharacter(name)
                println(characters)
            } catch (e: Exception) {
                Log.e(TAG, "Error loading Data: $e")
            }
        }
    }
}