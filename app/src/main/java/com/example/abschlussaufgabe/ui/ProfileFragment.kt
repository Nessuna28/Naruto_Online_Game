package com.example.abschlussaufgabe.ui

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
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

        authViewModel.currentUser.observe(viewLifecycleOwner) {
            if (it != null) {
                storeViewModel.getUserData(it.uid)
            }
        }

        storeViewModel.currentProfile.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.tvLastName.text = it.lastName
                binding.tvFirstName.text = it.firstName
                binding.tvUserName.text = it.userName
                binding.tvBirthday.text = it.birthday
                binding.tvHomeTown.text = it.homeTown
                binding.tvEmail.text = it.email
                binding.ivProfilePhoto.setImageURI(it.profileImage)
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
        // komme ich vom Profil bearbeiten dann navigiert der Zurück-Button zum Home
        binding.ivBack.setOnClickListener {
            if (previousFragmentTag == "fragment_edit_profile_tag" || previousFragmentTag == "fragment_create_profile_tag") {
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