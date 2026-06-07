package com.omero.yeni.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.omero.yeni.R
import com.omero.yeni.databinding.ItemCharacterBinding
import com.omero.yeni.room.CharacterEntity

class CharacterAdapter(private val onItemClick: (Int) -> Unit) :
    ListAdapter<CharacterEntity, CharacterAdapter.CharacterViewHolder>(
        CharacterDiffCallback()
    ) {

    // ViewBinding ile satır tasarımını bağlıyoruz
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = ItemCharacterBinding.inflate(
            LayoutInflater.from(
                parent.context
            ), parent, false
        )
        return CharacterViewHolder(binding)
    }

    // Verileri ekrandaki görünümlere atıyoruz
    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = getItem(position)
        holder.bind(character)
    }

    inner class CharacterViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(character: CharacterEntity) {
            binding.tvName.text = character.name
            binding.tvSpecies.text = character.species
            // İŞTE COIL'İN SİHRİ! Tek satırda internetten resmi indir, önbelleğe al ve ImageView'a bas
            binding.ivCharacter.load(character.imageUrl) {
                crossfade(true) // Resmi yüklerken yumuşak bir geçiş efekti verir
                error(R.drawable.ic_launcher_background) // Hata olursa gösterilecek resim (opsiyonel)
            }

            // Tüm satıra (root) tıklandığında karakterin ID'sini dışarı fırlat
            binding.root.setOnClickListener {
                onItemClick(character.id)
            }

        }
    }

    class CharacterDiffCallback : DiffUtil.ItemCallback<CharacterEntity>() {
        override fun areItemsTheSame(oldItem: CharacterEntity, newItem: CharacterEntity): Boolean {
            return oldItem.id == newItem.id // ID'ler aynı mı?
        }

        override fun areContentsTheSame(
            oldItem: CharacterEntity,
            newItem: CharacterEntity
        ): Boolean {
            return oldItem == newItem // İçerik tamamen aynı mı?
        }
    }
}