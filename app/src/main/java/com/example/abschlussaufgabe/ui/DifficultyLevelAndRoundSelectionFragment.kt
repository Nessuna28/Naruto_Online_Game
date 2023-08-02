package com.example.abschlussaufgabe.ui

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.abschlussaufgabe.R
import com.example.abschlussaufgabe.databinding.FragmentDifficultyLevelAndRoundSelectionBinding


class DifficultyLevelAndRoundSelectionFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var binding: FragmentDifficultyLevelAndRoundSelectionBinding

    val fight = listOf(R.string.single, R.string.team)
    val rounds = listOf(R.string.oneRound, R.string.threeRounds)
    val difficultyLevel = listOf(R.string.light, R.string.middle, R.string.difficult)

    var currentFightIndex = 0
    lateinit var selectionFight: String
    var currentRoundsIndex = 0
    lateinit var selctionRounds: String
    var currentDifficultyLevelIndex = 1
    lateinit var selectionDifficultyLevel: String


    override fun onStart() {
        super.onStart()

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        context?.let { viewModel.setSound(it, R.raw.song_beginn) }

        viewModel.materialCard.value?.let { viewModel.showMaterialCard(it) }
        viewModel.imageHome.value?.let { viewModel.showImages(it) }
        viewModel.imageBackground.value?.let { viewModel.hideImages(it) }
        viewModel.imageTitle.value?.let { viewModel.hideImages(it) }

        selectionFight = getString(fight[currentFightIndex])
        selctionRounds = getString(rounds[currentRoundsIndex])
        selectionDifficultyLevel = getString(difficultyLevel[currentDifficultyLevelIndex])

        binding.tvSingleOrTeam?.text = getString(fight[0])
        binding.tvNumberOfRounds?.text = getString(rounds[0])
        binding.tvSelectionDifficultyLevel?.text = getString(difficultyLevel[1])

        binding.btnFurther?.visibility = View.INVISIBLE
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_difficulty_level_and_round_selection, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Buttons

        binding.ivPreviousSingleOrTeam?.setOnClickListener {
            currentFightIndex = (currentFightIndex - 1 + fight.size) % fight.size
            selectionFight = getString(fight[currentFightIndex])
            binding.tvSingleOrTeam?.text = selectionFight
        }

        binding.ivNextSingleOrTeam?.setOnClickListener {
            currentFightIndex = (currentFightIndex + 1) % fight.size
            selectionFight = getString(fight[currentFightIndex])
            binding.tvSingleOrTeam?.text = selectionFight
        }

        binding.ivPreviousRounds?.setOnClickListener {
            currentRoundsIndex = (currentRoundsIndex -1 + rounds.size) % rounds.size
            selctionRounds = getString(rounds[currentRoundsIndex])
            binding.tvNumberOfRounds?.text = selctionRounds
        }

        binding.ivNextRounds?.setOnClickListener {
            currentRoundsIndex = (currentRoundsIndex +1) % rounds.size
            selctionRounds = getString(rounds[currentRoundsIndex])
            binding.tvNumberOfRounds?.text = selctionRounds
        }

        binding.ivPreviousDifficultyLevel?.setOnClickListener {
            currentDifficultyLevelIndex = (currentDifficultyLevelIndex - 1 + difficultyLevel.size) % difficultyLevel.size
            selectionDifficultyLevel = getString(difficultyLevel[currentDifficultyLevelIndex])
            binding.tvSelectionDifficultyLevel?.text = selectionDifficultyLevel
        }

        binding.ivNextDifficultyLevel?.setOnClickListener {
            currentDifficultyLevelIndex = (currentDifficultyLevelIndex + 1) % difficultyLevel.size
            selectionDifficultyLevel = getString(difficultyLevel[currentDifficultyLevelIndex])
            binding.tvSelectionDifficultyLevel?.text = selectionDifficultyLevel
        }


        binding.btnOkPlayer?.setOnClickListener {
            viewModel.selectFight(selectionFight)
            viewModel.selectRounds(selctionRounds)
            viewModel.selectDifficultyLevel(selectionDifficultyLevel)
            Log.e("Auswahl", "${viewModel.selectDifficultyLevel.value}, ${viewModel.selectRounds.value}")
            it.setBackgroundColor(Color.GREEN)
            binding.btnFurther?.visibility = View.VISIBLE
        }

        // Navigation

        binding.btnFurther?.setOnClickListener {
            findNavController().navigate(DifficultyLevelAndRoundSelectionFragmentDirections.actionDifficultyLevelAndRoundSelectionFragmentToCharacterSelectionFragment())
        }

        binding.ivBack?.setOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.imageHome.value?.setOnClickListener {
            findNavController().navigate(CharacterSelectionFragmentDirections.actionCharacterSelectionFragmentToHomeFragment())
        }

        viewModel.materialCard.value!!.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProfileFragment())
        }
    }
}