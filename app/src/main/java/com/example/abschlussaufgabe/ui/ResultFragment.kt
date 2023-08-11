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
import com.example.abschlussaufgabe.data.datamodels.modelForFight.fightDataForDatabase.DataPlayer
import com.example.abschlussaufgabe.databinding.FragmentResultBinding


class ResultFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var binding: FragmentResultBinding



    override fun onStart() {
        super.onStart()

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

        viewModel.imageBackground.value?.let { viewModel.hideImages(it) }
        viewModel.materialCard.value?.let { viewModel.showMaterialCard(it) }
        viewModel.imageHome.value?.let { viewModel.showImages(it) }
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

        viewModel.location.observe(viewLifecycleOwner) {
            binding.ivBackgroundResult?.setImageResource(it.image)
        }

        viewModel.result.observe(viewLifecycleOwner) {
            if (it == "Sieg") {
                binding.ivTitleWonOrLost?.setImageResource(R.drawable.winner)
                viewModel.player.observe(viewLifecycleOwner) {
                    binding.tvCharacterName?.text = it.name
                    binding.ivCharacterImage?.setImageResource(it.imagePose)
                    binding.vvCharacterVideo?.setVideoPath(it.video)
                    binding.vvCharacterVideo?.start()
                    context?.let { it1 -> viewModel.setSound(it1, it.sound) }
                }

            } else {
                binding.ivTitleWonOrLost?.setImageResource(R.drawable.loser)
                viewModel.player.observe(viewLifecycleOwner) {
                    binding.tvCharacterName?.text = it.name
                    binding.ivCharacterImage?.setImageResource(it.imageSad)
                    binding.vvCharacterVideo?.setVideoPath(it.video)
                    binding.vvCharacterVideo?.start()
                    context?.let { it1 -> viewModel.setSound(it1, it.sound) }
                }
            }
        }

        viewModel.profile.observe(viewLifecycleOwner) {
            binding.tvUserName?.text = viewModel.profile.value?.userName
        }

        binding.tvRounds?.text = viewModel.rounds.value.toString()
        binding.tvRoundsWon?.text = viewModel.roundsWonPlayer.value.toString()

        viewModel.countVictorysAndDefeats(viewModel.result.value!!)


        // der Datenbank hinzufügen

        val charakter = viewModel.player.value!!
        val charakterEnemy = viewModel.enemy.value!!

        val today = viewModel.getTodayDate()

        val data = DataPlayer(1,
            today,
            viewModel.profile.value!!.userName,
            charakter.name,
            charakter.image,
            charakter.lifePoints,
            viewModel.result.value!!,
            viewModel.userNameEnemy.value!!,
            charakterEnemy.name,
            charakterEnemy.image,
            charakterEnemy.lifePoints,
            viewModel.resultEnemy.value!!,
            viewModel.victory.value!!,
            viewModel.defeat.value!!)
        
        viewModel.updateDatabase(data)


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