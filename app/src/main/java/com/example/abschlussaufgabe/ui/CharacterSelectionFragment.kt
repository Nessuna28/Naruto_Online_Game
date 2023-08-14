package com.example.abschlussaufgabe.ui

import android.annotation.SuppressLint
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
import com.example.abschlussaufgabe.adapter.SelectionCharacterPlayerAdapter
import com.example.abschlussaufgabe.adapter.JutsuEnemyAdapter
import com.example.abschlussaufgabe.adapter.JutsuPlayerAdapter
import com.example.abschlussaufgabe.adapter.SelectionCharacterEnemyAdapter
import com.example.abschlussaufgabe.adapter.TraitEnemyAdapter
import com.example.abschlussaufgabe.adapter.TraitPlayerAdapter
import com.example.abschlussaufgabe.databinding.FragmentCharacterSelectionBinding


class CharacterSelectionFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private val fightViewModel: FightViewModel by activityViewModels()

    private lateinit var binding: FragmentCharacterSelectionBinding


    @SuppressLint("ResourceAsColor")
    override fun onStart() {
        super.onStart()

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

        viewModel.materialCard.value?.let { viewModel.showMaterialCard(it) }
        viewModel.imageHome.value?.let { viewModel.showImages(it) }
        viewModel.userName.value?.let { viewModel.showTextView(it) }
        viewModel.imageBackground.value?.let { viewModel.hideImages(it) }
        viewModel.imageTitle.value?.let { viewModel.hideImages(it) }

        val firstCharacter = fightViewModel.characterForFight.value!!.first()
        binding.tvCharacterNamePlayer?.text = firstCharacter.name
        binding.tvCharacterNameEnemy?.text = firstCharacter.name
        binding.ivSelectionPlayer?.setImageResource(firstCharacter.image)
        binding.ivSelectionEnemy?.setImageResource(firstCharacter.image)

        resetToDefault()

        viewModel.setUserNameEnemy("Computer")
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_character_selection, container, false)
        return binding.root
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.profile.observe(viewLifecycleOwner) {
            binding.tvPlayerName?.text = it.userName
        }

        viewModel.userNameEnemy.observe(viewLifecycleOwner) {
            binding.tvEnemyName?.text = it
        }

        fightViewModel.characterForFight.observe(viewLifecycleOwner) {
            binding.rvCharactersPlayer?.adapter = SelectionCharacterPlayerAdapter(it, viewModel)
            binding.rvCharactersEnemy?.adapter = SelectionCharacterEnemyAdapter(it, viewModel)
        }

        fightViewModel.player.observe(viewLifecycleOwner) {
            binding.tvCharacterNamePlayer?.visibility = View.VISIBLE
            binding.tvCharacterNamePlayer?.text = it.name
            binding.ivSelectionPlayer?.visibility = View.VISIBLE
            binding.ivSelectionPlayer?.setImageResource(it.image)
            binding.tvTitleJutsusPlayer?.visibility = View.VISIBLE
            binding.rvJutsusPlayer?.visibility = View.VISIBLE
            binding.rvJutsusPlayer?.adapter = JutsuPlayerAdapter(it.jutsus, viewModel)
            binding.tvTitleTraitsPlayer?.visibility = View.VISIBLE
            binding.rvTraitsPlayer?.visibility = View.VISIBLE
            binding.rvTraitsPlayer?.adapter = TraitPlayerAdapter(it.uniqueTraits, viewModel)
        }

        fightViewModel.enemy.observe(viewLifecycleOwner) {
            binding.tvCharacterNameEnemy?.visibility = View.VISIBLE
            binding.tvCharacterNameEnemy?.text = it.name
            binding.ivSelectionEnemy?.visibility = View.VISIBLE
            binding.ivSelectionEnemy?.setImageResource(it.image)
            binding.tvTitleJutsusEnemy?.visibility = View.VISIBLE
            binding.rvJutsusEnemy?.visibility = View.VISIBLE
            binding.rvJutsusEnemy?.adapter = JutsuEnemyAdapter(it.jutsus)
            binding.tvTitleTraitsEnemy?.visibility = View.VISIBLE
            binding.rvTraitsEnemy?.visibility = View.VISIBLE
            binding.rvTraitsEnemy?.adapter = TraitEnemyAdapter(it.uniqueTraits)
        }


        // OnClickListerner & Navigation

        binding.btnOkPlayer?.setOnClickListener {
            if (fightViewModel.player.isInitialized) {
                binding.ivSelectionPlayer?.setImageResource(fightViewModel.player.value!!.imagePose)
                it.setBackgroundColor(Color.GREEN)
                fightViewModel.confirmSelectionPlayer(true)
                binding.rvCharactersPlayer?.isEnabled = false
                binding.rvCharactersEnemy?.isEnabled = true
                check()
            }
        }

        binding.btnRandomPlayer?.setOnClickListener {
            fightViewModel.randomCharacterForPlayer()
            fightViewModel.confirmSelectionPlayer(true)
        }


        binding.btnOkEnemy?.setOnClickListener {
            if (fightViewModel.enemy.isInitialized) {
                binding.ivSelectionEnemy?.setImageResource(fightViewModel.enemy.value!!.imagePose)
                it.setBackgroundColor(Color.GREEN)
                fightViewModel.confirmSelectionEnemy(true)
                binding.rvCharactersEnemy?.isEnabled = false
                check()
            }
        }

        binding.btnRandomEnemy?.setOnClickListener {
            fightViewModel.randomCharacterForEnemy()
            fightViewModel.confirmSelectionEnemy(true)
        }


        binding.btnFurther?.setOnClickListener {
            if (fightViewModel.selectionConfirmedPlayer.value == true && fightViewModel.selectionConfirmedEnemy.value == true) {
                findNavController().navigate(CharacterSelectionFragmentDirections.actionCharacterSelectionFragmentToLocationSelectionFragment())
            } // TODO: Toast schreiben
        }

        binding.btnReset?.setOnClickListener {
            fightViewModel.resetSelectionData()
            resetToDefault()
        }

        binding.ivBack?.setOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.imageHome.value?.setOnClickListener {
            findNavController().navigate(CharacterSelectionFragmentDirections.actionCharacterSelectionFragmentToHomeFragment())
        }
    }

    private fun check() {

        if (fightViewModel.selectionConfirmedPlayer.value == true && fightViewModel.selectionConfirmedEnemy.value == true) {
            binding.btnFurther?.visibility = View.VISIBLE
            binding.btnFurther?.setBackgroundColor(Color.rgb(255, 105, 0))
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun resetToDefault() {

        binding.rvCharactersPlayer?.isEnabled = true
        binding.rvCharactersEnemy?.isEnabled = true
        binding.tvTitleJutsusPlayer?.visibility = View.INVISIBLE
        binding.rvJutsusPlayer?.visibility = View.INVISIBLE
        binding.tvTitleTraitsPlayer?.visibility = View.INVISIBLE
        binding.rvTraitsPlayer?.visibility = View.INVISIBLE
        binding.tvTitleJutsusEnemy?.visibility = View.INVISIBLE
        binding.rvJutsusEnemy?.visibility = View.INVISIBLE
        binding.tvTitleTraitsEnemy?.visibility = View.INVISIBLE
        binding.rvTraitsEnemy?.visibility = View.INVISIBLE
        binding.btnOkPlayer?.setBackgroundColor(Color.GRAY)
        binding.btnOkEnemy?.setBackgroundColor(Color.GRAY)
        binding.btnFurther?.visibility = View.INVISIBLE
    }
}