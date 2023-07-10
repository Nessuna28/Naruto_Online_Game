package com.example.abschlussaufgabe.data.datamodels.modelsApi

import androidx.room.Entity
import com.squareup.moshi.Json

@Entity
data class NinjaRank(

    @Json(name = "Part I")
    val part1: String,

    @Json(name = "Part II")
    val part2: String
)