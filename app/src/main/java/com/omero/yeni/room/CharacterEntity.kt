package com.omero.yeni.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.omero.yeni.network.LocationReferenceDto

@Entity(tableName = "characters_table")
data class CharacterEntity(
    @PrimaryKey // İnternetten kendi ID'leri geleceği için autoGenerate=true SİLDİK
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: LocationReferenceDto, // Çevirmen sayesinde hata vermeyecek
    val location: LocationReferenceDto, // Çevirmen sayesinde hata vermeyecek
    val imageUrl: String,
    val episode: List<String>, // Çevirmen sayesinde hata vermeyecek
    val url: String,
    val created: String
)
