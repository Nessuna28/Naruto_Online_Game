package com.example.abschlussaufgabe.data.remote

import com.example.abschlussaufgabe.data.datamodels.modelsApi.CharacterList
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

const val BASE_URL = "https://api.narutodb.xyz"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


interface CharacterApiService {

    @GET("/character")
    suspend fun getCharacterList(): CharacterList

}

object CharacterApi {
    val retrofitService: CharacterApiService by lazy { retrofit.create(CharacterApiService::class.java) }
}