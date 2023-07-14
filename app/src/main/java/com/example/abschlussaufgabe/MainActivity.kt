package com.example.abschlussaufgabe

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.abschlussaufgabe.databinding.ActivityMainBinding
import com.example.abschlussaufgabe.ui.MainViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel._imageTitle.value = binding.ivTitle
        viewModel._imageHome.value = binding.ivHome
        viewModel._imageBackground.value = binding.ivBackground


        binding.ivSettings.setOnClickListener {
            TODO()
        }

        binding.mcProfile.setOnClickListener {
            TODO()
        }
    }
}