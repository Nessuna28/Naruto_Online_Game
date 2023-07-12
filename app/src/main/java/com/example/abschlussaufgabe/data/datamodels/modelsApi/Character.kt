package com.example.abschlussaufgabe.data.datamodels.modelsApi


data class Character(

    val id: Int = 0,
    val name: String = "",
    val images: List<String> = listOf(),
    val jutsu: List<String> = listOf(),
    val natureType: List<String> = listOf(),
    //val personal: Personal = Personal("", "", "", "", "", "", listOf(), listOf(), listOf()),
    val rank: Rank = Rank(NinjaRank("", "")),
    val family: Family = Family("", "", "", "", "", "","", ""),
    val uniqueTraits: List<String> = listOf(),
    val tools: List<String> = listOf()
)