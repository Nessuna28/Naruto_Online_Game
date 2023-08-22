package com.example.abschlussaufgabe.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.abschlussaufgabe.data.datamodels.Profile
import com.example.abschlussaufgabe.data.datamodels.modelForFight.fightDataForDatabase.DataPlayer
import com.example.abschlussaufgabe.data.datamodels.modelsApi.Character
import com.example.abschlussaufgabe.data.local.GameDatabase
import com.example.abschlussaufgabe.data.remote.CharacterApi


const val TAG = "AppRepository"

class AppRepository(private val api: CharacterApi, private val gameDatabase: GameDatabase) {

    // für die Charakterinformationen

    var characters: List<Character> = listOf()

    // für die Datenbank
    val dataList = gameDatabase.gameDao.getAllDataGame()



    suspend fun getCharacter(name: String) {

        try {
            if (name != "") {
                characters = listOf(api.retrofitService.getCharacter(name))
            } else {
                getAllCharacters()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error: ${e.message}")
        }
    }

    suspend fun getAllCharacters() {

        try {
            characters = api.retrofitService.getAllCharacters().characters
        } catch (e: Exception) {
            Log.e(TAG, "Error: ${e.message}")
        }
    }


    // für die Datenbank

    // für die Datenbank für die Spieldaten
    suspend fun insertDataGame(dataPlayer: DataPlayer) {

        try {
            gameDatabase.gameDao.insertDataGame(dataPlayer)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to insert into database: $e")
        }
    }

    suspend fun deleteAllDataGame(dataPlayer: DataPlayer) {

        try {
            gameDatabase.gameDao.deleteAllDataGame(dataPlayer)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to delete database: $e")
        }
    }

    // für die Datenbank für die Profildaten
    suspend fun insertDataProfile(user: Profile) {

        try {
            gameDatabase.gameDao.insertDataProfile(user)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to insert into database: $e")
        }
    }

    fun getProfileByEmail(email: String): LiveData<Profile> {

        try {
            return gameDatabase.gameDao.getDataByEmail(email)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to load Profile: $e")
            throw e
        }
    }

    suspend fun updateDataProfile(profile: Profile) {

        try {
            gameDatabase.gameDao.updateDataProfile(profile)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to update Profile: $e")
        }
    }

    suspend fun deleteAllDataProfile(profile: Profile) {

        try {
            gameDatabase.gameDao.deleteAllDataProfile(profile)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to delete Profile: $e")
        }
    }
}