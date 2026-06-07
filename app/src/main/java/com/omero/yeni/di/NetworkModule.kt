package com.omero.yeni.di

import com.omero.yeni.network.RickAndMortyApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/") // Rick and Morty'nin ana adresi
            .addConverterFactory(GsonConverterFactory.create()) //Gelen JSON'ı Kotlin objesine çevir
            .build()
    }

    @Provides
    @Singleton
    fun provideRickAndMortyApi(retrofit: Retrofit) : RickAndMortyApi {
        return retrofit.create(RickAndMortyApi::class.java)
    }

}