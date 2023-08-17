package com.example.abschlussaufgabe.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.abschlussaufgabe.data.datamodels.Profile
import com.example.abschlussaufgabe.data.datamodels.modelForFight.fightDataForDatabase.DataPlayer
import com.example.abschlussaufgabe.data.datamodels.modelsApi.Character
import com.example.abschlussaufgabe.data.local.GameDatabase
import com.example.abschlussaufgabe.data.local.ProfileDatabase
import com.example.abschlussaufgabe.data.remote.CharacterApi


const val TAG = "AppRepository"

class AppRepository(private val api: CharacterApi, private val gameDatabase: GameDatabase, private val profileDatabase: ProfileDatabase) {

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


    // für die Datenbank

    suspend fun insertDataGame(dataPlayer: DataPlayer) {

        try {
            gameDatabase.playerDao.insertData(dataPlayer)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to insert into database: $e")
        }
    }

    suspend fun getAllDataGame(): List<DataPlayer> {

        return try {
            gameDatabase.playerDao.getAllData()
        } catch (e: Exception) {
            Log.e(TAG, "Failed load database: $e")
            emptyList()
        }
    }


    suspend fun insertDataProfile(user: Profile) {

        try {
            profileDatabase.profileDao.insertData(user)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to insert into database: $e")
        }
    }

    suspend fun getAllDataProfile(): Profile {

        return try {
            profileDatabase.profileDao.getAllData()
        } catch (e: Exception) {
            Log.e(TAG, "Failed load database: $e")
            Profile(
                profileImage = 0,
                lastName = "",
                firstName = "",
                userName = "",
                birthday = "",
                phone = "",
                email = "")
        }
    }
}