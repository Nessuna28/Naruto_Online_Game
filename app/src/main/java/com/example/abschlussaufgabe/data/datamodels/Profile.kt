package com.example.abschlussaufgabe.data.datamodels

import android.net.Uri


data class Profile(

    val userID: String,
    val profileImage: Uri,
    val lastName: String,
    val firstName: String,
    val userName: String,
    val birthday: String,
    val homeTown: String,
    val email: String

)