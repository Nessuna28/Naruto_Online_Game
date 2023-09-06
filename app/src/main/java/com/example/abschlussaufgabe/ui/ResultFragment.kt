package com.example.abschlussaufgabe.ui

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.abschlussaufgabe.AuthViewModel
import com.example.abschlussaufgabe.FightViewModel
import com.example.abschlussaufgabe.FirestoreViewModel
import com.example.abschlussaufgabe.MainViewModel
import com.example.abschlussaufgabe.R
import com.example.abschlussaufgabe.data.datamodels.modelForFight.fightDataForDatabase.DataPlayer
import com.example.abschlussaufgabe.databinding.FragmentResultBinding


class ResultFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private val fightViewModel: FightViewModel by activityViewModels()
    private val storeViewModel: FirestoreViewModel by activityViewModels()
    private val authViewModel: AuthViewModel by activityViewModels()

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
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_result, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fightViewModel.location.observe(viewLifecycleOwner) {
            binding.ivBackgroundResult?.setImageResource(it.image)
        }

        fightViewModel.result.observe(viewLifecycleOwner) { result ->
            if (result == "Sieg") {
                binding.ivTitleWonOrLost?.setImageResource(R.drawable.winner)
                fightViewModel.player.observe(viewLifecycleOwner) { player ->
                    binding.tvCharacterName?.text = player.name
                    binding.ivCharacterImage?.setImageResource(player.imagePose)
                    imageAnimationScale()
                    context?.let { it1 -> fightViewModel.playSound(it1, player.sound) }
                }

            } else {
                binding.ivTitleWonOrLost?.setImageResource(R.drawable.loser)
                fightViewModel.player.observe(viewLifecycleOwner) { enemy ->
                    binding.tvCharacterName?.text = enemy.name
                    binding.ivCharacterImage?.setImageResource(enemy.imageSad)
                    imageAnimationScale()
                    context?.let { it1 -> fightViewModel.playSound(it1, enemy.sound) }
                }
            }
        }

        binding.tvUserName?.text = viewModel.tvUserName.value!!.text

        fightViewModel.rounds.observe(viewLifecycleOwner) { rounds ->
            binding.tvRounds?.text = rounds.toString()
        }

        fightViewModel.roundsWonPlayer.observe(viewLifecycleOwner) { roundWon ->
            binding.tvRoundsWon?.text = roundWon.toString()
        }


        // der Datenbank hinzufügen
        authViewModel.currentUser.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                setData(user.uid)
            } else {
                setData(R.string.guest.toString())
            }
        }


        // Navigation

        binding.btnAgain?.setOnClickListener {
            fightViewModel.stopSound()
            fightViewModel.resetPointsForNewGame()
            fightViewModel.resetToDefaultRounds()
            findNavController().navigate(ResultFragmentDirections.actionResultFragmentToFightFragment())
        }

        binding.btnHome?.setOnClickListener {
            fightViewModel.stopSound()
            findNavController().navigate(ResultFragmentDirections.actionResultFragmentToHomeFragment())
        }

        binding.btnSelection?.setOnClickListener {
            fightViewModel.stopSound()
            findNavController().navigate(ResultFragmentDirections.actionResultFragmentToCharacterSelectionFragment())
        }

        viewModel.imageSettings.value!!.setOnClickListener {
            fightViewModel.stopSound()
            findNavController().navigate(ResultFragmentDirections.actionResultFragmentToSettingsFragment())
        }
    }

    // setzt die aktuellen Daten in eine Variable, die dann der Funktion
    // zum einfügen in die Datenbank übergeben wird
    private fun setData(userId: String) {

        val charakter = fightViewModel.player.value!!
        val charakterEnemy = fightViewModel.enemy.value!!

        val today = viewModel.getTodayDate()

        val data = DataPlayer(
            firestoreId = "",
            userId = userId,
            date = today,
            userName = viewModel.tvUserName.value!!.text.toString(),
            characterName = charakter.name,
            characterImage = charakter.imagePlayer,
            result = fightViewModel.result.value!!,
            userNameEnemy = viewModel.userNameEnemy.value!!,
            characterNameEnemy = charakterEnemy.name,
            characterImageEnemy = charakterEnemy.imageEnemy,
            resultEnemy = fightViewModel.resultEnemy.value!!
        )

        if (userId != R.string.guest.toString()) {
            storeViewModel.addNewPlayerData(data)
        } else {
            viewModel.insertDataGame(data)
        }
    }

    private fun imageAnimationScale() {
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 2f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 2f)
        val animator =
            ObjectAnimator.ofPropertyValuesHolder(binding.ivCharacterImage, scaleX, scaleY)
        animator.duration = 1500
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.interpolator = BounceInterpolator()
        animator.start()
    }
}