package com.omero.yeni.di

import android.content.Context
import androidx.room.Room
import com.omero.yeni.room.AppDatabase
import com.omero.yeni.room.CharacterDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // Bu nesneler uygulama yaşadığı sürece yaşasın (Singleton)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "rick_and_morty_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCharacterDao(database: AppDatabase) : CharacterDao {
        return database.characterDao()
    }
}