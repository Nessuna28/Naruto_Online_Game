package com.example.abschlussaufgabe.ui

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.abschlussaufgabe.FightViewModel
import com.example.abschlussaufgabe.FirestoreViewModel
import com.example.abschlussaufgabe.KniffelViewModel
import com.example.abschlussaufgabe.MainViewModel
import com.example.abschlussaufgabe.R
import com.example.abschlussaufgabe.databinding.FragmentSettingsBinding
import com.google.android.material.slider.Slider


class SettingsFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private val fightViewModel: FightViewModel by activityViewModels()
    private val kniffelViewModel: KniffelViewModel by activityViewModels()
    private val storeViewModel: FirestoreViewModel by activityViewModels()

    private lateinit var binding: FragmentSettingsBinding

    private lateinit var sliderVolume: Slider
    private lateinit var sliderFontSize: Slider

    override fun onStart() {
        super.onStart()

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

        viewModel.imageBackground.value?.let { viewModel.hideImages(it) }
        viewModel.materialCard.value?.let { viewModel.showMaterialCard(it) }
        viewModel.tvUserName.value?.let { viewModel.showTextView(it) }
        viewModel.imageTitle.value?.let { viewModel.showImages(it) }
        viewModel.imageHome.value?.let { viewModel.showImages(it) }
        viewModel.imageSettings.value?.let { viewModel.hideImages(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sliderFontSize = binding.sliderFontSize
        sliderVolume = binding.sliderVolume


        sliderVolume.addOnChangeListener { slider, value, fromUser ->

            val mediaPlayerVolume = value / slider.valueTo

            fightViewModel.setValueForVolume(mediaPlayerVolume)
            kniffelViewModel.setValueForVolume(mediaPlayerVolume)
        }

        // TODO: addOnChangeListener für die Schriftgröße

        // Navigation

        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.imageHome.value!!.setOnClickListener {
            findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToHomeFragment())
        }

        viewModel.tvUserName.value!!.setOnClickListener {
            findNavController().navigate(SelectionGameFragmentDirections.actionSelectionGameFragmentToProfileFragment())
        }

        viewModel.cvImageProfile.value!!.setOnClickListener {
            findNavController().navigate(SelectionGameFragmentDirections.actionSelectionGameFragmentToProfileFragment())
        }
    }
}