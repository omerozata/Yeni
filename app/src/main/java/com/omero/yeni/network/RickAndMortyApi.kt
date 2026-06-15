package com.omero.yeni.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface RickAndMortyApi {
    // Base URL'mizi Hilt'te (https://rickandmortyapi.com/api/) vermiştik.
    // Bu @GET onu tamamlıyor -> https://rickandmortyapi.com/api/character

    // Nullable (String?) parametreler kullanıyoruz.
    // Eğer bir parametreye değer göndermezsek (null bırakırsak), Retrofit onu URL'ye eklemez.
    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int? = null,
        @Query("name") name: String? = null, // İsimle arama yapmak için
        @Query("status") status: String? = null, // alive, dead, unknown
        @Query("species") species: String? = null, // alien, human vb.
        @Query("type") type: String? = null, // alt tür veya varyantı
        @Query("gender") gender: String? = null // female, male, genderless, unknown
    ): Response<CharacterResponse>

    // Tek bir karakterin tüm detaylarını ID'si ile çekmek için (Ekstra yetenek)
    @GET("character/{id}")
    suspend fun getSingleCharacter(
        @Path("id") id: Int
    ): Response<CharacterDto>
}