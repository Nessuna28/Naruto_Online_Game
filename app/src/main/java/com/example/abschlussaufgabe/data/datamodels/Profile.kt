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

    // hier wird der Download-URL in eine Uri konvertiert und zurückgegeben
    fun getProfileImageUri(): Uri {

        return Uri.parse(profileImage)
    }
}