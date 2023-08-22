package com.example.abschlussaufgabe.data.local


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
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
    fun getAllDataGame(): LiveData<List<DataPlayer>>


    // Funktion die die übergebenen Daten aus der Tabelle löscht
    @Delete
    suspend fun deleteAllDataGame(dataPlayer: DataPlayer)



    // Funktion die die Profildaten vom User einfügt
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDataProfile(dataUserData: Profile)


    // Funktion die alle Daten eines Profils zurück gibt
    @Query("SELECT * FROM userData_table")
    fun getAllDataProfile(): LiveData<Profile>


    // Funktion mit der Daten anhand des Vornamen aus dem Profil zurückgegeben werden
    @Query("SELECT * FROM userData_table WHERE email = :email")
    fun getDataByEmail(email: String): LiveData<Profile>


    // Funktion mit der Daten aus der Tabelle geändert werden können
    @Update
    suspend fun updateDataProfile(profile: Profile)


    // Funktion die die übergebenen Profildaten aus der Tabelle löscht
    @Delete
    suspend fun deleteAllDataProfile(profile: Profile)
}