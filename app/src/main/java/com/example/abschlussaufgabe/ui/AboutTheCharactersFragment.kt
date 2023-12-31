package com.example.abschlussaufgabe.ui

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.abschlussaufgabe.MainViewModel
import com.example.abschlussaufgabe.R
import com.example.abschlussaufgabe.adapter.CharacterAdapter
import com.example.abschlussaufgabe.databinding.FragmentAboutTheCharactersBinding


class AboutTheCharactersFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var binding: FragmentAboutTheCharactersBinding


    override fun onStart() {
        super.onStart()

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

        viewModel.imageTitle.value?.let { viewModel.showImages(it) }
        viewModel.imageHome.value?.let { viewModel.showImages(it) }
        viewModel.tvUserName.value?.let { viewModel.showTextView(it) }
        viewModel.materialCard.value?.let { viewModel.showMaterialCard(it) }
        viewModel.cvImageProfile.value?.let { viewModel.showMaterialCard(it) }
        viewModel.imageSettings.value?.let { viewModel.showImages(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_about_the_characters, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.characters.observe(viewLifecycleOwner) {
            binding.rvAboutTheCharacters.adapter = CharacterAdapter(it)
        }

        binding.tiSearch.addTextChangedListener {
            viewModel.searchCharacter(it.toString())
        }

        binding.ivClose?.setOnClickListener {
            binding.tiSearch.setText("")
        }

        // Navigation

        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.imageHome.value!!.setOnClickListener {
            findNavController().navigate(AboutTheCharactersFragmentDirections.actionAboutTheCharactersFragmentToHomeFragment())
        }

        viewModel.cvImageProfile.value!!.setOnClickListener {
            findNavController().navigate(AboutTheCharactersFragmentDirections.actionAboutTheCharactersFragmentToProfileFragment4())
        }

        viewModel.tvUserName.value!!.setOnClickListener {
            findNavController().navigate(AboutTheCharactersFragmentDirections.actionAboutTheCharactersFragmentToProfileFragment4())
        }

        viewModel.imageSettings.value!!.setOnClickListener {
            findNavController().navigate(AboutTheCharactersFragmentDirections.actionAboutTheCharactersFragmentToSettingsFragment())
        }
    }
}