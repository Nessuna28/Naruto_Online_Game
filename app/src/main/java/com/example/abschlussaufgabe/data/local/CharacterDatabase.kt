package com.example.abschlussaufgabe.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.abschlussaufgabe.data.datamodels.modelsApi.Character

/*@Database(entities = [Character::class], version = 1)
abstract class CharacterDatabase: RoomDatabase() {

    abstract val characterDao: CharacterDatabaseDao

    companion object {
        private lateinit var INSTANCE: CharacterDatabase

        fun getDatabase(context: Context): CharacterDatabase {
            synchronized(this) {
                if (!this::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        CharacterDatabase::class.java,
                        "character_database"
                    ).build()
                }
                return INSTANCE
            }
        }
    }
}

 */