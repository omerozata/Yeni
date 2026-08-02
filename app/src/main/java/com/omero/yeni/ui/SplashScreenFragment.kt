package com.omero.yeni.ui


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.omero.yeni.R
import com.omero.yeni.vievmodel.SplashScreenViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull

// :)

@AndroidEntryPoint
class SplashScreenFragment : Fragment(R.layout.fragment_splash_screen) {

private val viewModel: SplashScreenViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            // Splash ekranında veriyi hazırlamak için en fazla 3 saniye veriyoruz
            withTimeoutOrNull(3000) {
                viewModel.prepareAppConfiguration()
            }
            // Süre dolduğunda veya veri geldiğinde Home ekranına zıpla
            findNavController().navigate(R.id.action_splashScreenFragment_to_homeFragment)
        }
    }

}