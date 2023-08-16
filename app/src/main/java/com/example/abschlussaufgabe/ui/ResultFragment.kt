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
    private val fightViewModel: FightViewModel by activityViewModels()

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
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_result, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fightViewModel.location.observe(viewLifecycleOwner) {
            binding.ivBackgroundResult?.setImageResource(it.image)
        }

        fightViewModel.result.observe(viewLifecycleOwner) {
            if (it == "Sieg") {
                binding.ivTitleWonOrLost?.setImageResource(R.drawable.winner)
                fightViewModel.player.observe(viewLifecycleOwner) {
                    binding.tvCharacterName?.text = it.name
                    binding.ivCharacterImage?.setImageResource(it.imagePose)
                    binding.vvCharacterVideo?.setVideoPath(it.video.toString())
                    binding.vvCharacterVideo?.start()
                    context?.let { it1 -> viewModel.playSound(it1, it.sound) }
                }

            } else {
                binding.ivTitleWonOrLost?.setImageResource(R.drawable.loser)
                fightViewModel.player.observe(viewLifecycleOwner) {
                    binding.tvCharacterName?.text = it.name
                    binding.ivCharacterImage?.setImageResource(it.imageSad)
                    binding.vvCharacterVideo?.setVideoPath(it.video.toString())
                    binding.vvCharacterVideo?.start()
                    context?.let { it1 -> viewModel.playSound(it1, it.sound) }
                }
            }
        }

        viewModel.profile.observe(viewLifecycleOwner) {
            binding.tvUserName?.text = viewModel.profile.value?.userName
        }

        binding.tvRounds?.text = fightViewModel.rounds.value.toString()
        binding.tvRoundsWon?.text = fightViewModel.roundsWonPlayer.value.toString()



        // der Datenbank hinzufügen

        val charakter = fightViewModel.player.value!!
        val charakterEnemy = fightViewModel.enemy.value!!

        val today = viewModel.getTodayDate()


        val data = DataPlayer(

            date = today,
            userName = viewModel.profile.value!!.userName,
            characterName = charakter.name,
            characterImage = charakter.image,
            lifePoints = charakter.lifePoints,
            result = fightViewModel.result.value!!,
            userNameEnemy = viewModel.userNameEnemy.value!!,
            characterNameEnemy = charakterEnemy.name,
            characterImageEnemy = charakterEnemy.image,
            lifePointsEnemy = charakterEnemy.lifePoints,
            resultEnemy = fightViewModel.resultEnemy.value!!,
           )
        
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