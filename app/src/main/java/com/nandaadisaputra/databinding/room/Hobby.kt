package com.nandaadisaputra.databinding.room

import androidx.room.ColumnInfo
import androidx.room.Entity

import android.os.Parcelable
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/*bungkus dengan interface Parcelable untuk Implementasi Intent mengirim Data dengan Parcelable."*/
@Parcelize
@Entity(tableName = "hobby")
data class Hobby(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "yourHobby")
    val yourHobby: String?
) : Parcelable