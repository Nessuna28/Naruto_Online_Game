package com.example.abschlussaufgabe.ui

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.abschlussaufgabe.AuthViewModel
import com.example.abschlussaufgabe.FirestoreViewModel
import com.example.abschlussaufgabe.MainViewModel
import com.example.abschlussaufgabe.R
import com.example.abschlussaufgabe.ScissorsRockPaperViewModel
import com.example.abschlussaufgabe.databinding.FragmentScissorsRockPaperBinding


class ScissorsRockPaperFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private val gameViewModel: ScissorsRockPaperViewModel by activityViewModels()
    private val storeViewModel: FirestoreViewModel by activityViewModels()
    private val authViewModel: AuthViewModel by activityViewModels()

    private lateinit var binding: FragmentScissorsRockPaperBinding

    override fun onStart() {
        super.onStart()

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        viewModel.imageBackground.value?.let { viewModel.hideImages(it) }
        viewModel.imageHome.value?.let { viewModel.showImages(it) }
        viewModel.imageTitle.value?.let { viewModel.showImages(it) }
        viewModel.materialCard.value?.let { viewModel.showMaterialCard(it) }
        viewModel.imageSettings.value?.let { viewModel.showImages(it) }
        viewModel.tvUserName.value?.let { viewModel.showTextView(it) }
        viewModel.cvImageProfile.value?.let { viewModel.showMaterialCard(it) }

        binding.ivCurtain.setImageResource(R.drawable.curtain_close)
        gameViewModel.resetPoints()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_scissors_rock_paper,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (authViewModel.currentUser.value != null) {
            binding.tvPlayerName.text = storeViewModel.currentProfile.value!!.userName
        } else {
            binding.tvPlayerName.setText(R.string.guest)
        }

        binding.tvEnemyName.setText(R.string.computer)

        binding.btnPaper.setOnClickListener {
            gameViewModel.setPlayerHand("paper")
            round("paper")
        }

        binding.btnRock.setOnClickListener {
            gameViewModel.setPlayerHand("rock")
            round("rock")
        }

        binding.btnScissors.setOnClickListener {
            gameViewModel.setPlayerHand("scissors")
            round("scissors")
        }

        gameViewModel.playerHand.observe(viewLifecycleOwner) {
            if (it == "scissors") {
                binding.ivHandPlayer.setImageResource(R.drawable.scissors_player)
            } else if (it == "rock") {
                binding.ivHandPlayer.setImageResource(R.drawable.rock_player)
            } else if (it == "paper") {
                binding.ivHandPlayer.setImageResource(R.drawable.paper_player)
            }
        }

        gameViewModel.randomHand.observe(viewLifecycleOwner) {
            if (it == "scissors") {
                binding.ivHandEnemy.setImageResource(R.drawable.scissors_enemy)
            } else if (it == "rock") {
                binding.ivHandEnemy.setImageResource(R.drawable.rock_enemy)
            } else if (it == "paper") {
                binding.ivHandEnemy.setImageResource(R.drawable.paper_enemy)
            }
        }

        gameViewModel.playerPoints.observe(viewLifecycleOwner) {
            binding.tvPlayerPoints.text = it.toString()
        }

        gameViewModel.enemyPoints.observe(viewLifecycleOwner) {
            binding.tvEnemyPoints.text = it.toString()
        }


        // Navigation

        viewModel.imageHome.value!!.setOnClickListener {
            findNavController().navigate(ScissorsRockPaperFragmentDirections.actionScissorsRockPaperFragmentToHomeFragment())
        }

        viewModel.imageSettings.value!!.setOnClickListener {
            findNavController().navigate(ScissorsRockPaperFragmentDirections.actionScissorsRockPaperFragmentToSettingsFragment())
        }

        viewModel.cvImageProfile.value!!.setOnClickListener {
            findNavController().navigate(ScissorsRockPaperFragmentDirections.actionScissorsRockPaperFragmentToProfileFragment())
        }

        viewModel.tvUserName.value!!.setOnClickListener {
            findNavController().navigate(ScissorsRockPaperFragmentDirections.actionScissorsRockPaperFragmentToProfileFragment())
        }

        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }


    private fun round(hand: String) {

        gameViewModel.setPlayerHand(hand)
        gameViewModel.randomHandEnemy()

        Handler().postDelayed({
            binding.ivCurtain.setImageResource(R.drawable.curtain_open)
        }, 2000)

        Handler().postDelayed({
            binding.ivCurtain.setImageResource(R.drawable.curtain_close)
            gameViewModel.checkHands(gameViewModel.playerHand.value!!, gameViewModel.randomHand.value!!)
            gameViewModel.calculatePoints()
        }, 5000)
    }
}