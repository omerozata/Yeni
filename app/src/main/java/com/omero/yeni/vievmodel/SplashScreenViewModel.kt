package com.omero.yeni.vievmodel

import androidx.lifecycle.ViewModel
import com.omero.yeni.datastore.DataStoreManager
import com.omero.yeni.repo.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val repository: CharacterRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
}