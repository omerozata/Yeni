package com.omero.yeni.network

import com.google.gson.annotations.SerializedName

// API'den gelen en dıştaki JSON yapısını karşılar
data class CharacterResponse(
    @SerializedName("results")
    val results: List<CharacterDto>
)

// Listedeki her bir karakterin detaylarını karşılar
data class CharacterDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("species")
    val species: String,
    @SerializedName("image")
    val imageUrl: String
)
