package com.example.abschlussaufgabe.data.datamodels

import android.widget.ImageView
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "userData_table")
data class Profile(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val profileImage: Int,
    val lastName: String,
    val firstName: String,
    val userName: String,
    val birthday: String,
    val phone: String,
    val email: String

)