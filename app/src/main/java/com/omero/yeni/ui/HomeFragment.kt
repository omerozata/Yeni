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
        setupButtons()
        observeViewModel()

    }

    private fun setupRecyclerView() {
        characterAdapter = CharacterAdapter { clickedCharacterId ->
            // Navigasyon Grafiğindeki oklu yolu ve göndereceğimiz ID'yi seçiyoruz
            val action =
                HomeFragmentDirections.actionHomeFragmentToDetailsFragment(clickedCharacterId)
            // Portalı açarak diğer sayfaya geç!
            findNavController().navigate(action)

        }
        binding.rvCharacters.adapter = characterAdapter

    }

    private fun setupButtons() {
        binding.btnNext.setOnClickListener {
            viewModel.nextPage()
        }
        binding.btnPrev.setOnClickListener {
            viewModel.previousPage()
        }

        // Not: btnGo (Git) butonu şimdilik boş duruyor, ona dokunmuyoruz.
    }

    private fun observeViewModel() {
        // Coroutine başlatıyoruz çünkü Flow dinlemek (collect) arka plan işidir
        viewLifecycleOwner.lifecycleScope.launch {
            // repeatOnLifecycle(STARTED): Kullanıcı uygulamayı arka plana attığında
            // internet/veri dinlemeyi durdurur, geri açtığında devam ettirir. Şarjı ve RAM'i korur
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {


                // Liste Güncellemesi
                launch {
                    viewModel.charactersList.collectLatest { list ->
                        // ListAdapter'ın kendi fonksiyonu. Ajan (DiffUtil) burada devreye girer,
                        // eski listeyle bu gelen yeni listeyi kıyaslar ve ekranı pürüzsüzce günceller.
                        characterAdapter.submitList(list)
                        if (list.isNotEmpty()) {
                            binding.rvCharacters.scrollToPosition(0) // Yeni sayfada listeyi en başa sar
                        }
                    }
                }

                // Geçerli Sayfa (currentPage) Değiştiğinde Arayüzü Güncelle

                launch {
                    viewModel.currentPage.collectLatest { current ->
                        val max = viewModel.maxPage.value

                        // Ortadaki yazıyı güncelle
                        binding.tvPageInfo.text = "Sayfa $current / $max"

                        // Sol oku kontrol et (1. sayfadaysa kapat)
                        binding.btnPrev.isEnabled = current > 1
                        binding.btnPrev.alpha = if (current > 1) 1.0f else 0.3f

                        // Sağ oku kontrol et (Son sayfadaysa kapat)
                        binding.btnNext.isEnabled = current < max
                        binding.btnNext.alpha = if (current < max) 1.0f else 0.3f
                    }
                }

                // Maksimum Sayfa (maxPage) İlk Kez API'den Geldiğinde Arayüzü Güncelle
                launch {
                    viewModel.maxPage.collectLatest {max ->
                        val current = viewModel.currentPage.value

                        binding.tvPageInfo.text = "Sayfa $current / $max"

                        binding.btnNext.isEnabled = current < max
                        binding.btnNext.alpha = if (current < max) 1.0f else 0.3f
                    }
                }

            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Hafıza sızıntısını (memory leak) önlemek için
    }

}