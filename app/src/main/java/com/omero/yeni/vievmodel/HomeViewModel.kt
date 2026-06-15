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

    // Room'dan gelen canlı akış borusunu doğrudan UI'ın dinlemesi için dışarı açıyoruz
    val charactersList = repository.getAllCharacters()


    init {
        // Uygulama açılır açılmaz sadece 1. sayfayı yükle
        fetchFirstPage()
    }

    private fun fetchFirstPage() {
        viewModelScope.launch {

            // Repository'deki yeni fonksiyonumuza sabit olarak 1 sayısını gönderiyoruz
            repository.fetchAndSaveCharacters(1)
        }
    }



}