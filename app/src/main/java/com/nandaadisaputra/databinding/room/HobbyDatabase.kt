package com.nandaadisaputra.databinding.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Hobby::class],
    /*versi database. Silahkan ubah versi yang lebih tinggi ketika
      kalian mengubah tabel pada class Hobby*/
    version = 2
)
abstract class HobbyDatabase : RoomDatabase() {
    abstract fun hobbyDao(): HobbyDao

    companion object {
        @Volatile
        private var INSTANCE: HobbyDatabase? = null

        @Synchronized
        fun getInstance(context: Context): HobbyDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            val instance = Room.databaseBuilder(context.applicationContext, HobbyDatabase::class.java, "Hobby_Database.db")
                .fallbackToDestructiveMigration()
                .build()
            INSTANCE = instance
            return instance
        }
    }
}