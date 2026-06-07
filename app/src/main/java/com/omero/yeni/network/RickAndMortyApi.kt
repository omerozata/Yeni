package com.omero.yeni.network

import retrofit2.http.GET


interface RickAndMortyApi {
    // Base URL'mizi Hilt'te (https://rickandmortyapi.com/api/) vermiştik.
    // Bu @GET onu tamamlıyor -> https://rickandmortyapi.com/api/character

    @GET("character")
    suspend fun getCharacters() : CharacterResponse
}