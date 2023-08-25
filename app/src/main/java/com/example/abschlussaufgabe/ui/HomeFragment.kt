package com.example.abschlussaufgabe.ui

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.abschlussaufgabe.AuthViewModel
import com.example.abschlussaufgabe.FirestoreViewModel
import com.example.abschlussaufgabe.KniffelViewModel
import com.example.abschlussaufgabe.MainViewModel
import com.example.abschlussaufgabe.R
import com.example.abschlussaufgabe.databinding.FragmentHomeBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: MainViewModel by activityViewModels()
    private val storeViewModel: FirestoreViewModel by activityViewModels()


    override fun onStart() {
        super.onStart()


        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

        viewModel.materialCard.value?.let { viewModel.showMaterialCard(it) }
        viewModel.imageTitle.value?.let { viewModel.hideImages(it) }
        viewModel.imageHome.value?.let { viewModel.hideImages(it) }
        viewModel.imageBackground.value?.let { viewModel.showImages(it) }
        viewModel.tvUserName.value?.let { viewModel.showTextView(it) }
        viewModel.imageSettings.value?.let { viewModel.showImages(it) }
        viewModel.imageProfile.value?.let { viewModel.showMaterialCard(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvCharacterTitle.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAboutTheCharactersFragment())
        }

        binding.tvStatisticTitle.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToStatisticsFragment())
        }

        binding.tvGamesTitle?.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSelectionGameFragment())
        }

        viewModel.imageProfile.value!!.setOnClickListener {
            checkProfile()
        }

        viewModel.tvUserName.value!!.setOnClickListener {
            checkProfile()
        }

        viewModel.imageSettings.value!!.setOnClickListener {
            // TODO: noch schreiben
        }
    }

    private fun checkProfile() {

        if (storeViewModel.currentProfile.value != null) {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProfileFragment())
        } else {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCreateProfileFragment())
        }
    }
}
