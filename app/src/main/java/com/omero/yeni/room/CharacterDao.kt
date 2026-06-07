package com.omero.yeni.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) // Aynı karakter gelirse üzerine yaz
    suspend fun insertCharacters(characters: List<CharacterEntity>)

    @Query("SELECT * FROM characters_table")
    fun getAllCharacters(): Flow<List<CharacterEntity>>

    @Query("SELECT * FROM characters_table WHERE id = :id")
    fun getCharacterById(id: Int) : Flow<CharacterEntity>
}