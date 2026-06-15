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

    //Sayfa Hafızaları
    private val _currentPage = MutableStateFlow(1)
    val currentPage: StateFlow<Int> = _currentPage.asStateFlow()

    private val _maxPage = MutableStateFlow(1)
    val maxPage: StateFlow<Int> = _maxPage.asStateFlow()

    init {
        // Uygulama açılır açılmaz sadece 1. sayfayı yükle
        loadPage(1)
    }

    // Yükleme Motoru
    private fun loadPage(page: Int) {
        viewModelScope.launch {
            // Hangi sayfayı yüklediğimizi hafızaya al
            _currentPage.value = page

            // Repodan sayfayı çek ve dönen toplam sayfa sayısını (maxPage) hafızaya al
            val fetchedMax = repository.fetchAndSaveCharacters(page)
            _maxPage.value = fetchedMax
        }
    }

    // ok Tuşları İçin Tetikleyiciler

    fun nextPage() {
        if (_currentPage.value < _maxPage.value) {
            loadPage(_currentPage.value + 1)
        }
    }

    fun previousPage() {
        if (_currentPage.value > 1) {
            loadPage(_currentPage.value - 1)
        }
    }

}