package com.example.abschlussaufgabe.data.local


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.abschlussaufgabe.data.datamodels.Profile
import com.example.abschlussaufgabe.data.datamodels.modelForFight.fightDataForDatabase.DataPlayer

@Dao
interface GameDao {

    // Funktion die die Daten in die Tabelle einfügt
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDataGame(dataUserData: DataPlayer)


    // Funktion die alle Daten aus der Tabelle zurück gibt
    @Query("SELECT * FROM player_table ORDER BY date DESC")
    suspend fun getAllDataGame(): List<DataPlayer>


    // Funktion die alle Daten aus der Tabelle löscht
    @Query("DELETE FROM player_table")
    suspend fun deleteAllDataGame()



    // Funktion die die Profildaten vom User einfügt
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDataProfile(dataUserData: Profile)


    // Funktion die alle Daten eines Profils zurück gibt
    @Query("SELECT * FROM userData_table")
    suspend fun getAllDataProfile(): Profile


    // Funktion mit der Daten anhand des Vornamen aus dem Profil zurückgegeben werden
    @Query("SELECT * FROM userData_table WHERE firstName = :name")
    suspend fun getDataByName(name: String): Profile


    // Funktion mit der Daten in die Tabelle eingefügt werden können
    @Update
    suspend fun updateDataProfile(data: Profile)


    // Funktion die alle Daten aus dem Profil löscht
    @Query("DELETE FROM userData_table")
    suspend fun deleteAllDataProfile()
}