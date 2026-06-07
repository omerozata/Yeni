package com.omero.yeni.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.omero.yeni.R
import com.omero.yeni.adapter.CharacterAdapter
import com.omero.yeni.databinding.FragmentHomeBinding
import com.omero.yeni.vievmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint // Hilt'i bu ekrana da tanıtıyoruz
class HomeFragment : Fragment(R.layout.fragment_home) {

    // ViewBinding için modern ve güvenli kurulum
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // Hilt sayesinde ViewModel'ı tek satırda, çok temiz bir şekilde çağırıyoruz
    private val viewModel: HomeViewModel by viewModels()

    private lateinit var characterAdapter: CharacterAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        // Artık tasarımdaki elemanlara binding.butonAdi şeklinde ulaşabilirsin!

        setupRecyclerView()
        observeViewModel()

    }

    private fun setupRecyclerView() {
        characterAdapter = CharacterAdapter { clickedCharacterId ->
            // Navigasyon Grafiğindeki oklu yolu ve göndereceğimiz ID'yi seçiyoruz
            val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(clickedCharacterId)
            // Portalı açarak diğer sayfaya geç!
            findNavController().navigate(action)

        }
        binding.rvCharacters.adapter = characterAdapter

    }

    private fun observeViewModel() {
        // Coroutine başlatıyoruz çünkü Flow dinlemek (collect) arka plan işidir
        viewLifecycleOwner.lifecycleScope.launch {
            // repeatOnLifecycle(STARTED): Kullanıcı uygulamayı arka plana attığında
            // internet/veri dinlemeyi durdurur, geri açtığında devam ettirir. Şarjı ve RAM'i korur
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.charactersList.collectLatest {
                    // ListAdapter'ın kendi fonksiyonu. Ajan (DiffUtil) burada devreye girer,
                    // eski listeyle bu gelen yeni listeyi kıyaslar ve ekranı pürüzsüzce günceller.
                    characterAdapter.submitList(it)
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Hafıza sızıntısını (memory leak) önlemek için
    }

}