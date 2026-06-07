package com.omero.yeni.repo

import com.omero.yeni.network.RickAndMortyApi
import com.omero.yeni.room.CharacterDao
import com.omero.yeni.room.CharacterEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


// @Inject constructor ile Hilt'e "Bu sınıfa gerekli olan Api ve Dao'yu sen ver" diyoruz
class CharacterRepository @Inject constructor(
    private val api: RickAndMortyApi,
    private val dao: CharacterDao
) {
    // 1. Ekranın (ViewModel'ın) doğrudan dinleyeceği Room veritabanı akışı
    fun getAllCharacters(): Flow<List<CharacterEntity>> {
        return dao.getAllCharacters()
    }

    fun getCharacterById(id: Int): Flow<CharacterEntity> {
        return dao.getCharacterById(id)
    }


    // 2. İnternetten veriyi çekip Room'a kaydedecek olan arka plan işlemi

    suspend fun fetchAndSaveCharacters() {
        try {
            val response = api.getCharacters()

            // Adım B: DTO'yu (İnternet Modeli) Entity'ye (Veritabanı Modeli) dönüştür

            val entities = response.results.map { dto ->
                CharacterEntity(
                    id = dto.id,
                    name = dto.name,
                    species = dto.species,
                    imageUrl = dto.imageUrl
                )
            }
            // Adım C: Dönüştürülmüş verileri Room veritabanına kaydet
            dao.insertCharacters(entities)

        } catch (e: Exception) {
            // Eğer internet yoksa veya sunucu çökmüşse kod buraya düşer.
            // Veritabanında eski veriler olduğu için uygulama çökmez, offline çalışmaya devam eder.
            e.printStackTrace()
        }
    }
}