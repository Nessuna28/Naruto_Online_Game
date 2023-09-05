package com.example.abschlussaufgabe.data.datamodels

import android.net.Uri


data class Profile(

    val userID: String,
    val profileImage: String,
    val lastName: String,
    val firstName: String,
    val userName: String,
    val birthday: String,
    val homeTown: String,
    val email: String

) {

    fun getProfileImageUri(): Uri {
        // Hier konvertieren Sie den Download-URL in eine Uri und geben ihn zurück
        return Uri.parse(profileImage)
    }
}