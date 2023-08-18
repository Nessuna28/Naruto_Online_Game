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
import com.example.abschlussaufgabe.FightViewModel
import com.example.abschlussaufgabe.MainViewModel
import com.example.abschlussaufgabe.R
import com.example.abschlussaufgabe.databinding.FragmentCombatSettingsBinding


class CombatSettingsFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private val fightViewModel: FightViewModel by activityViewModels()

    private lateinit var binding: FragmentCombatSettingsBinding

    val fight = listOf(R.string.single, R.string.team)
    val rounds = listOf(R.string.oneRound, R.string.threeRounds)
    val timer = listOf(R.string.noLimit, R.string.second30, R.string.second60, R.string.second90)
    val difficultyLevel = listOf(R.string.light, R.string.middle, R.string.difficult)

    var currentFightIndex = 0
    lateinit var selectionFight: String
    var currentRoundsIndex = 0
    lateinit var selectionRounds: String
    var currentTimerIndex =2
    lateinit var selectionTimer: String
    var currentDifficultyLevelIndex = 1
    lateinit var selectionDifficultyLevel: String


    override fun onStart() {
        super.onStart()

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        context?.let { viewModel.playSound(it, R.raw.song_beginn) }

        viewModel.materialCard.value?.let { viewModel.showMaterialCard(it) }
        viewModel.imageHome.value?.let { viewModel.showImages(it) }
        viewModel.tvUserName.value?.let { viewModel.showTextView(it) }
        viewModel.imageBackground.value?.let { viewModel.hideImages(it) }
        viewModel.imageTitle.value?.let { viewModel.hideImages(it) }
        viewModel.imageSettings.value?.let { viewModel.showImages(it) }

        selectionFight = getString(fight[currentFightIndex])
        selectionRounds = getString(rounds[currentRoundsIndex])
        selectionTimer = getString(timer[currentTimerIndex])
        selectionDifficultyLevel = getString(difficultyLevel[currentDifficultyLevelIndex])

        binding.tvSingleOrTeam?.text = getString(fight[0])
        binding.tvNumberOfRounds?.text = getString(rounds[0])
        binding.tvTimer?.text = getString(timer[2])
        binding.tvSelectionDifficultyLevel?.text = getString(difficultyLevel[1])

        binding.btnFurther?.visibility = View.INVISIBLE
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_combat_settings, container, false)
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
            selectionRounds = getString(rounds[currentRoundsIndex])
            binding.tvNumberOfRounds?.text = selectionRounds
        }

        binding.ivNextRounds?.setOnClickListener {
            currentRoundsIndex = (currentRoundsIndex +1) % rounds.size
            selectionRounds = getString(rounds[currentRoundsIndex])
            binding.tvNumberOfRounds?.text = selectionRounds
        }

        binding.ivPreviousTimer?.setOnClickListener {
            currentTimerIndex = (currentTimerIndex -1 + timer.size) % timer.size
            selectionTimer = getString(timer[currentTimerIndex])
            binding.tvTimer?.text = selectionTimer
        }

        binding.ivNextTimer?.setOnClickListener {
            currentTimerIndex = (currentTimerIndex +1) % timer.size
            selectionTimer = getString(timer[currentTimerIndex])
            binding.tvTimer?.text = selectionTimer
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
            fightViewModel.selectFight(selectionFight)
            fightViewModel.selectRounds(selectionRounds)
            fightViewModel.selectTimer(selectionTimer)
            fightViewModel.selectDifficultyLevel(selectionDifficultyLevel)
            it.setBackgroundColor(Color.GREEN)
            binding.btnFurther?.visibility = View.VISIBLE
        }

        // Navigation

        binding.btnFurther?.setOnClickListener {
            findNavController().navigate(CombatSettingsFragmentDirections.actionCombatSettingsFragmentToCharacterSelectionFragment(viewModel.currentUser.value!!.email.toString()))
        }

        binding.ivBack?.setOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.imageHome.value?.setOnClickListener {
            findNavController().navigate(CombatSettingsFragmentDirections.actionCombatSettingsFragmentToHomeFragment())
        }

        viewModel.imageProfile.value!!.setOnClickListener {
            findNavController().navigate(CombatSettingsFragmentDirections.actionCombatSettingsFragmentToProfileFragment(viewModel.currentUser.value!!.email.toString()))
        }

        viewModel.tvUserName.value!!.setOnClickListener {
            findNavController().navigate(CombatSettingsFragmentDirections.actionCombatSettingsFragmentToProfileFragment(viewModel.currentUser.value!!.email.toString()))
        }
    }
}