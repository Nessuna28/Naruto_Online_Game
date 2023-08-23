package com.example.abschlussaufgabe.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.abschlussaufgabe.databinding.ActivityMainBinding
import com.example.abschlussaufgabe.MainViewModel
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import com.example.abschlussaufgabe.FirestoreViewModel
import com.example.abschlussaufgabe.R


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()
    private val storeViewModel: FirestoreViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel.profile?.observe(this) {
            //binding.ivProfilePhoto.setImageURI(it.profileImage)
        }

        storeViewModel.currentProfile.observe(this) {
            if (it != null) {
                binding.tvUserName.text = it.userName
                binding.ivProfilePhoto.setImageURI(it.profileImage)
            }
        }


        viewModel._imageTitle.value = binding.ivTitle
        viewModel._imageProfile.value = binding.mcProfile
        viewModel._tvUserName.value = binding.tvUserName
        viewModel._imageHome.value = binding.ivHome
        viewModel._imageSettings.value = binding.ivSettings
        viewModel._imageBackground.value = binding.ivBackground
        viewModel._materialCard.value = binding.materialCardView
    }
}