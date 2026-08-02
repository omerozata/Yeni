package com.omero.yeni.ui


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.omero.yeni.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

// :)

@AndroidEntryPoint
class SplashScreenFragment : Fragment(R.layout.fragment_splash_screen) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            // Görsellik ve MainViewModel'daki isteğe avans vermek için 2 saniye bekle
            delay(2000.milliseconds)

            // Home ekranına geç
            findNavController().navigate(R.id.action_splashScreenFragment_to_homeFragment)
        }
    }

}