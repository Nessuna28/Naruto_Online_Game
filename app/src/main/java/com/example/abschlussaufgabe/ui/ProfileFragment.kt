package com.example.abschlussaufgabe.ui

import android.content.pm.ActivityInfo
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
import com.example.abschlussaufgabe.MainViewModel
import com.example.abschlussaufgabe.R
import com.example.abschlussaufgabe.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private val authViewModel: AuthViewModel by activityViewModels()

    private lateinit var binding: FragmentProfileBinding

    private var previousFragmentTag: String? = null

    private var email = ""

    override fun onStart() {
        super.onStart()

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

        viewModel.imageBackground.value?.let { viewModel.hideImages(it) }
        viewModel.materialCard.value?.let { viewModel.showMaterialCard(it) }
        viewModel.tvUserName.value?.let { viewModel.hideTextView(it) }
        viewModel.imageProfile.value?.let { viewModel.hideMaterialCard(it) }
        viewModel.imageTitle.value?.let { viewModel.showImages(it) }
        viewModel.imageHome.value?.let { viewModel.showImages(it) }
        viewModel.imageSettings.value?.let { viewModel.showImages(it) }

        viewModel.loadDataProfile()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            email = it.getString("email").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = viewModel.profile.value?.find { it.email == email }

        if (user != null) {
            binding.ivProfilePhoto.setImageURI(user?.profileImage)
            binding.tvLastName.text = user?.lastName
            binding.tvFirstName.text = user?.firstName
            binding.tvUserName.text = user!!.userName
            binding.tvBirthday.text = user?.birthday
            binding.tvPhoneNumber.text = user?.phone
            binding.tvEmail.text = user!!.email
        } else {
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToCreateProfileFragment())
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
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment(viewModel.currentUser.value!!.email.toString()))
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
            viewModel.deleteDataProfile()
        }

        binding.tvDeleteProfile.setOnClickListener {
            viewModel.deleteDataProfile()
        }
    }
}