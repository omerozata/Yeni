package com.omero.yeni.vievmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omero.yeni.repo.CharacterRepository
import com.omero.yeni.room.CharacterEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {

    private val _charactersList = MutableStateFlow<List<CharacterEntity>>(emptyList())
    val charactersList: StateFlow<List<CharacterEntity>> = _charactersList.asStateFlow()

    init {
        getCharactersFromDatabase()
        fetchCharactersFromApi()
    }


    // 1. Sürekli olarak Room veritabanını dinle ve liste güncellendikçe ekrana fırlat
    private fun getCharactersFromDatabase() {
        viewModelScope.launch {
            repository.getAllCharacters().collectLatest {
                _charactersList.value = it
            }
        }
    }

    // 2. Arka planda internetten güncel veriyi çek ve veritabanına kaydet
    private fun fetchCharactersFromApi() {
        viewModelScope.launch {
            repository.fetchAndSaveCharacters()
        }
    }
}