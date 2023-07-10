package com.example.abschlussaufgabe.data.datamodels.modelsApi

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class Characters(

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val name: String,

    @Json(name = "images")
    val image: List<String>,

    val jutsu: List<String>,
    val natureType: List<String>,
    val personal: Personal,
    val rank: Rank,
    val family: Family,
    val uniqueTraits: List<String>,
    val tools: List<String>

)