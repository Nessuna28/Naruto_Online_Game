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
import com.example.abschlussaufgabe.MainViewModel
import com.example.abschlussaufgabe.R
import com.example.abschlussaufgabe.databinding.FragmentSelectionGameBinding


class SelectionGameFragment : Fragment() {

    private lateinit var binding: FragmentSelectionGameBinding

    private val viewModel: MainViewModel by activityViewModels()


    override fun onStart() {
        super.onStart()

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

        viewModel.imageTitle.value?.let { viewModel.showImages(it) }
        viewModel.materialCard.value?.let { viewModel.showMaterialCard(it) }
        viewModel.tvUserName.value?.let { viewModel.showTextView(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_selection_game, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvFight.setOnClickListener {
            findNavController().navigate(SelectionGameFragmentDirections.actionSelectionGameFragmentToCombatSettingsFragment())
        }

        binding.tvKniffel.setOnClickListener {
            findNavController().navigate(SelectionGameFragmentDirections.actionSelectionGameFragmentToKniffelFragment())
        }

        binding.tvMemory.setOnClickListener {
            //TODO:
        }

        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.imageHome.value!!.setOnClickListener {
            findNavController().navigate(SelectionGameFragmentDirections.actionSelectionGameFragmentToHomeFragment())
        }

        viewModel.imageProfile.value!!.setOnClickListener {
            findNavController().navigate(SelectionGameFragmentDirections.actionSelectionGameFragmentToProfileFragment())
        }

        viewModel.tvUserName.value!!.setOnClickListener {
            findNavController().navigate(SelectionGameFragmentDirections.actionSelectionGameFragmentToProfileFragment())
        }

        viewModel.imageSettings.value!!.setOnClickListener {
            findNavController().navigate(SelectionGameFragmentDirections.actionSelectionGameFragmentToSettingsFragment())
        }
    }
}