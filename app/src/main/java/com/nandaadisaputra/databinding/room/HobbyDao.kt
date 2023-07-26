package com.nandaadisaputra.databinding.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HobbyDao {
    /*untuk menyimpan data*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(users: Hobby)

    /*untuk mengambil data*/
    @Query("SELECT * FROM hobby ORDER BY id DESC")
    fun getAllHobby(): Flow<List<Hobby>>
}