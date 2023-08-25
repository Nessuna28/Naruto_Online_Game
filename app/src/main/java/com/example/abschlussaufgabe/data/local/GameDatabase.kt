package com.example.abschlussaufgabe.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.abschlussaufgabe.UriTypeConverter
import com.example.abschlussaufgabe.data.datamodels.Profile
import com.example.abschlussaufgabe.data.datamodels.modelForFight.fightDataForDatabase.DataPlayer

@Database(entities = [DataPlayer::class], version = 1)
@TypeConverters(UriTypeConverter::class)
abstract class GameDatabase: RoomDatabase() {

    abstract val gameDao: GameDao


    companion object {
        private lateinit var INSTANCE: GameDatabase

        fun getDatabase(context: Context): GameDatabase {
            synchronized(this) {
                if (!this::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        GameDatabase::class.java,
                        "game_database"
                    ).build()
                }
                return INSTANCE
            }
        }
    }
}