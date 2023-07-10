package com.example.abschlussaufgabe.data.datamodels.modelsApi

import androidx.room.Entity
import com.squareup.moshi.Json

@Entity
data class Personal(

    val status: String,
    val sex: String,
    val clan: String,
    val kekkeiGenkai: String,
    val classification: String,

    @Json(name = "jinchūriki")
    val jinchuriki: List<String>,

    val affiliation: List<String>,
    val occupation: List<String>
)