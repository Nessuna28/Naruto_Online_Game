package com.example.abschlussaufgabe.ui

import android.content.pm.ActivityInfo
import android.os.Bundle
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
import com.example.abschlussaufgabe.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var binding: FragmentProfileBinding

    private var previousFragmentTag: String? = null

    override fun onStart() {
        super.onStart()

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

        viewModel.imageBackground.value?.let { viewModel.hideImages(it) }
        viewModel.materialCard.value?.let { viewModel.hideMaterialCard(it) }
        viewModel.userName.value?.let { viewModel.hideTextView(it) }
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.profile.observe(viewLifecycleOwner) {
            binding.ivProfilePhoto.setImageResource(it.profileImage)
            binding.tvLastName.text = it.lastName
            binding.tvFirstName.text = it.firstName
            binding.tvUserName.text = it.userName
            binding.tvBirthday.text = it.birthday
            binding.tvPhoneNumber.text = it.phone
            binding.tvEmail.text = it.email
        }

        findNavController().addOnDestinationChangedListener { _, destination, _ ->
            val currentFragmentTag = destination.label.toString()
            previousFragmentTag = currentFragmentTag
        }

        // der Zurück-Button navigiert nur zurück wenn ich nicht vom Profil bearbeiten komme
        // komme ich vom Profil bearbeiten dann navigiert der Zurück-Button zum Home
        binding.ivBack.setOnClickListener {
            if (previousFragmentTag == "fragment_edit_profile_tag") {
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToHomeFragment())
            } else {
                findNavController().navigateUp()
            }
        }

        binding.ivEdit.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment())
        }
    }
}