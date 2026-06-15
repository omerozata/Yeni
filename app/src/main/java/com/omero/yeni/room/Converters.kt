package com.omero.yeni.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.omero.yeni.network.LocationReferenceDto

class Converters {
    private val gson = Gson()

    // 1. Liste (List<String>) için Çevirmenler

    @TypeConverter
    fun fromStringList(value: List<String>?): String {
        return gson.toJson(value) // Listeyi ["Bölüm 1", "Bölüm 2"] şeklinde metne çevirir
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType)
    }

    // 2. Lokasyon Objesi için Çevirmenler

    @TypeConverter
    fun fromLocation(value: LocationReferenceDto?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toLocation(value: String): LocationReferenceDto {
        return gson.fromJson(value, LocationReferenceDto::class.java)
    }

}