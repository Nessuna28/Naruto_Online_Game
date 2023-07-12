package com.example.abschlussaufgabe.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.abschlussaufgabe.data.datamodels.modelForFight.CharacterForFight
import com.example.abschlussaufgabe.data.datamodels.modelForFight.characterData.CharacterListForFight
import com.example.abschlussaufgabe.data.datamodels.modelsApi.CharacterList

@Dao
interface CharacterDatabaseDao {

    // Funktion die die Daten in die Tabelle einfügt
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(characters: CharacterForFight)


    // Funktion die alle Daten aus der Tabelle zurück gibt
    @Query("SELECT * FROM character_table")
    fun getAllCharacters(): LiveData<List<CharacterForFight>>


    // Funktion mit der Daten anhand des Namen aus der Tabelle zurückgegeben werden
    @Query("SELECT * FROM character_table WHERE name = :name")
    fun getCharacterByName(name: String): LiveData<CharacterForFight>


    // Funktion mit der Daten in die Tabelle eingefügt werden können
    @Update
    fun updateCharacter(character: CharacterForFight)


    // Funktion mit der Daten anhand des Namen aus der Tabelle gelöscht werden können
    @Query("DELETE FROM character_table WHERE name = :name")
    fun deleteByName(name: String)


    // Funktion die alle Daten aus der Tabelle löscht
    @Query("DELETE FROM character_table")
    suspend fun deleteAllCharacters()
}