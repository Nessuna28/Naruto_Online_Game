package com.example.abschlussaufgabe.data

import android.net.Uri
import android.util.Log
import com.example.abschlussaufgabe.R
import com.example.abschlussaufgabe.data.datamodels.Profile
import com.example.abschlussaufgabe.data.datamodels.modelForFight.fightDataForDatabase.DataPlayer
import com.example.abschlussaufgabe.data.datamodels.modelsApi.Character
import com.example.abschlussaufgabe.data.local.GameDatabase
import com.example.abschlussaufgabe.data.remote.CharacterApi


const val TAG = "AppRepository"

class AppRepository(private val api: CharacterApi, private val gameDatabase: GameDatabase) {

    // für die Charakterinformationen

    var characters: List<Character> = listOf()
    lateinit var profile: Profile



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

    suspend fun getAllDataGame(): List<DataPlayer> {

        return try {
            gameDatabase.gameDao.getAllDataGame()
        } catch (e: Exception) {
            Log.e(TAG, "Failed load database: $e")
            emptyList()
        }
    }

    suspend fun deleteAllDataGame() {

        try {
            gameDatabase.gameDao.deleteAllDataGame()
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

    suspend fun getAllDataProfile(): Profile {

        val imageList = listOf(R.drawable.anko_face, R.drawable.asuma_face, R.drawable.choji_face, R.drawable.deidara_face, R.drawable.gaara_face, R.drawable.gai_face, R.drawable.hidan_face, R.drawable.hinata_face, R.drawable.ino_face, R.drawable.itachi_face, R.drawable.jiraiya_face, R.drawable.kabuto_face, R.drawable.kiba_face, R.drawable.naruto_face, R.drawable.sasuke_face, R.drawable.sakura_face, R.drawable.kakashi_face)
        val randomImage = imageList.random()

        val randomImageUri = Uri.parse("android.resource://drawable/${randomImage}")

        return try {
            gameDatabase.gameDao.getAllDataProfile()
        } catch (e: Exception) {
            Log.e(TAG, "Failed load database: $e")
            Profile(
                profileImage = randomImageUri,
                lastName = "",
                firstName = "",
                userName = "",
                birthday = "",
                homeTown = "",
                email = "")
        }
    }

    suspend fun updateDataProfile(profile: Profile) {

        try {
            gameDatabase.gameDao.updateDataProfile(profile)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to update database: $e")
        }
    }

    suspend fun deleteAllDataProfile() {

        try {
            gameDatabase.gameDao.deleteAllDataProfile()
        } catch (e: Exception) {
            Log.e(TAG, "Failed to delete database: $e")
        }
    }
}