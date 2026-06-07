package com.omero.yeni.vievmodel

import androidx.lifecycle.ViewModel
import com.omero.yeni.repo.CharacterRepository
import com.omero.yeni.room.CharacterEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: CharacterRepository) :
    ViewModel() {

    fun getCharacter(id: Int): Flow<CharacterEntity> {
        return repository.getCharacterById(id)
    }
}