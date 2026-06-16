package com.omero.yeni.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

@Singleton
class DataStoreManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        val LAST_PAGE_KEY = intPreferencesKey("last_page")
        val MAX_PAGE_KEY = intPreferencesKey("max_page")
    }

    suspend fun savePreferences(currentPage: Int, maxPage: Int) {
        context.dataStore.edit { preferences ->
            preferences[LAST_PAGE_KEY] = currentPage
            preferences[MAX_PAGE_KEY] = maxPage
        }
    }

    // Varsayılan sayfa 1
    val lastPage: Flow<Int> = context.dataStore.data.map { it[LAST_PAGE_KEY] ?: 1 }

    // Varsayılan maxPage 0 (Bilinmiyor durumu)
    val maxPage: Flow<Int> = context.dataStore.data.map { it[MAX_PAGE_KEY] ?: 0 }

}
