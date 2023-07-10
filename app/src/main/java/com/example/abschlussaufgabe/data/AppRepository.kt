package com.example.abschlussaufgabe.data

import com.example.abschlussaufgabe.data.datamodels.modelsApi.Character
import com.example.abschlussaufgabe.data.remote.CharacterApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

const val TAG = "AppRepositoryTAG"

class AppRepository(private val api: CharacterApi) {


    suspend fun getCharacter(): List<Character> {

            return api.retrofitService.getCharacterList().characters
    }
}