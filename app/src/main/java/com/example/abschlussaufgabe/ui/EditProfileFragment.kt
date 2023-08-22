package com.example.abschlussaufgabe.ui

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
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
import com.example.abschlussaufgabe.MainViewModel
import com.example.abschlussaufgabe.R
import com.example.abschlussaufgabe.data.datamodels.Profile
import com.example.abschlussaufgabe.databinding.FragmentEditProfileBinding


class EditProfileFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var binding: FragmentEditProfileBinding

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_profile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.profile.observe(viewLifecycleOwner) {
            binding.tietLastName.setText(it.lastName)
            binding.tietFirstName.setText(it.firstName)
            binding.tietUserName.setText(it.userName)
            binding.tietBirthday.setText(it.birthday)
            binding.tietHomeTown.setText(it.homeTown)
            binding.ivProfilePhoto.setImageURI(it.profileImage)
        }

        binding.ivProfilePhoto.setOnClickListener {
                openImagePicker()
        }

        binding.btnSave.setOnClickListener {
            changeProfile()
            findNavController().navigate(EditProfileFragmentDirections.actionEditProfileFragmentToProfileFragment())
        }

        binding.btnCancel.setOnClickListener {
            findNavController().navigateUp()
        }
    }


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

    private fun changeProfile() {

        var currentImage = Uri.EMPTY

        currentImage = if (profileImage.toString().isEmpty()) {
            viewModel.profile.value!!.profileImage
        } else {
            profileImage
        }

        val profile = Profile(
            id = viewModel.profile.value!!.id,
            profileImage = currentImage,
            lastName = binding.tietLastName.text.toString(),
            firstName = binding.tietFirstName.text.toString(),
            userName = binding.tietUserName.text.toString(),
            birthday = binding.tietBirthday.text.toString(),
            homeTown = binding.tietHomeTown.text.toString(),
            email = viewModel.profile.value!!.email
        )

        viewModel.changeDataProfile(profile)
    }
}


