package com.example.abschlussaufgabe.ui

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.abschlussaufgabe.R
import com.example.abschlussaufgabe.adapter.DefensePlayerAdapter
import com.example.abschlussaufgabe.adapter.JutsuPlayerAdapter
import com.example.abschlussaufgabe.adapter.ToolPlayerAdapter
import com.example.abschlussaufgabe.adapter.TraitPlayerAdapter
import com.example.abschlussaufgabe.data.datamodels.modelForFight.Attack
import com.example.abschlussaufgabe.data.datamodels.modelForFight.CharacterForFight
import com.example.abschlussaufgabe.databinding.FragmentFightBinding


class FightFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var binding: FragmentFightBinding

    private lateinit var player: CharacterForFight
    private lateinit var enemy: CharacterForFight


    override fun onStart() {
        super.onStart()

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        context?.let { viewModel.setSound(it, R.raw.song_theme) }

        viewModel.imageBackground.value?.let { viewModel.hideImages(it) }
        viewModel.materialCard.value?.let { viewModel.hideMaterialCard(it) }

        binding.ivImage2Player?.visibility = View.INVISIBLE
        binding.ivImage2Enemy?.visibility = View.INVISIBLE

        viewModel.resetToDefaultRounds()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fight, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        player = viewModel.player.value!!
        enemy = viewModel.enemy.value!!

        viewModel.profile.observe(viewLifecycleOwner) {
            binding.tvNamePlayer?.text = it.userName
        }


        viewModel.location.observe(viewLifecycleOwner) {
            binding.ivBackgroundFight?.setImageResource(it.image)
        }


        viewModel.player.observe(viewLifecycleOwner) {
            binding.ivCharacterImagePlayer?.setImageResource(it.imageFace)
            binding.ivImage1Player?.setImageResource(it.image)
            binding.ivImage2Player?.setImageResource(it.imageAttack)
            binding.ivImageDouble1Player?.setImageResource(it.image)
            binding.ivImageDouble2Player?.setImageResource(it.imageAttack)
            binding.tvCharacterNamePlayer?.text = it.name
            binding.tvLifeValuePlayer?.text = it.lifePoints.toString()
            binding.tvChakraValuePlayer?.text = it.chakraPoints.toString()

            binding.rvDefensePlayer?.adapter = DefensePlayerAdapter(it.defense, viewModel)
            binding.rvToolsPlayer?.adapter = ToolPlayerAdapter(it.tools, viewModel)
            binding.rvUniqueTraitsPlayer?.adapter = TraitPlayerAdapter(it.uniqueTraits, viewModel)
            binding.rvJutsuPlayer?.adapter = JutsuPlayerAdapter(it.jutsus, viewModel)

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

        viewModel.attackPlayer.observe(viewLifecycleOwner) {attackPlayer ->
            actionOfSelectionPlayer(attackPlayer)
                viewModel.playRound()
            viewModel.attackEnemy.observe(viewLifecycleOwner) {
                actionOfSelectionEnemy(it)
            }
        }


        viewModel.toastMessage.observe(viewLifecycleOwner, Observer { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        })

        viewModel.roundsWon.observe(viewLifecycleOwner) {
            if (it >= 2 && viewModel.rounds.value!! >= 2) {
                viewModel.setResult(true)
            } else if (viewModel.rounds.value!! >= 2 && it < 2) {
                viewModel.setResult(false)
            } else if (viewModel.rounds.value!! >= 2 && it == 0) {
                viewModel.setResult(false)
            }
        }


        // Navigation

        binding.ivBack?.setOnClickListener {
            findNavController().navigate(FightFragmentDirections.actionFightFragmentToCharacterSelectionFragment())
        }

        viewModel.result.observe(viewLifecycleOwner) {
            if (it != "") {
                findNavController().navigate(FightFragmentDirections.actionFightFragmentToResultFragment())
            }
        }
    }


    // Funktionen um Änderungen am Design während der Ausführung vorzunehmen
    // je nach Wahl der Attacke wird eine bestimmt Funktion aufgerufen (Änderung, Ein- oder Ausblenden eines Bildes)

    fun actionOfSelectionPlayer(it: Attack) {

        val check = true
        val person = player


        when (it) {
            in person.defense -> changeViewsForDefense(check, it, 2000)
            in person.tools -> changeViewsForTools(check, it, 2000)
            in person.uniqueTraits -> changeViewsForAttack(check, it, 2000)
            in person.jutsus -> changeViewsForAttack(check, it, 2000)
        }
    }

    fun actionOfSelectionEnemy(it: Attack) {

        val check = false
        val person = enemy


        when (it) {
            in person.defense -> changeViewsForDefense(check, it, 2000)
            in person.tools -> changeViewsForTools(check, it, 2000)
            in person.uniqueTraits -> changeViewsForAttack(check, it, 2000)
            in person.jutsus -> changeViewsForAttack(check, it, 2000)
        }
    }


    // wechselt ein Bild für eine bestimmte Zeit oder
    // ändert die Visibility der Views

    private fun changeViewsForAttack(check: Boolean, attack: Attack, duration: Long) {

        if (check) {
            if (attack.name == "Heilung") {
                binding.ivImageHealPlayer?.visibility = View.VISIBLE

                Handler().postDelayed(
                    { binding.ivImageHealPlayer?.visibility = View.INVISIBLE },
                    duration
                )
            } else {
                binding.ivImage1Player?.visibility = View.INVISIBLE
                binding.ivImage2Player?.visibility = View.VISIBLE

                Handler().postDelayed(
                    { binding.ivImage1Player?.visibility = View.VISIBLE },
                    duration
                )
                Handler().postDelayed(
                    { binding.ivImage2Player?.visibility = View.INVISIBLE },
                    duration
                )
            }
        } else {
            if (attack.name == "Heilung") {
                binding.ivImageHealEnemy?.visibility = View.VISIBLE

                Handler().postDelayed(
                    { binding.ivImageHealEnemy?.visibility = View.INVISIBLE },
                    duration
                )
            } else {
                binding.ivImage1Enemy?.visibility = View.INVISIBLE
                binding.ivImage2Enemy?.visibility = View.VISIBLE

                Handler().postDelayed(
                    { binding.ivImage1Enemy?.visibility = View.VISIBLE },
                    duration
                )
                Handler().postDelayed(
                    { binding.ivImage2Enemy?.visibility = View.INVISIBLE },
                    duration
                )
            }
        }
    }

    private fun changeViewsForTools(check: Boolean, attack: Attack, duration: Long) {

        if (check) {
            if (attack.name == "Kunai") {
                binding.ivImage2Player?.setImageResource(R.drawable.kunai_player)
                binding.ivImage2Player?.visibility = View.VISIBLE

                Handler().postDelayed(
                    { binding.ivImage2Player?.visibility = View.INVISIBLE }, duration
                )
                Handler().postDelayed(
                    { binding.ivImage2Player?.setImageResource(player.imageAttack) }, duration
                )

            } else if (attack.name == "Shuriken") {
                binding.ivImage2Player?.setImageResource(R.drawable.shuriken)
                binding.ivImage2Player?.visibility = View.VISIBLE
                binding.ivImage2Player?.rotationX = 50F

                Handler().postDelayed(
                    { binding.ivImage2Player?.visibility = View.INVISIBLE }, duration
                )
                Handler().postDelayed(
                    { binding.ivImage2Player?.setImageResource(player.imageAttack) }, duration
                )
                Handler().postDelayed(
                    { binding.ivImage2Player?.rotationX = 0F }, duration
                )
            } else if (attack.name == "Eisensand") {
                binding.ivImage2Player?.setImageResource(R.drawable.gaara_attack2)
                binding.ivImage2Player?.visibility = View.VISIBLE
                binding.ivImage1Player?.visibility = View.INVISIBLE

                Handler().postDelayed(
                    { binding.ivImage2Player?.visibility = View.INVISIBLE }, duration
                )
                Handler().postDelayed(
                    { binding.ivImage1Player?.visibility = View.VISIBLE }, duration
                )
                Handler().postDelayed(
                    { binding.ivImage2Player?.setImageResource(player.imageAttack) }, duration
                )
            } else {
                binding.ivImage1Player?.visibility = View.INVISIBLE
                binding.ivImage2Player?.visibility = View.VISIBLE

                Handler().postDelayed(
                    { binding.ivImage1Player?.visibility = View.VISIBLE },
                    duration
                )
                Handler().postDelayed(
                    { binding.ivImage2Player?.visibility = View.INVISIBLE },
                    duration
                )
            }
        } else {
            if (attack.name == "Kunai") {
                binding.ivImage2Enemy?.setImageResource(R.drawable.kunai_enemy)
                binding.ivImage2Enemy?.visibility = View.VISIBLE

                Handler().postDelayed(
                    { binding.ivImage2Enemy?.visibility = View.INVISIBLE }, duration
                )
                Handler().postDelayed(
                    { binding.ivImage2Enemy?.setImageResource(enemy.imageAttack) }, duration
                )

            } else if (attack.name == "Shuriken") {
                binding.ivImage2Enemy?.setImageResource(R.drawable.shuriken)
                binding.ivImage2Enemy?.visibility = View.VISIBLE
                binding.ivImage2Enemy?.rotationX = 50F

                Handler().postDelayed(
                    { binding.ivImage2Enemy?.visibility = View.INVISIBLE }, duration
                )
                Handler().postDelayed(
                    { binding.ivImage2Enemy?.setImageResource(player.imageAttack) }, duration
                )
                Handler().postDelayed(
                    { binding.ivImage2Enemy?.rotationX = 0F }, duration
                )
            } else if (attack.name == "Eisensand") {
                binding.ivImage2Enemy?.setImageResource(R.drawable.gaara_attack2)
                binding.ivImage2Enemy?.visibility = View.VISIBLE
                binding.ivImage1Enemy?.visibility = View.INVISIBLE

                Handler().postDelayed(
                    { binding.ivImage2Enemy?.visibility = View.INVISIBLE }, duration
                )
                Handler().postDelayed(
                    { binding.ivImage1Enemy?.visibility = View.VISIBLE }, duration
                )
                Handler().postDelayed(
                    { binding.ivImage2Enemy?.setImageResource(enemy.imageAttack) }, duration
                )
            } else {
                binding.ivImage1Enemy?.visibility = View.INVISIBLE
                binding.ivImage2Enemy?.visibility = View.VISIBLE

                Handler().postDelayed(
                    { binding.ivImage1Enemy?.visibility = View.VISIBLE },
                    duration
                )
                Handler().postDelayed(
                    { binding.ivImage2Enemy?.visibility = View.INVISIBLE },
                    duration
                )
            }
        }
    }

    private fun changeViewsForDefense(check: Boolean, attack: Attack, duration: Long) {

        if (check) {
            if (attack.name == "Schattendoppelgänger" || attack.name == "Sanddoppelgänger") {
                binding.ivImageDouble1Player?.visibility = View.VISIBLE
                binding.ivImageDouble2Player?.visibility = View.VISIBLE

                Handler().postDelayed(
                    { binding.ivImageDouble1Player?.visibility = View.INVISIBLE }, duration
                )
                Handler().postDelayed(
                    { binding.ivImageDouble2Player?.visibility = View.INVISIBLE }, duration
                )
            } else if (attack.name == "Sandschild") {
                binding.ivImage1Player?.setImageResource(R.drawable.sandschild)

                Handler().postDelayed(
                    { binding.ivImage1Player?.setImageResource(player.image) }, duration
                )
            } else if (attack.name == "Jutsu des Tausches") {
                binding.ivImage1Player?.setImageResource(R.drawable.baumstamm)

                Handler().postDelayed(
                    { binding.ivImage1Player?.setImageResource(player.image) }, duration
                )
            }
        } else {
            if (attack.name == "Schattendoppelgänger" || attack.name == "Sanddoppelgänger") {
                binding.ivImageDouble1Enemy?.visibility = View.VISIBLE
                binding.ivImageDouble2Enemy?.visibility = View.VISIBLE

                Handler().postDelayed(
                    { binding.ivImageDouble1Enemy?.visibility = View.INVISIBLE }, duration
                )
                Handler().postDelayed(
                    { binding.ivImageDouble2Enemy?.visibility = View.INVISIBLE }, duration
                )
            } else if (attack.name == "Sandschild") {
                binding.ivImage1Enemy?.setImageResource(R.drawable.sandschild)

                Handler().postDelayed(
                    { binding.ivImage1Enemy?.setImageResource(enemy.image) }, duration
                )
            } else if (attack.name == "Jutsu des Tausches") {
                binding.ivImage1Enemy?.setImageResource(R.drawable.baumstamm)

                Handler().postDelayed(
                    { binding.ivImage1Enemy?.setImageResource(enemy.image) }, duration
                )
            }
        }
    }
}
