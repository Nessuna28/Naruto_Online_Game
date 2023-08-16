package com.example.abschlussaufgabe.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.abschlussaufgabe.databinding.ActivityMainBinding
import com.example.abschlussaufgabe.MainViewModel
import androidx.activity.viewModels
import com.example.abschlussaufgabe.R


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel.profile.observe(this) {
            binding.tvUserName.text = it.userName
            binding.ivProfilePhoto.setImageResource(it.profileImage)
        }


        viewModel._imageTitle.value = binding.ivTitle
        viewModel._imageHome.value = binding.ivHome
        viewModel._imageBackground.value = binding.ivBackground
        viewModel._materialCard.value = binding.materialCardView
        viewModel._userName.value = binding.tvUserName
        viewModel._imageSettings.value = binding.ivSettings
        viewModel._imageProfile.value = binding.mcProfile


        binding.ivSettings.setOnClickListener {
            TODO()
        }
    }
}