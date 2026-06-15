package com.omero.yeni.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

// DİKKAT: version = 2 yaptık!
@Database(entities = [CharacterEntity::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class) // Çevirmenleri Room'a tanıttık
abstract class AppDatabase : RoomDatabase(){
    abstract fun characterDao(): CharacterDao
}