package com.example.abschlussaufgabe.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.abschlussaufgabe.data.datamodels.modelForFight.fightDataForDatabase.DataPlayer

@Dao
interface PlayerDatabaseDao {

    // Funktion die die Daten in die Tabelle einfügt
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(dataUserData: DataPlayer)


    // Funktion die alle Daten aus der Tabelle zurück gibt
    @Query("SELECT * FROM player_table ORDER BY date DESC")
    suspend fun getAllData(): List<DataPlayer>


    // Funktion mit der Daten anhand des Namen aus der Tabelle zurückgegeben werden
    @Query("SELECT * FROM player_table WHERE characterName = :name")
    suspend fun getDataByName(name: String): DataPlayer


    // Funktion mit der Daten in die Tabelle eingefügt werden können
    @Update
    suspend fun updateData(data: DataPlayer)


    // Funktion mit der Daten anhand des Namen aus der Tabelle gelöscht werden können
    @Query("DELETE FROM player_table WHERE characterName = :name")
    suspend fun deleteByName(name: String)


    // Funktion die alle Daten aus der Tabelle löscht
    @Query("DELETE FROM player_table")
    suspend fun deleteAllData()
}