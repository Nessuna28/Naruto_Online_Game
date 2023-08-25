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
    // für die Spieldaten

    suspend fun insertDataGame(dataPlayer: DataPlayer) {

        try {
            gameDatabase.gameDao.insertDataGame(dataPlayer)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to insert into database: $e")
        }
    }

    suspend fun deleteDataGame(dataPlayer: DataPlayer) {

        try {
            gameDatabase.gameDao.deleteDataGame(dataPlayer)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to delete database: $e")
        }
    }

    suspend fun deleteAllDataGame() {

        try {
            gameDatabase.gameDao.deleteAllDataGame()
        } catch (e: Exception) {
            Log.e(TAG, "Failed to delete database: $e")
        }
    }

}