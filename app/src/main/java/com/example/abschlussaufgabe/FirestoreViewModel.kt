package com.example.abschlussaufgabe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.abschlussaufgabe.data.datamodels.Profile
import com.example.abschlussaufgabe.data.datamodels.modelForFight.fightDataForDatabase.DataPlayer
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class FirestoreViewModel: ViewModel() {


    private val _currentProfile = MutableLiveData<Profile>()
    val currentProfile: LiveData<Profile>
        get() = _currentProfile


    private val _updateDone = MutableLiveData(false)
    val updateDone: LiveData<Boolean>
        get() = _updateDone


    private val _playerDataList = MutableLiveData<List<DataPlayer>>()
    val playerDataList: LiveData<List<DataPlayer>>
        get() = _playerDataList

    private var filteredPlayerDataList = mutableListOf<DataPlayer>()

    private val db = Firebase.firestore


    fun addNewUser(profile: Profile) {

        val userProfile = mapOf(
            "userID" to profile.userID,
            "profileImage" to profile.profileImage,
            "lastName" to profile.lastName,
            "firstName" to profile.firstName,
            "userName" to profile.userName,
            "birthday" to profile.birthday,
            "homeTown" to profile.homeTown,
            "email" to profile.email
        )

        db.collection("profiles").document(profile.userID).set(userProfile)
            .addOnSuccessListener { Log.d("Firestore", "DocumentSnapshot successfully written!")
            _updateDone.value = true }
            .addOnFailureListener { e -> Log.w("Firestore", "Error writing document", e) }
    }

    fun resetUpdateDone() {

        _updateDone.value = false
    }

    fun addNewPlayerData(data: DataPlayer) {

        val firestoreId = db.collection("playerData").document().id // Generiere die Firestore-ID

        val playerData = mapOf(
            "userID" to data.userId,
            "data" to data.date,
            "userName" to data.userName,
            "characterName" to data.characterName,
            "characterImage" to data.characterImage,
            "result" to data.result,
            "userNameEnemy" to data.userNameEnemy,
            "characterNameEnemy" to data.characterNameEnemy,
            "characterImageEnemy" to data.characterImageEnemy,
            "resultEnemy" to data.resultEnemy
        )

        db.collection("playerData").document(firestoreId).set(playerData)
            .addOnSuccessListener { Log.d("Firestore", "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w("Firestore", "Error writing document", e) }
    }

    fun getUserData(userId: String) {

        val docRef = db.collection("profiles").document(userId)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val profile = Profile(
                        userID = userId,
                        profileImage = document.data?.get("profileImage").toString(),
                        lastName = document.data?.get("lastName").toString(),
                        firstName = document.data?.get("firstName").toString(),
                        userName = document.data?.get("userName").toString(),
                        birthday = document.data?.get("birthday").toString(),
                        homeTown = document.data?.get("homeTown").toString(),
                        email = document.data?.get("email").toString()
                    )
                    _currentProfile.value = profile
                } else {
                    Log.d("Firestore", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Firestore", "get failed with ", exception)
            }
    }

    fun reloadProfile(profile: Profile) {

        _currentProfile.value = profile
        _currentProfile.value = _currentProfile.value
    }

    fun getPlayerData(currentUserId: String) {

        val docRef = db.collection("playerData")
        docRef.get()
            .addOnSuccessListener { documents ->
                setDataList(documents, currentUserId)
            }
            .addOnFailureListener { exception ->
                Log.w("Firestore", "Error getting documents: ", exception)
            }
    }


    fun deleteUserData(userId: String) {

        db.collection("profiles").document(userId)
            .delete()
            .addOnSuccessListener {
                Log.d("Firestore", "DocumentSnapshot successfully deleted!")
            }
            .addOnFailureListener { e -> Log.w("Firestore", "Error deleting document", e) }
    }

    fun deletePlayerData(playerData: DataPlayer) {

        Log.e("FirestoreId", "${playerData.firestoreId}")
        db.collection("playerData").document(playerData.firestoreId)
            .delete()
            .addOnSuccessListener {
                filteredPlayerDataList.remove(playerData)
                _playerDataList.value = filteredPlayerDataList
                Log.d("Firestore", "DocumentSnapshot successfully deleted!")
            }
            .addOnFailureListener { e -> Log.w("Firestore", "Error deleting document", e) }
    }

    fun deleteAllPlayerData() {

        val docRef = db.collection("playerData")
        docRef.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                     if (document.getString("userID") == currentProfile.value!!.userID) {
                         document.reference.delete()
                             .addOnSuccessListener {
                                 filteredPlayerDataList.clear()
                                 _playerDataList.value = filteredPlayerDataList
                                 Log.d("Firestore", "Document successfully deleted!")
                             }
                             .addOnFailureListener { exception ->
                                 Log.w("Firestore", "Error deleting document: ", exception)
                             }
                     }
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Firestore", "Error getting documents: ", exception)
            }
    }


    private fun setDataList(documents: QuerySnapshot, currentUserId: String) {

        val list = mutableListOf<DataPlayer>()

        for (document in documents) {
            val firestoreId = document.id
            val userId = document.getString("userID").toString()
            val date = document.getString("data").toString()
            val userName = document.getString("userName").toString()
            val characterName = document.getString("characterName").toString()
            val characterImage = document.getLong("characterImage")!!.toInt()
            val result = document.getString("result").toString()
            val userNameEnemy = document.getString("userNameEnemy").toString()
            val characterNameEnemy = document.getString("characterNameEnemy").toString()
            val characterImageEnemy = document.getLong("characterImageEnemy")!!.toInt()
            val resultEnemy = document.getString("resultEnemy").toString()

            val playerData = DataPlayer(
                firestoreId = firestoreId,
                userId = userId,
                date = date,
                userName = userName,
                characterName = characterName,
                characterImage = characterImage,
                result = result,
                userNameEnemy = userNameEnemy,
                characterNameEnemy = characterNameEnemy,
                characterImageEnemy = characterImageEnemy,
                resultEnemy = resultEnemy
            )

            list.add(playerData)
        }

        filteredPlayerDataList = list.filter { playerData ->
            playerData.userId == currentUserId
        }.toMutableList()
        _playerDataList.value = filteredPlayerDataList
    }

}
