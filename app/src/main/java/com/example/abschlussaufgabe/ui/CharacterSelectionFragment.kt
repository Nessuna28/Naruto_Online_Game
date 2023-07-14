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
import com.example.abschlussaufgabe.adapter.SelectionCharacterPlayerAdapter
import com.example.abschlussaufgabe.adapter.JutsuEnemyAdapter
import com.example.abschlussaufgabe.adapter.JutsuPlayerAdapter
import com.example.abschlussaufgabe.adapter.SelectionCharacterEnemyAdapter
import com.example.abschlussaufgabe.databinding.FragmentCharacterSelectionBinding


class CharacterSelectionFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var binding: FragmentCharacterSelectionBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

        viewModel.imageBackground.value?.let { viewModel.hideImages(it) }

        //viewModel.updateDatabase(dataPlayer)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_character_selection, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.characterForFight.observe(viewLifecycleOwner) {
            binding.rvCharactersPlayer?.adapter = SelectionCharacterPlayerAdapter(it, viewModel)
            binding.rvCharactersEnemy?.adapter = SelectionCharacterEnemyAdapter(it, viewModel)
        }

        viewModel.jutsuListForPlayer.observe(viewLifecycleOwner) {
            binding.rvSelectionJutsusPlayer?.adapter = JutsuPlayerAdapter(it,viewModel)
        }

        viewModel.jutsuListForEnemy.observe(viewLifecycleOwner) {
            binding.rvSelectionJutsusCom?.adapter = JutsuEnemyAdapter(it)
        }



        if (viewModel.imageForPlayer?.value != null && viewModel.imageForPlayer.value != 0) {
            binding.tvCharacterNamePlayer?.setText(viewModel.characterNameForPlayer.value)
            binding.rvSelectionJutsusPlayer?.visibility = View.VISIBLE
        }

        if (viewModel.imageForEnemy?.value != null && viewModel.imageForEnemy.value != 0) {
            binding.ivSelectionEnemy?.setImageResource(viewModel.image2ForEnemy.value!!)
            binding.tvCharacterNameEnemy?.setText(viewModel.characterNameForEnemy.value)
            binding.rvSelectionJutsusCom?.visibility = View.VISIBLE
        }

        binding.btnReset?.setOnClickListener {

        }

        binding.btnOk?.setOnClickListener {
            binding.rvCharactersEnemy?.isClickable = true
        }


        // Navigation

        binding.ivBack?.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnRandom?.setOnClickListener {
            viewModel.randomCharacterForPlayer()
        }

        binding.btnReset?.setOnClickListener {
            viewModel.resetSelectionData()
        }

        binding.btnFurther?.setOnClickListener {
            findNavController().navigate(CharacterSelectionFragmentDirections.actionCharacterSelectionFragmentToLocationSelectionFragment())
        }

        viewModel.imageHome.value?.setOnClickListener {
            findNavController().navigate(CharacterSelectionFragmentDirections.actionCharacterSelectionFragmentToHomeFragment())
        }
    }
}