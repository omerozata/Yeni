package com.omero.yeni.vievmodel

import androidx.lifecycle.ViewModel
import com.omero.yeni.datastore.DataStoreManager
import com.omero.yeni.repo.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject


@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val repository: CharacterRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    suspend fun prepareAppConfiguration() {
        try {
            // 1. Kullanıcının kaldığı son sayfayı hafızadan öğren
            val targetPage = dataStoreManager.lastPage.first()

            // 2. API'ye sadece o sayfa için istek at ve güncel faturayı (maxPage) al
            val fetchedMax = repository.fetchAndSaveCharacters(page = targetPage)

            // 3. Hafızayı yeni güncel değerlerle yenile
            dataStoreManager.savePreferences(currentPage = targetPage, maxPage = fetchedMax)

        } catch (e: Exception) {
            // İnternet yoksa veya API çökerse uygulama patlamaz, DataStore'daki eski verilerle devam eder.
        }
    }
}