package com.example.abschlussaufgabe.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.abschlussaufgabe.data.datamodels.modelsApi.Characters

@Dao
interface CharacterDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<Characters>)

    @Query("SELECT * FROM Characters")
    fun getAll(): LiveData<List<Characters>>
}