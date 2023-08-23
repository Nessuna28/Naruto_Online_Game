package com.example.abschlussaufgabe.data.datamodels

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "userData_table")
data class Profile(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val userID: String,
    val profileImage: Uri,
    val lastName: String,
    val firstName: String,
    val userName: String,
    val birthday: String,
    val homeTown: String,
    val email: String

)