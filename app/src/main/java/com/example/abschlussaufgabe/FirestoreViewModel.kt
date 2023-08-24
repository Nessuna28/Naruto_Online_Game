package com.example.abschlussaufgabe

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.abschlussaufgabe.data.datamodels.Profile
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirestoreViewModel: ViewModel() {


    private val _currentProfile = MutableLiveData<Profile>()
    val currentProfile: LiveData<Profile>
        get() = _currentProfile

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
            .addOnSuccessListener { Log.d("Firestore", "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w("Firestore", "Error writing document", e) }
    }

    fun getUserData(userId: String) {

        val docRef = db.collection("profiles").document(userId)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val profileImageUri = document.data?.get("profileImage") as? String
                    val profileImage = if (!profileImageUri.isNullOrEmpty()) {
                        Uri.parse(profileImageUri)
                    } else {
                        null
                    }
                    _currentProfile.value = profileImage?.let {
                        Profile(
                            userID = userId,
                            profileImage = it,
                            lastName = document.data?.get("lastName").toString(),
                            firstName = document.data?.get("firstName").toString(),
                            userName = document.data?.get("userName").toString(),
                            birthday = document.data?.get("birthday").toString(),
                            homeTown = document.data?.get("homeTown").toString(),
                            email = document.data?.get("email").toString()
                        )
                    }
                } else {
                    Log.d("Firebase", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Firebase", "get failed with ", exception)
            }
    }
}
