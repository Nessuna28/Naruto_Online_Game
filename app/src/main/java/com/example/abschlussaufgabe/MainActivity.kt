package com.example.abschlussaufgabe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.abschlussaufgabe.databinding.ActivityMainBinding
import com.example.abschlussaufgabe.ui.AboutTheCharactersFragmentDirections
import com.example.abschlussaufgabe.ui.CharacterDetailFragmentDirections
import com.example.abschlussaufgabe.ui.HomeFragment
import com.example.abschlussaufgabe.ui.HomeFragmentDirections

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.ivHome.setOnClickListener {
            TODO()
        }

        binding.ivSettings.setOnClickListener {
            TODO()
        }

        binding.mcProfile.setOnClickListener {
            TODO()
        }
    }
}