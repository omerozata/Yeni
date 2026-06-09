package com.omero.yeni.di

import com.omero.yeni.network.RickAndMortyApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // 1. Adım: Ağ trafiğini dinleyecek olan Ajanı (Interceptor) oluşturuyoruz
    @Provides
    @Singleton
    fun provideLoggingInterceptor() : HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            // Level.BODY: Gönderilen isteklerin ve gelen cevapların (JSON dahil) her şeyini loglar.
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    // 2. Adım: Retrofit'in arkasında çalışan asıl motoru (OkHttpClient) kuruyoruz
    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor) : OkHttpClient {
        return OkHttpClient.Builder()
        // Az önce oluşturduğumuz ajanı motorun içine entegre ediyoruz
            .addInterceptor(loggingInterceptor)
            .build()
    }

    // 3. Adım: Retrofit nesnesini oluştururken yukarıdaki motoru içine veriyoruz
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/") // Rick and Morty'nin ana adresi
            .addConverterFactory(GsonConverterFactory.create()) //Gelen JSON'ı Kotlin objesine çevir
            // Profesyonel dokunuş: Standart motor yerine kendi özelleştirilmiş motorumuzu bağladık
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideRickAndMortyApi(retrofit: Retrofit) : RickAndMortyApi {
        return retrofit.create(RickAndMortyApi::class.java)
    }

}