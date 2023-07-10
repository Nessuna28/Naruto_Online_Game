package com.example.abschlussaufgabe.data.datamodels.modelsApi

import com.squareup.moshi.Json

data class Family(

    val father: String? = "",
    val mother: String? = "",
    val sister: String? = "",
    val brother: String? = "",
    @Json(name = "adoptive brother")
    val adoptiveBrother: String? = "",
    @Json(name = "adoptive sister")
    val adoptiveSister: String? = "",
    @Json(name = "incarnation with the god tree")
    val incarnation: String? = "",
    @Json(name = "depowered form")
    val depoweredForm: String? = ""
)