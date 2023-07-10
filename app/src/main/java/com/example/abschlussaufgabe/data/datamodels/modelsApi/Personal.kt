package com.example.abschlussaufgabe.data.datamodels.modelsApi

import com.squareup.moshi.Json

data class Personal(

    val species: String = "",
    val status: String = "",
    val sex: String = "",
    val clan: String = "",
    val kekkeiGenkai: String = "",
    val classification: String = "",

    @Json(name = "jinchūriki")
    val jinchuriki: List<String> = listOf(),

    val affiliation: List<String> = listOf(),
    val occupation: List<String> = listOf()
)