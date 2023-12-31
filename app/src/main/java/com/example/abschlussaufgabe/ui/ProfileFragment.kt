package com.example.abschlussaufgabe.ui

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
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
import com.example.abschlussaufgabe.databinding.FragmentProfileBinding
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso


class ProfileFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private val authViewModel: AuthViewModel by activityViewModels()
    private val storeViewModel: FirestoreViewModel by activityViewModels()

    private lateinit var binding: FragmentProfileBinding

    private var previousFragmentTag: String? = null


    override fun onStart() {
        super.onStart()

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

        viewModel.imageBackground.value?.let { viewModel.hideImages(it) }
        viewModel.materialCard.value?.let { viewModel.showMaterialCard(it) }
        viewModel.tvUserName.value?.let { viewModel.hideTextView(it) }
        viewModel.cvImageProfile.value?.let { viewModel.hideMaterialCard(it) }
        viewModel.imageTitle.value?.let { viewModel.showImages(it) }
        viewModel.imageHome.value?.let { viewModel.showImages(it) }
        viewModel.imageSettings.value?.let { viewModel.showImages(it) }

        storeViewModel.resetUpdateDone()
    }

    override fun onResume() {
        super.onResume()

        if (authViewModel.currentUser.value != null) {
            storeViewModel.getUserData(authViewModel.currentUser.value!!.uid)
        }
        storeViewModel.resetUpdateDone()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)

        if (authViewModel.currentUser.value == null) {
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToLogInFragment())
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        storeViewModel.currentProfile.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.tvLastName.text = it.lastName
                binding.tvFirstName.text = it.firstName
                binding.tvUserName.text = it.userName
                binding.tvBirthday.text = it.birthday
                binding.tvHomeTown.text = it.homeTown
                binding.tvEmail.text = it.email
                // Das Bild in einer ImageView anzeigen
                Picasso.get()
                    .load(it.profileImage)
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .placeholder(R.drawable.placeholder_image)
                    .into(binding.ivProfilePhoto)
            } else {
                binding.tvLastName.text = ""
                binding.tvFirstName.text = ""
                binding.tvUserName.text = R.string.guest.toString()
                binding.tvBirthday.text = ""
                binding.tvHomeTown.text = ""
                binding.tvEmail.text = authViewModel.currentUser.value!!.email!!
                binding.ivProfilePhoto.setImageURI(Uri.parse(viewModel.cvImageProfile.value!!.toString()))
            }
        }


        findNavController().addOnDestinationChangedListener { _, destination, _ ->
            val currentFragmentTag = destination.label.toString()
            previousFragmentTag = currentFragmentTag
        }

        // der Zurück-Button navigiert nur zurück wenn ich nicht vom Profil bearbeiten komme
        // komme ich vom Profil bearbeiten dann navigiert der Zurück-Button zum HomeScreen
        binding.ivBack.setOnClickListener {
            if (previousFragmentTag == R.layout.fragment_edit_profile.toString() || previousFragmentTag == R.layout.fragment_create_profile.toString()) {
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToHomeFragment())
            } else {
                findNavController().navigateUp()
            }
        }

        binding.ivEdit.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment())
        }

        binding.ivLogout.setOnClickListener {
            authViewModel.logout()
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToLogInFragment())
        }

        binding.tvLogout.setOnClickListener {
            authViewModel.logout()
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToLogInFragment())
        }

        viewModel.imageHome.value!!.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToHomeFragment())
        }

        binding.ivDelete.setOnClickListener {
            storeViewModel.deleteUserData(authViewModel.currentUser.value!!.uid)
        }

        binding.tvDeleteAccount.setOnClickListener {
            storeViewModel.deleteUserData(authViewModel.currentUser.value!!.uid)
            authViewModel.logout()
            //authViewModel. TODO: löschen der Anmeldedaten
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToHomeFragment())
        }
    }

}