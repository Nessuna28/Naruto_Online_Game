package com.example.abschlussaufgabe.ui

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.abschlussaufgabe.AuthViewModel
import com.example.abschlussaufgabe.FirestoreViewModel
import com.example.abschlussaufgabe.MainViewModel
import com.example.abschlussaufgabe.R
import com.example.abschlussaufgabe.data.datamodels.Profile
import com.example.abschlussaufgabe.data.datamodels.RandomProfileImage
import com.example.abschlussaufgabe.databinding.FragmentCreateProfileBinding
import com.example.abschlussaufgabe.databinding.FragmentEditProfileBinding
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso

class CreateProfileFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private val storeViewModel: FirestoreViewModel by activityViewModels()
    private val authViewModel: AuthViewModel by activityViewModels()

    private lateinit var binding: FragmentCreateProfileBinding

    private val PICK_IMAGE_REQUEST = 1
    private var profileImage: Uri = Uri.EMPTY


    override fun onStart() {
        super.onStart()

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

        viewModel.imageBackground.value?.let { viewModel.hideImages(it) }
        viewModel.materialCard.value?.let { viewModel.hideMaterialCard(it) }
        viewModel.tvUserName.value?.let { viewModel.hideTextView(it) }
        viewModel.imageTitle.value?.let { viewModel.showImages(it) }
        viewModel.imageHome.value?.let { viewModel.showImages(it) }
        viewModel.imageSettings.value?.let { viewModel.showImages(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_profile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivProfilePhoto.setOnClickListener {
            openImagePicker()
            // hier wird das Bild in der ImageView aktualisiert
            Picasso.get()
                .load(profileImage)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .placeholder(R.drawable.placeholder_image)
                .into(binding.ivProfilePhoto)
        }

        binding.btnSave.setOnClickListener {
            authViewModel.currentUser.observe(viewLifecycleOwner) {
                setProfile(it!!.uid, it.email!!)
                storeViewModel.getUserData(it.uid)
            }
            findNavController().navigate(CreateProfileFragmentDirections.actionCreateProfileFragmentToLogInFragment())
        }

        binding.btnCancel.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    // Funktionen

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
    // die die Daten in Firestore speichert
    private fun setProfile(id: String, email: String) {
        var currentImage = Uri.EMPTY

        if (profileImage.toString().isNotEmpty()) {
            currentImage = profileImage
        } else {
            currentImage = createProfileImage()
        }

        val storageRef = FirebaseStorage.getInstance().reference
        val imageRef = storageRef.child("profile_images/${authViewModel.currentUser.value!!.uid}/profileImage.jpg")

        val imageStream = requireActivity().contentResolver.openInputStream(currentImage)

        val uploadTask = imageStream?.let { imageRef.putStream(it) }
        uploadTask?.addOnSuccessListener { taskSnapshot ->
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                val downloadUrl = uri.toString()

                val profile = Profile(
                    userID = id,
                    profileImage = downloadUrl,
                    lastName = binding.tietLastName.text.toString(),
                    firstName = binding.tietFirstName.text.toString(),
                    userName = binding.tietUserName.text.toString(),
                    birthday = binding.tietBirthday.text.toString(),
                    homeTown = binding.tietHomeTown.text.toString(),
                    email = email
                )

                storeViewModel.addNewUser(profile)
            }
        }
            ?.addOnFailureListener { exception ->
                Log.w("FirestoreStorage", "Error loading Image", exception)
            }
    }



    private fun createProfileImage(): Uri {

        val randomImage = RandomProfileImage().imageList.random()

        return Uri.parse("android.resource://com.example.abschlussaufgabe/drawable/${randomImage}")
    }

}