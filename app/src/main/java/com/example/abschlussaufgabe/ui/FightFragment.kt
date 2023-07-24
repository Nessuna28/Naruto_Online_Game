package com.example.abschlussaufgabe.ui

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.abschlussaufgabe.R
import com.example.abschlussaufgabe.data.datamodels.modelForFight.CharacterForFight
import com.example.abschlussaufgabe.databinding.FragmentFightBinding


class FightFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var binding: FragmentFightBinding

    private lateinit var player: CharacterForFight
    private lateinit var enemy: CharacterForFight


    override fun onStart() {
        super.onStart()

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

        context?.let { viewModel.setSound(it, R.raw.song_theme) }

        viewModel.imageBackground.value?.let { viewModel.hideImages(it) }
        viewModel.materialCard.value?.let { viewModel.hideMaterialCard(it) }

        binding.ivImage2Player?.visibility = View.INVISIBLE
        binding.ivImage2Enemy?.visibility = View.INVISIBLE
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_fight, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        player = viewModel.player.value!!
        enemy = viewModel.enemy.value!!

        viewModel.location.observe(viewLifecycleOwner) {
            binding.ivBackgroundFight?.setImageResource(it.image)
        }

        binding.tvNamePlayer?.text = viewModel.profile.value?.userName

        viewModel.player.observe(viewLifecycleOwner) {
            binding.ivCharacterImagePlayer?.setImageResource(it.imageFace)
            binding.ivImage1Player?.setImageResource(it.image)
            binding.ivImage2Player?.setImageResource(it.imageAttack)
            binding.ivImageDouble1Player?.setImageResource(it.image)
            binding.ivImageDouble2Player?.setImageResource(it.imageAttack)
            binding.tvCharacterNamePlayer?.text = it.name
            binding.tvLifeValuePlayer?.text = it.lifePoints.toString()
            binding.tvChakraValuePlayer?.text = it.chakraPoints.toString()
            binding.tvDefense1?.text = it.defense.keys.elementAt(0)
            binding.tvDefense2?.text = it.defense.keys.elementAt(1)
            binding.tvTool1?.text = it.tools.keys.elementAt(0)
            binding.tvTool2?.text = it.tools.keys.elementAt(1)
            binding.tvUniqueTraits1?.text = it.uniqueTraits.keys.elementAt(0)
            if (it.uniqueTraits.size >= 2) {
                binding.tvUniqueTraits2?.text = it.uniqueTraits.keys.elementAt(1)
            } else {
                binding.tvUniqueTraits2?.visibility = View.GONE
            }
            binding.tvJutsu1?.text = it.jutsus.keys.elementAt(0)
            binding.tvJutsu2?.text = it.jutsus.keys.elementAt(1)
            binding.tvJutsu3?.text = it.jutsus.keys.elementAt(2)
            binding.tvJutsu4?.text = it.jutsus.keys.elementAt(3)
        }

        viewModel.enemy.observe(viewLifecycleOwner) {
            binding.ivCharacterImageEnemy?.setImageResource(it.imageFace)
            binding.ivImage1Enemy?.setImageResource(it.image)
            binding.ivImage2Enemy?.setImageResource(it.imageAttack)
            binding.ivImageDouble1Enemy?.setImageResource(it.image)
            binding.ivImageDouble2Enemy?.setImageResource(it.imageAttack)
            binding.tvCharacterNameEnemy?.text = it.name
            binding.tvLifeValueEnemy?.text = it.lifePoints.toString()
            binding.tvChakraValueEnemy?.text = it.chakraPoints.toString()
        }

        // Attackenauswahl
        binding.tvDefense1?.setOnClickListener {

            incorporatesTheLogic(player.defense.keys.elementAt(0), player.defense.values.elementAt(0))
        }

        binding.tvDefense2?.setOnClickListener {

            incorporatesTheLogic(player.defense.keys.elementAt(1), player.defense.values.elementAt(1))
        }

        binding.tvTool1?.setOnClickListener {

            incorporatesTheLogic(player.tools.keys.elementAt(0), player.tools.values.elementAt(0))
        }

        binding.tvTool2?.setOnClickListener {

            incorporatesTheLogic(player.tools.keys.elementAt(1), player.tools.values.elementAt(1))
        }

        binding.tvUniqueTraits1?.setOnClickListener {

            incorporatesTheLogic(player.uniqueTraits.keys.elementAt(0), player.uniqueTraits.values.elementAt(0))
        }

        binding.tvUniqueTraits2?.setOnClickListener {

            incorporatesTheLogic(player.uniqueTraits.keys.elementAt(1), player.uniqueTraits.values.elementAt(1))
        }

        binding.tvJutsu1?.setOnClickListener {

            incorporatesTheLogic(player.jutsus.keys.elementAt(0), player.jutsus.values.elementAt(0))
        }

        binding.tvJutsu2?.setOnClickListener {

            incorporatesTheLogic(player.jutsus.keys.elementAt(1), player.jutsus.values.elementAt(1))
        }

        binding.tvJutsu3?.setOnClickListener {

            incorporatesTheLogic(player.jutsus.keys.elementAt(2), player.jutsus.values.elementAt(2))
        }

        binding.tvJutsu4?.setOnClickListener {

            incorporatesTheLogic(player.jutsus.keys.elementAt(3), player.jutsus.values.elementAt(3))
        }

        // Navigation

        binding.ivBack?.setOnClickListener {
            findNavController().navigate(FightFragmentDirections.actionFightFragmentToCharacterSelectionFragment())
        }

        viewModel.player.observe(viewLifecycleOwner) {
            if (it.lifePoints <= 0) {
                findNavController().navigate(FightFragmentDirections.actionFightFragmentToResultFragment())
                println(player.lifePoints)
            }
        }

        viewModel.enemy.observe(viewLifecycleOwner) {
            if (it.lifePoints <= 0) {
                findNavController().navigate(FightFragmentDirections.actionFightFragmentToResultFragment())
                println(enemy.lifePoints)
            }
        }
    }

    // Funktionen um Änderungen am Design während der Ausführung vorzunehmen

    // je nach Wahl der Attacke wird eine bestimmt Funktion aufgerufen (Änderung, Ein- oder Ausblenden eines Bildes)
    fun actionOfSelection() {

        viewModel.attackStringPlayer.observe(viewLifecycleOwner) {

            if (player.uniqueTraits.size >= 2) {
                when(it) {
                    player.defense.keys.elementAt(0) -> changeImageForDuration(2000)
                    player.defense.keys.elementAt(1) -> changeVisibilityForDefense(3000)
                    player.tools.keys.elementAt(0) -> changeVisibilityForDuration(2000)
                    player.tools.keys.elementAt(1) -> changeVisibilityForDuration(2000)
                    player.uniqueTraits.keys.elementAt(0) -> changeVisibilityForDuration(2000)
                    player.uniqueTraits.keys.elementAt(1) -> changeVisibilityForDuration(2000)
                    player.jutsus.keys.elementAt(0) -> changeVisibilityForDuration(2000)
                    player.jutsus.keys.elementAt(1) -> changeVisibilityForDuration(2000)
                    player.jutsus.keys.elementAt(2) -> changeVisibilityForDuration(2000)
                    player.jutsus.keys.elementAt(3) -> changeVisibilityForDuration(2000)
                }
            } else {
                when(it) {
                    player.defense.keys.elementAt(0) -> changeImageForDuration(2000)
                    player.defense.keys.elementAt(1) -> changeVisibilityForDefense(3000)
                    player.tools.keys.elementAt(0) -> changeVisibilityForDuration(2000)
                    player.tools.keys.elementAt(1) -> changeVisibilityForDuration(2000)
                    player.uniqueTraits.keys.elementAt(0) -> changeVisibilityForDuration(2000)
                    player.jutsus.keys.elementAt(0) -> changeVisibilityForDuration(2000)
                    player.jutsus.keys.elementAt(1) -> changeVisibilityForDuration(2000)
                    player.jutsus.keys.elementAt(2) -> changeVisibilityForDuration(2000)
                    player.jutsus.keys.elementAt(3) -> changeVisibilityForDuration(2000)
                }
            }
        }
    }

    // wechselt ein Bild für eine bestimmte Zeit
    private fun changeImageForDuration(duration: Long) {
        binding.ivImage1Player?.setImageResource(R.drawable.baumstamm)

        // Handler verwenden, um nach der angegebenen Dauer das Bild wieder zurückzusetzen
        Handler().postDelayed({ binding.ivImage1Player?.setImageResource(player.image) }, duration)
    }


    // ändert die Visibility der Views

    private fun changeVisibilityForDuration(duration: Long) {
        binding.ivImage1Player?.visibility = View.INVISIBLE
        binding.ivImage2Player?.visibility = View.VISIBLE

        Handler().postDelayed({binding.ivImage1Player?.visibility = View.VISIBLE}, duration)
        Handler().postDelayed({binding.ivImage2Player?.visibility = View.INVISIBLE}, duration)
    }

    private fun changeVisibilityForDefense(duration: Long) {
        binding.ivImageDouble1Player?.visibility = View.VISIBLE
        binding.ivImageDouble2Player?.visibility = View.VISIBLE

        Handler().postDelayed({binding.ivImageDouble1Player?.visibility = View.INVISIBLE}, duration)
        Handler().postDelayed({binding.ivImageDouble2Player?.visibility = View.INVISIBLE}, duration)
    }

    // fügt die einzelnen Funktionen der Logik zusammen, die nach jedem Klick auf eine Attacke ausgeführt werden
    private fun incorporatesTheLogic(attack: String, value: Int) {

        viewModel.setAttackStringPlayer(attack)
        viewModel.setAttackValuePlayer(value)
        viewModel.setAttackEnemy()
        viewModel.subtractPoints()
        actionOfSelection()
    }
}