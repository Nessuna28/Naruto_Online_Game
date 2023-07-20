package com.example.abschlussaufgabe.ui

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
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

    private val darkgrey = R.color.darkgrey
    private val grey = R.color.grey
    private val green = R.color.green
    private val primary = R.color.primary


    @SuppressLint("ResourceAsColor")
    override fun onStart() {
        super.onStart()

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

        context?.let { viewModel.setSound(it, R.raw.song_beginn) }

        viewModel.materialCard.value?.let { viewModel.showMaterialCard(it) }
        viewModel.imageHome.value?.let { viewModel.showImages(it) }
        viewModel.imageBackground.value?.let { viewModel.hideImages(it) }
        viewModel.imageTitle.value?.let { viewModel.hideImages(it) }

        val firstCharacter = viewModel.characterForFight.value!!.first()
        binding.tvCharacterNamePlayer?.text = firstCharacter.name
        binding.tvCharacterNameEnemy?.text = firstCharacter.name
        binding.ivSelectionPlayer?.setImageResource(firstCharacter.image)
        binding.ivSelectionEnemy?.setImageResource(firstCharacter.image)

        resetToDefault()
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

    @SuppressLint("ResourceAsColor")
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
            if (viewModel.player.isInitialized) {
                binding.ivSelectionPlayer?.setImageResource(viewModel.imagePoseForPlayer.value!!)
                it.background = green.toDrawable()
                viewModel.confirmSelectionPlayer(true)
                binding.rvCharactersPlayer?.isEnabled = false
                binding.rvCharactersEnemy?.isEnabled = true
                check()
            }
        }

        binding.btnRandomPlayer?.setOnClickListener {
            viewModel.randomCharacterForPlayer()
            viewModel.confirmSelectionPlayer(true)
        }


        binding.btnOkEnemy?.setOnClickListener {
            if (viewModel.enemy.isInitialized) {
                binding.ivSelectionEnemy?.setImageResource(viewModel.imagePoseForEnemy.value!!)
                it.setBackgroundColor(green)
                viewModel.confirmSelectionEnemy(true)
                binding.rvCharactersEnemy?.isEnabled = false
                check()
            }
        }

        binding.btnRandomEnemy?.setOnClickListener {
            viewModel.randomCharacterForEnemy()
            viewModel.confirmSelectionEnemy(true)
        }


        binding.btnFurther?.setOnClickListener {
            if (viewModel.selectionConfirmedPlayer.value == true && viewModel.selectionConfirmedEnemy.value == true) {
                findNavController().navigate(CharacterSelectionFragmentDirections.actionCharacterSelectionFragmentToLocationSelectionFragment())
            } // TODO: Toast schreiben
        }

        binding.btnReset?.setOnClickListener {
            viewModel.resetSelectionData()
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

        if (viewModel.selectionConfirmedPlayer.value == true && viewModel.selectionConfirmedEnemy.value == true) {
            binding.btnFurther?.background = primary.toDrawable()
            binding.btnFurther?.isEnabled = true
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
        binding.btnOkPlayer?.background = grey.toDrawable() // TODO: funktioniert nicht
        binding.btnOkEnemy?.background = grey.toDrawable()
        binding.btnFurther?.background = darkgrey.toDrawable()
        binding.btnFurther?.isEnabled = false
    }
}