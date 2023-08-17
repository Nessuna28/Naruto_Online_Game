package com.example.abschlussaufgabe.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.abschlussaufgabe.data.datamodels.Profile

@Dao
interface ProfileDao {

    // Funktion die die Daten einfügt
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(dataUserData: Profile)


    // Funktion die alle Daten eines Profils zurück gibt
    @Query("SELECT * FROM userData_table")
    suspend fun getAllData(): Profile


    // Funktion mit der Daten anhand des Vornamen aus dem Profil zurückgegeben werden
    @Query("SELECT * FROM userData_table WHERE firstName = :name")
    suspend fun getDataByName(name: String): Profile


    // Funktion mit der Daten in die Tabelle eingefügt werden können
    @Update
    suspend fun updateData(data: Profile)


    // Funktion mit der Daten anhand des Vornamen aus dem Profil gelöscht werden können
    @Query("DELETE FROM userData_table WHERE firstName = :name")
    suspend fun deleteByName(name: String)


    // Funktion die alle Daten aus dem Profil löscht
    @Query("DELETE FROM userData_table")
    suspend fun deleteAllData()
}