package com.example.abschlussaufgabe.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.abschlussaufgabe.data.datamodels.modelForFight.fightDataForDatabase.Player

@Dao
interface PlayerDatabaseDao {

    // Funktion die die Daten in die Tabelle einfügt
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(playerData: Player)


    // Funktion die alle Daten aus der Tabelle zurück gibt
    @Query("SELECT * FROM player_table")
    fun getAllData(): LiveData<List<Player>>


    // Funktion mit der Daten anhand des Namen aus der Tabelle zurückgegeben werden
    @Query("SELECT * FROM player_table WHERE characterName = :name")
    fun getDataByName(name: String): LiveData<Player>


    // Funktion mit der Daten in die Tabelle eingefügt werden können
    @Update
    fun updateData(data: Player)


    // Funktion mit der Daten anhand des Namen aus der Tabelle gelöscht werden können
    @Query("DELETE FROM player_table WHERE characterName = :name")
    fun deleteByName(name: String)


    // Funktion die alle Daten aus der Tabelle löscht
    @Query("DELETE FROM player_table")
    suspend fun deleteAllData()
}