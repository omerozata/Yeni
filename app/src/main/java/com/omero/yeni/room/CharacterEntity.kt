package com.omero.yeni.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters_table")
data class CharacterEntity(
    @PrimaryKey // İnternetten kendi ID'leri geleceği için autoGenerate=true SİLDİK
    val id: Int,
    val name: String,
    val species: String,
    val imageUrl: String
)
