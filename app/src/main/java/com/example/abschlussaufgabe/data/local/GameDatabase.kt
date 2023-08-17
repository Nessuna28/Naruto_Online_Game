package com.example.abschlussaufgabe.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.abschlussaufgabe.data.datamodels.modelForFight.fightDataForDatabase.DataPlayer

@Database(entities = [DataPlayer::class], version = 1)
abstract class GameDatabase: RoomDatabase() {

    abstract val playerDao: GameDao

    companion object {
        private lateinit var INSTANCE: GameDatabase

        fun getDatabase(context: Context): GameDatabase {
            synchronized(this) {
                if (!this::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        GameDatabase::class.java,
                        "player_database"
                    ).build()
                }
                return INSTANCE
            }
        }
    }
}