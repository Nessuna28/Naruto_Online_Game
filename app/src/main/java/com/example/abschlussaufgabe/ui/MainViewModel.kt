package com.example.abschlussaufgabe.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.abschlussaufgabe.data.AppRepository
import com.example.abschlussaufgabe.data.datamodels.modelsApi.Character
import com.example.abschlussaufgabe.data.remote.CharacterApi
import kotlinx.coroutines.launch

const val TAG = "MainViewModel"


class MainViewModel(application: Application): AndroidViewModel(application) {

    private val repository = AppRepository(CharacterApi)
    var characters = listOf<Character>()


    init {
        loadCharacters()
    }

    fun loadCharacters() {

        viewModelScope.launch {
            try {
                characters = repository.getCharacter()
            } catch (e: Exception) {
                Log.e(TAG, "Error loading Data: $e")
            }
        }
    }
}