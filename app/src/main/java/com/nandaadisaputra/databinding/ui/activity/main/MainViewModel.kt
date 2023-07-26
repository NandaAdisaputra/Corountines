package com.nandaadisaputra.databinding.ui.activity.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.nandaadisaputra.databinding.room.Hobby
import com.nandaadisaputra.databinding.room.HobbyDao
import com.nandaadisaputra.databinding.room.HobbyDatabase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel(private val hobbyDao: HobbyDao): ViewModel() {
    /*Kita berikan isi variabel hobby dengan perintah mengambil data*/
    /*Karena nilainya tetap tidak berubah ubah value nya maka kita pakai val bukan var*/
    val hobby = hobbyDao.getAllHobby()
    /*Kita tuliskan type Private karena hanya diakses oleh class ini*/
    private val _responseSave = Channel<Boolean>()
    /*Untuk mengetahui respon save*/
    val responseSave = _responseSave.receiveAsFlow()
    /*Untuk menambahkan hobby ke table*/
    fun addHobby(hobby: Hobby) = viewModelScope.launch {
        hobbyDao.insert(hobby)
        _responseSave.send(true)
    }
    // Define ViewModel factory in a companion object
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val hobbyDao = HobbyDatabase.getInstance(this[APPLICATION_KEY]?.applicationContext!!).hobbyDao()
                MainViewModel(hobbyDao)
            }
        }
    }
}