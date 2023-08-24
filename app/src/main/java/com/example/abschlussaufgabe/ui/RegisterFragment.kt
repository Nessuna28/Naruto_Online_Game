package com.example.abschlussaufgabe.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.abschlussaufgabe.AuthViewModel
import com.example.abschlussaufgabe.MainViewModel
import com.example.abschlussaufgabe.R
import com.example.abschlussaufgabe.data.datamodels.Profile
import com.example.abschlussaufgabe.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val authViewModel: AuthViewModel by activityViewModels()
    private val viewModel: MainViewModel by activityViewModels()

    private val PICK_IMAGE_REQUEST = 1
    private var profileImage: Uri = Uri.EMPTY


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivProfilePhoto.setOnClickListener {
            openImagePicker()
        }

        binding.btnRegister.setOnClickListener {
            val email: String = binding.tietEmail.text.toString()
            val password: String = binding.tietPassword.text.toString()
            val password2: String = binding.tietPasswordRepeat.text.toString()
            if (email != "" && password != "") {
                if (password == password2) {
                    authViewModel.register(email, password)
                    setProfile()
                } else {
                    authViewModel.setMessage("Die beiden Passwörter müssen identisch sein!")
                    authViewModel.showToast(requireContext())
                }
            } else {
                authViewModel.setMessage("Bitte gib deine Email und dein Passwort ein!")
                authViewModel.showToast(requireContext())
            }
        }

        binding.btnCancel.setOnClickListener {
            findNavController().navigateUp()
        }

        authViewModel.registerSuccess.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLogInFragment())
            }
        }
    }


    // ermöglicht es ein Bild aus der Galerie auszuwählen

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            profileImage = selectedImageUri!!
        }
    }

    // ertellt eine Variable mit den eingetragenen Daten und übergibt sie der Funktion
    // die die Daten in der Datenbank speichert
    private fun setProfile() {

        val currentImage: Uri = if(profileImage.toString().isNotEmpty()) {
            profileImage
        } else {
            createProfileImage()
        }

        val profile = Profile(
            userID = "folgt",
            profileImage = currentImage,
            lastName = binding.tietLastName.text.toString(),
            firstName = binding.tietFirstName.text.toString(),
            userName = binding.tietUserName.text.toString(),
            birthday = binding.tietBirthday.text.toString(),
            homeTown = binding.tietHomeTown.text.toString(),
            email = binding.tietEmail.text.toString()
            )

        viewModel.insertDataProfile(profile)
    }


    private fun createProfileImage(): Uri {

        val imageList = listOf(
            R.drawable.anko_face,
            R.drawable.asuma_face,
            R.drawable.choji_face,
            R.drawable.deidara_face,
            R.drawable.gaara_face,
            R.drawable.gai_face,
            R.drawable.hidan_face,
            R.drawable.hinata_face,
            R.drawable.ino_face,
            R.drawable.itachi_face,
            R.drawable.jiraiya_face,
            R.drawable.kabuto_face,
            R.drawable.kiba_face,
            R.drawable.naruto_face,
            R.drawable.sasuke_face,
            R.drawable.sakura_face,
            R.drawable.kakashi_face
        )
        val randomImage = imageList.random()

        return Uri.parse("android.resource://com.example.abschlussaufgabe/drawable/${randomImage}")
    }
}