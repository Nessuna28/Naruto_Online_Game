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
import com.example.abschlussaufgabe.R
import com.example.abschlussaufgabe.databinding.FragmentResultBinding


class ResultFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var binding: FragmentResultBinding


    override fun onStart() {
        super.onStart()

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

        viewModel.imageBackground.value?.let { viewModel.hideImages(it) }
        viewModel.materialCard.value?.let { viewModel.hideMaterialCard(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_result, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val character = viewModel.player.value!!

        binding.ivBackgroundResult?.setImageResource(viewModel.location.value!!.image)
        binding.ivCharacterWhoWon?.setImageResource(character.imagePose)
        binding.tvCharacterName?.text = character.name

        binding.vvCharacterWhoWon?.setVideoPath(character.video.toString())
        binding.vvCharacterWhoWon?.start()
        context?.let { viewModel.setSound(it, character.sound) }

        if (viewModel.result.value == "Sieg") {
            binding.ivTitleWonOrLost?.setImageResource(R.drawable.winner)
        } else {
            binding.ivTitleWonOrLost?.setImageResource(R.drawable.loser)
        }

        binding.tvUserName?.text = viewModel.profile.value?.userName
        binding.tvRoundsWon?.text = viewModel.roundsWon.value.toString()


        // Navigation

        binding.btnAgain?.setOnClickListener {
            findNavController().navigate(ResultFragmentDirections.actionResultFragmentToFightFragment())
        }

        binding.btnHome?.setOnClickListener {
            findNavController().navigate(ResultFragmentDirections.actionResultFragmentToHomeFragment())
        }

        binding.btnSelection?.setOnClickListener {
            findNavController().navigate(ResultFragmentDirections.actionResultFragmentToCharacterSelectionFragment())
        }
    }
}