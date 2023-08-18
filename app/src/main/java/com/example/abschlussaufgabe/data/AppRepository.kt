package com.example.abschlussaufgabe.data

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.abschlussaufgabe.R
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

    // für die Datenbank für die Spieldaten
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

    suspend fun deleteAllDataGame() {

        try {
            gameDatabase.playerDao.deleteAllData()
        } catch (e: Exception) {
            Log.e(TAG, "Failed to delete database: $e")
        }
    }

    // für die Datenbank für die Profildaten
    suspend fun insertDataProfile(user: Profile) {

        try {
            profileDatabase.profileDao.insertData(user)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to insert into database: $e")
        }
    }

    suspend fun getAllDataProfile(): Profile {

        val imageList = listOf(R.drawable.anko_face, R.drawable.asuma_face, R.drawable.choji_face, R.drawable.deidara_face, R.drawable.gaara_face, R.drawable.gai_face, R.drawable.hidan_face, R.drawable.hinata_face, R.drawable.ino_face, R.drawable.itachi_face, R.drawable.jiraiya_face, R.drawable.kabuto_face, R.drawable.kiba_face, R.drawable.naruto_face, R.drawable.sasuke_face, R.drawable.sakura_face, R.drawable.kakashi_face)
        val randomImage = imageList.random()

        val randomImageUri = Uri.parse("android.resource://drawable/${randomImage}")

        return try {
            profileDatabase.profileDao.getAllData()
        } catch (e: Exception) {
            Log.e(TAG, "Failed load database: $e")
            Profile(
                profileImage = randomImageUri,
                lastName = "",
                firstName = "",
                userName = "",
                birthday = "",
                phone = "",
                email = "")
        }
    }

    suspend fun updateDataProfile(profile: Profile) {

        try {
            profileDatabase.profileDao.updateData(profile)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to update database: $e")
        }
    }

    suspend fun deleteAllDataProfile() {

        try {
            profileDatabase.profileDao.deleteAllData()
        } catch (e: Exception) {
            Log.e(TAG, "Failed to delete database: $e")
        }
    }
}