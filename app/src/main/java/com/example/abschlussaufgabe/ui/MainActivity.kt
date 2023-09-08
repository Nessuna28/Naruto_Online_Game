package com.example.abschlussaufgabe.ui

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.abschlussaufgabe.databinding.ActivityMainBinding
import com.example.abschlussaufgabe.MainViewModel
import androidx.activity.viewModels
import com.example.abschlussaufgabe.AuthViewModel
import com.example.abschlussaufgabe.FirestoreViewModel
import com.example.abschlussaufgabe.R
import com.example.abschlussaufgabe.data.datamodels.RandomProfileImage
import com.squareup.picasso.Picasso


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()
    private val storeViewModel: FirestoreViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        authViewModel.currentUser.observe(this) {
            if (it != null) {
                storeViewModel.getUserData(it.uid)
                storeViewModel.currentProfile.observe(this) {
                    if (it != null) {
                        binding.tvUserName.text = it.userName
                        // Das Bild in einer ImageView anzeigen
                        Picasso.get()
                            .load(it.profileImage)
                            .placeholder(R.drawable.placeholder_image)
                            .into(binding.ivProfilePhoto)
                    }
                }
            } else {
                binding.tvUserName.setText(R.string.guest)
                binding.ivProfilePhoto.setImageURI(createProfileImage())
            }
        }


        viewModel._imageTitle.value = binding.ivTitle
        viewModel._cVImageProfile.value = binding.mcProfile
        viewModel._tvUserName.value = binding.tvUserName
        viewModel._imageHome.value = binding.ivHome
        viewModel._imageSettings.value = binding.ivSettings
        viewModel._imageBackground.value = binding.ivBackground
        viewModel._materialCard.value = binding.materialCardView
    }

    private fun createProfileImage(): Uri {

        val randomImage = RandomProfileImage().imageList.random()

        return Uri.parse("android.resource://com.example.abschlussaufgabe/drawable/${randomImage}")
    }
}