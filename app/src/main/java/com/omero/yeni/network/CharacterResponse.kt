package com.omero.yeni.network

import com.google.gson.annotations.SerializedName

// API'den gelen en dıştaki JSON yapısını karşılar
// 1. En Dış Katman: API'nin ana cevabı
data class CharacterResponse(
    @SerializedName("info") val info: InfoDto,
    @SerializedName("results") val results: List<CharacterDto>
)

// 2. Sayfalama Bilgileri
data class InfoDto(
    @SerializedName("count") val count: Int, // Toplam karakter sayısı (örn: 826)
    @SerializedName("pages") val pages: Int, // Toplam sayfa sayısı (örn: 42)
    @SerializedName("next") val next: String?, // Sonraki sayfanın linki
    @SerializedName("prev") val prev: String? // Önceki sayfanın linki
)

// Listedeki her bir karakterin detaylarını karşılar
// 3. Karakterin Bütün Detayları
data class CharacterDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("status") val status: String,
    @SerializedName("species") val species: String,
    @SerializedName("type") val type: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("origin") val origin: LocationReferenceDto,
    @SerializedName("location") val location: LocationReferenceDto,
    @SerializedName("image") val imageUrl: String,
    @SerializedName("episode") val episode: List<String>,
    @SerializedName("url") val url: String,
    @SerializedName("created") val created: String
)

// 4. Origin ve Location içindeki ufak objeleri karşılayan sınıf
data class LocationReferenceDto(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)

