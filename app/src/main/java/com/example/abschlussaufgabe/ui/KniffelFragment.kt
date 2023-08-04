package com.example.abschlussaufgabe.ui

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.abschlussaufgabe.R
import com.example.abschlussaufgabe.databinding.FragmentFightBinding
import com.example.abschlussaufgabe.databinding.FragmentKniffelBinding


class KniffelFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private val kniffelViewModel: KniffelViewModel by activityViewModels()

    private lateinit var binding: FragmentKniffelBinding

    override fun onStart() {
        super.onStart()

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

        viewModel.imageBackground.value?.let { viewModel.hideImages(it) }
        viewModel.imageTitle.value?.let { viewModel.showImages(it) }
        viewModel.materialCard.value?.let { viewModel.showMaterialCard(it) }
        viewModel.userName.value?.let { viewModel.hideTextView(it) }

        context?.let { viewModel.setSound(it, R.raw.song_theme) }

        kniffelViewModel.setAttempts(3)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_kniffel, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        kniffelViewModel.points.observe(viewLifecycleOwner) {
            binding.tvPoints.text = it.toString()
        }

        kniffelViewModel.attempts.observe(viewLifecycleOwner) {
            binding.tvAttempts.text = it.toString()
        }

        kniffelViewModel.selectTeam(kniffelViewModel.teamGaara)

        // Button

        binding.btnRollTheDice.setOnClickListener {
            if (kniffelViewModel.attempts.value != 0) {
                kniffelViewModel.calculateAttempts()
                kniffelViewModel.rollTheDice()
            } else {
                binding.btnRollTheDice.setBackgroundColor(Color.GRAY)
                binding.btnRollTheDice.isEnabled = false
            }
        }

        binding.btnOk.setOnClickListener {
            binding.btnRollTheDice.isEnabled = true
            kniffelViewModel.setAttempts(3)
        }

        // Navigation

        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}