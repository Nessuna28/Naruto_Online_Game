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
import com.example.abschlussaufgabe.adapter.TraitsEnemyAdapter
import com.example.abschlussaufgabe.adapter.TraitsPlayerAdapter
import com.example.abschlussaufgabe.databinding.FragmentCharacterSelectionBinding


class CharacterSelectionFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var binding: FragmentCharacterSelectionBinding


    override fun onStart() {
        super.onStart()

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

        viewModel.imageBackground.value?.let { viewModel.hideImages(it) }

        val firstCharacter = viewModel.characterForFight.value!!.first()
        binding.tvCharacterNamePlayer?.text = firstCharacter.name
        binding.tvCharacterNameEnemy?.text = firstCharacter.name
        binding.ivSelectionPlayer?.setImageResource(firstCharacter.image)
        binding.ivSelectionEnemy?.setImageResource(firstCharacter.image)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        viewModel.imageForPlayer.observe(viewLifecycleOwner) {
            binding.ivSelectionPlayer?.visibility = View.VISIBLE
            binding.ivSelectionPlayer?.setImageResource(it)
        }

        viewModel.characterNameForPlayer.observe(viewLifecycleOwner) {
            binding.tvCharacterNamePlayer?.visibility = View.VISIBLE
            binding.tvCharacterNamePlayer?.text = viewModel.characterNameForPlayer.value
        }

        viewModel.jutsuListForPlayer.observe(viewLifecycleOwner) {
            binding.tvTitleJutsusPlayer?.visibility = View.VISIBLE
            binding.rvJutsusPlayer?.visibility = View.VISIBLE
            binding.rvJutsusPlayer?.adapter = JutsuPlayerAdapter(it)
        }

        viewModel.uniqueTraitsListForPlayer.observe(viewLifecycleOwner) {
            binding.tvTitleTraitsPlayer?.visibility = View.VISIBLE
            binding.rvTraitsPlayer?.visibility = View.VISIBLE
            binding.rvTraitsPlayer?.adapter = TraitsPlayerAdapter(it)
        }

        viewModel.imageForEnemy.observe(viewLifecycleOwner) {
            binding.ivSelectionEnemy?.visibility = View.VISIBLE
            binding.ivSelectionEnemy?.setImageResource(it)
        }

        viewModel.characterNameForEnemy.observe(viewLifecycleOwner) {
            binding.tvCharacterNameEnemy?.visibility = View.VISIBLE
            binding.tvCharacterNameEnemy?.text = it
        }

        viewModel.jutsuListForEnemy.observe(viewLifecycleOwner) {
            binding.tvTitleJutsusEnemy?.visibility = View.VISIBLE
            binding.rvJutsusEnemy?.visibility = View.VISIBLE
            binding.rvJutsusEnemy?.adapter = JutsuEnemyAdapter(it)
        }

        viewModel.uniqueTraitsListForEnemy.observe(viewLifecycleOwner) {
            binding.tvTitleTraitsEnemy?.visibility = View.VISIBLE
            binding.rvTraitsEnemy?.visibility = View.VISIBLE
            binding.rvTraitsEnemy?.adapter = TraitsEnemyAdapter(it)
        }



        // OnClickListerner & Navigation

        binding.btnOkPlayer?.setOnClickListener {
            binding.ivSelectionPlayer?.setImageResource(viewModel.image2ForPlayer.value!!)
            binding.rvCharactersPlayer?.isClickable = false     // TODO: Warum funktioniert nicht
            binding.rvCharactersEnemy?.isClickable = true
        }

        binding.btnRandomPlayer?.setOnClickListener {
            viewModel.randomCharacterForPlayer()
        }

        binding.btnReset?.setOnClickListener {
            viewModel.resetSelectionData()
            binding.tvTitleJutsusPlayer?.visibility = View.INVISIBLE
            binding.tvTitleTraitsPlayer?.visibility = View.INVISIBLE
            binding.tvTitleJutsusEnemy?.visibility = View.INVISIBLE
            binding.tvTitleTraitsEnemy?.visibility = View.INVISIBLE
        }

        binding.btnOkEnemy?.setOnClickListener {
            binding.ivSelectionEnemy?.setImageResource(viewModel.image2ForEnemy.value!!)
            binding.rvCharactersEnemy?.isClickable = false
            binding.btnFurther?.isClickable = true
        }

        binding.btnRandomEnemy?.setOnClickListener {
            viewModel.randomCharacterForEnemy()
        }

        binding.ivBack?.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnFurther?.setOnClickListener {
            findNavController().navigate(CharacterSelectionFragmentDirections.actionCharacterSelectionFragmentToLocationSelectionFragment())
        }

        viewModel.imageHome.value?.setOnClickListener {
            findNavController().navigate(CharacterSelectionFragmentDirections.actionCharacterSelectionFragmentToHomeFragment())
        }
    }
}