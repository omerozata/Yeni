package com.omero.yeni.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.omero.yeni.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ViewBinding kurulumu
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}