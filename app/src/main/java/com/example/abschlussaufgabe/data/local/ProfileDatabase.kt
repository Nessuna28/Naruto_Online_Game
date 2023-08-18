package com.example.abschlussaufgabe.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.abschlussaufgabe.UriTypeConverter
import com.example.abschlussaufgabe.data.datamodels.Profile

@Database(entities = [Profile::class], version = 1)
@TypeConverters(UriTypeConverter::class)
abstract class ProfileDatabase: RoomDatabase() {

    abstract val profileDao: ProfileDao

    companion object {
        private lateinit var INSTANCE: ProfileDatabase

        fun getDatabase(context: Context): ProfileDatabase {
            synchronized(this) {
                if (!this::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ProfileDatabase::class.java,
                        "profile_database"
                    ).build()
                }
                return INSTANCE
            }
        }
    }
}