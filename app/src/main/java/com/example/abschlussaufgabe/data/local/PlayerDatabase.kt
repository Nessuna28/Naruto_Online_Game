package com.example.abschlussaufgabe.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.abschlussaufgabe.data.datamodels.modelForFight.fightDataForDatabase.Player

@Database(entities = [Player::class], version = 1)
abstract class PlayerDatabase: RoomDatabase() {

    abstract val playerDao: PlayerDatabaseDao

    companion object {
        private lateinit var INSTANCE: PlayerDatabase

        fun getDatabase(context: Context): PlayerDatabase {
            synchronized(this) {
                if (!this::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        PlayerDatabase::class.java,
                        "player_database"
                    ).build()
                }
                return INSTANCE
            }
        }
    }
}