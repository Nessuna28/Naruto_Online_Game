package com.example.abschlussaufgabe.data.datamodels.modelsApi

import androidx.room.Entity
import com.squareup.moshi.Json

@Entity
data class Family(

    val father: String,
    val mother: String,
    val sister: String,
    val brother: String,
    @Json(name = "adoptive brother")
    val adoptiveBrother: String,
    @Json(name = "adoptive sister")
    val adoptiveSister: String
)