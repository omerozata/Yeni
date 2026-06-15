package com.omero.yeni.repo

import com.omero.yeni.network.RickAndMortyApi
import com.omero.yeni.network.toEntity
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

    //İnternetten yeni sayfayı çekip Room'u güncelleyen ana fonksiyonumuz
    suspend fun fetchAndSaveCharacters(page: Int): Int {
        return try {

            // API'ye sayfa numarasını gönderiyoruz (Diğer filtreleri şimdilik null bıraktık)
            val response = api.getCharacters(page = page)

            if (response.isSuccessful && response.body() != null) {
                val body = response.body()!!

                // Gelen zengin DTO listesini, yazdığımız .toEntity() köprüsüyle Room modeline çeviriyoruz

                val characterEntities = body.results.map { it.toEntity() }

                // API'den gelen toplam sayfa sayısını yakalıyoruz (İleride sağ oku kısıtlamak için)

                val totalPages = body.info.pages

                // Önce eski sayfanın 20 karakterini temizliyoruz

                dao.clearAllCharacters()

                // Sonra yepyeni ve dopdolu 20 karakteri Room'a yazıyoruz

                dao.insertCharacters(characterEntities)

                // Her şey yolunda gittiyse toplam sayfa sayısını döndür
                totalPages
            } else {
                // API'den başarısız bir kod döndüyse varsayılan olarak 1 dönüyoruz
                1
            }
        } catch (e: Exception) {
            e.printStackTrace()
            1
        }
    }


}