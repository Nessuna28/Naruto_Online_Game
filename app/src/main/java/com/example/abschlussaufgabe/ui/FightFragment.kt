package com.example.abschlussaufgabe.ui

import android.content.pm.ActivityInfo
import android.graphics.Color
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

    private val handler = Handler()



    override fun onStart() {
        super.onStart()

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        context?.let { viewModel.setSound(it, R.raw.song_theme) }

        viewModel.imageBackground.value?.let { viewModel.hideImages(it) }
        viewModel.materialCard.value?.let { viewModel.hideMaterialCard(it) }

        binding.ivImage2Player?.visibility = View.INVISIBLE
        binding.ivImage2Enemy?.visibility = View.INVISIBLE

        invisibleAttacks()

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

       runViewsRound()

        viewModel.attackPlayer.observe(viewLifecycleOwner) {attackPlayer ->
            actionOfSelectionPlayer(attackPlayer)
            viewModel.playRound()
        }


        viewModel.lifePointsPlayer.observe(viewLifecycleOwner) {
            if (viewModel.lifePointsPlayer.value != null && viewModel.lifePointsEnemy.value != null) {
                if (viewModel.lifePointsPlayer.value!! <= 0 || viewModel.lifePointsEnemy.value!! <= 0) {
                    invisibleAttacks()
                    viewModel.endRound()
                }
            }
        }


        viewModel.attackEnemy.observe(viewLifecycleOwner) {
            if (viewModel.lifePointsPlayer.value != null && viewModel.lifePointsEnemy.value != null) {
                if (viewModel.lifePointsPlayer.value!! > 0 && viewModel.lifePointsEnemy.value!! > 0) {
                    actionOfSelectionEnemy(it)
                    viewModel.endRound()
                }
            }
        }

        viewModel.rounds.observe(viewLifecycleOwner) {
            setColorForRounds(it)
            runViewsRound()
        }


        viewModel.roundsWonPlayer.observe(viewLifecycleOwner) {
            if (it >= 2 && viewModel.rounds.value!! >= 2) {
                viewModel.setResult(true)
            } else if (viewModel.rounds.value!! >= 2 && it < 2) {
                viewModel.setResult(false)
            } else if (viewModel.rounds.value!! >= 2 && it == 0) {
                viewModel.setResult(false)
            }
        }

        viewModel.toastMessage.observe(viewLifecycleOwner, Observer { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        })


        // Navigation

        binding.ivBack?.setOnClickListener {
            findNavController().navigate(FightFragmentDirections.actionFightFragmentToCharacterSelectionFragment())
        }

        viewModel.gameEnd.observe(viewLifecycleOwner) {
            if (it) {
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

                handler.postDelayed(
                    { binding.ivImageHealPlayer?.visibility = View.INVISIBLE },
                    duration
                )
            } else {
                binding.ivImage1Player?.visibility = View.INVISIBLE
                binding.ivImage2Player?.visibility = View.VISIBLE

                handler.postDelayed(
                    { binding.ivImage1Player?.visibility = View.VISIBLE },
                    duration
                )
                handler.postDelayed(
                    { binding.ivImage2Player?.visibility = View.INVISIBLE },
                    duration
                )
            }
        } else {
            if (attack.name == "Heilung") {
                binding.ivImageHealEnemy?.visibility = View.VISIBLE

                handler.postDelayed(
                    { binding.ivImageHealEnemy?.visibility = View.INVISIBLE },
                    duration
                )
            } else {
                binding.ivImage1Enemy?.visibility = View.INVISIBLE
                binding.ivImage2Enemy?.visibility = View.VISIBLE

                handler.postDelayed(
                    { binding.ivImage1Enemy?.visibility = View.VISIBLE },
                    duration
                )
                handler.postDelayed(
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

                handler.postDelayed(
                    { binding.ivImage2Player?.visibility = View.INVISIBLE }, duration
                )
                handler.postDelayed(
                    { binding.ivImage2Player?.setImageResource(player.imageAttack) }, duration
                )

            } else if (attack.name == "Shuriken") {
                binding.ivImage2Player?.setImageResource(R.drawable.shuriken)
                binding.ivImage2Player?.visibility = View.VISIBLE
                binding.ivImage2Player?.rotationX = 50F

                handler.postDelayed(
                    { binding.ivImage2Player?.visibility = View.INVISIBLE }, duration
                )
                handler.postDelayed(
                    { binding.ivImage2Player?.setImageResource(player.imageAttack) }, duration
                )
                handler.postDelayed(
                    { binding.ivImage2Player?.rotationX = 0F }, duration
                )
            } else if (attack.name == "Eisensand") {
                binding.ivImage2Player?.setImageResource(R.drawable.gaara_attack2)
                binding.ivImage2Player?.visibility = View.VISIBLE
                binding.ivImage1Player?.visibility = View.INVISIBLE

                handler.postDelayed(
                    { binding.ivImage2Player?.visibility = View.INVISIBLE }, duration
                )
                handler.postDelayed(
                    { binding.ivImage1Player?.visibility = View.VISIBLE }, duration
                )
                handler.postDelayed(
                    { binding.ivImage2Player?.setImageResource(player.imageAttack) }, duration
                )
            } else {
                binding.ivImage1Player?.visibility = View.INVISIBLE
                binding.ivImage2Player?.visibility = View.VISIBLE

                handler.postDelayed(
                    { binding.ivImage1Player?.visibility = View.VISIBLE },
                    duration
                )
                handler.postDelayed(
                    { binding.ivImage2Player?.visibility = View.INVISIBLE },
                    duration
                )
            }
        } else {
            if (attack.name == "Kunai") {
                binding.ivImage2Enemy?.setImageResource(R.drawable.kunai_enemy)
                binding.ivImage2Enemy?.visibility = View.VISIBLE

                handler.postDelayed(
                    { binding.ivImage2Enemy?.visibility = View.INVISIBLE }, duration
                )
                handler.postDelayed(
                    { binding.ivImage2Enemy?.setImageResource(enemy.imageAttack) }, duration
                )

            } else if (attack.name == "Shuriken") {
                binding.ivImage2Enemy?.setImageResource(R.drawable.shuriken)
                binding.ivImage2Enemy?.visibility = View.VISIBLE
                binding.ivImage2Enemy?.rotationX = 50F

                handler.postDelayed(
                    { binding.ivImage2Enemy?.visibility = View.INVISIBLE }, duration
                )
                handler.postDelayed(
                    { binding.ivImage2Enemy?.setImageResource(player.imageAttack) }, duration
                )
                handler.postDelayed(
                    { binding.ivImage2Enemy?.rotationX = 0F }, duration
                )
            } else if (attack.name == "Eisensand") {
                binding.ivImage2Enemy?.setImageResource(R.drawable.gaara_attack2)
                binding.ivImage2Enemy?.visibility = View.VISIBLE
                binding.ivImage1Enemy?.visibility = View.INVISIBLE

                handler.postDelayed(
                    { binding.ivImage2Enemy?.visibility = View.INVISIBLE }, duration
                )
                handler.postDelayed(
                    { binding.ivImage1Enemy?.visibility = View.VISIBLE }, duration
                )
                handler.postDelayed(
                    { binding.ivImage2Enemy?.setImageResource(enemy.imageAttack) }, duration
                )
            } else {
                binding.ivImage1Enemy?.visibility = View.INVISIBLE
                binding.ivImage2Enemy?.visibility = View.VISIBLE

                handler.postDelayed(
                    { binding.ivImage1Enemy?.visibility = View.VISIBLE },
                    duration
                )
                handler.postDelayed(
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

                handler.postDelayed(
                    { binding.ivImageDouble1Player?.visibility = View.INVISIBLE }, duration
                )
                handler.postDelayed(
                    { binding.ivImageDouble2Player?.visibility = View.INVISIBLE }, duration
                )
            } else if (attack.name == "Sandschild") {
                binding.ivImage1Player?.setImageResource(R.drawable.sandschild)

                handler.postDelayed(
                    { binding.ivImage1Player?.setImageResource(player.image) }, duration
                )
            } else if (attack.name == "Jutsu des Tausches") {
                binding.ivImage1Player?.setImageResource(R.drawable.baumstamm)

                handler.postDelayed(
                    { binding.ivImage1Player?.setImageResource(player.image) }, duration
                )
            }
        } else {
            if (attack.name == "Schattendoppelgänger" || attack.name == "Sanddoppelgänger") {
                binding.ivImageDouble1Enemy?.visibility = View.VISIBLE
                binding.ivImageDouble2Enemy?.visibility = View.VISIBLE

                handler.postDelayed(
                    { binding.ivImageDouble1Enemy?.visibility = View.INVISIBLE }, duration
                )
                handler.postDelayed(
                    { binding.ivImageDouble2Enemy?.visibility = View.INVISIBLE }, duration
                )
            } else if (attack.name == "Sandschild") {
                binding.ivImage1Enemy?.setImageResource(R.drawable.sandschild)

                handler.postDelayed(
                    { binding.ivImage1Enemy?.setImageResource(enemy.image) }, duration
                )
            } else if (attack.name == "Jutsu des Tausches") {
                binding.ivImage1Enemy?.setImageResource(R.drawable.baumstamm)

                handler.postDelayed(
                    { binding.ivImage1Enemy?.setImageResource(enemy.image) }, duration
                )
            }
        }
    }

    private fun visibleAttacks() {

            binding.ivRound?.visibility = View.INVISIBLE

            binding.tvTitleDefense?.visibility = View.VISIBLE
            binding.rvDefensePlayer?.visibility = View.VISIBLE
            binding.tvTitleTraits?.visibility = View.VISIBLE
            binding.rvUniqueTraitsPlayer?.visibility = View.VISIBLE
            binding.tvTitleJutsus?.visibility = View.VISIBLE
            binding.rvJutsuPlayer?.visibility = View.VISIBLE
            binding.tvTitleTools?.visibility = View.VISIBLE
            binding.rvToolsPlayer?.visibility = View.VISIBLE
    }

    private fun invisibleAttacks() {

        binding.tvTitleDefense?.visibility = View.INVISIBLE
        binding.rvDefensePlayer?.visibility = View.INVISIBLE
        binding.tvTitleTraits?.visibility = View.INVISIBLE
        binding.rvUniqueTraitsPlayer?.visibility = View.INVISIBLE
        binding.tvTitleJutsus?.visibility = View.INVISIBLE
        binding.rvJutsuPlayer?.visibility = View.INVISIBLE
        binding.tvTitleTools?.visibility = View.INVISIBLE
        binding.rvToolsPlayer?.visibility = View.INVISIBLE
    }

     fun runViewsRound() {

         if (viewModel.rounds.value!! == 0) {
             binding.ivRound?.setImageResource(R.drawable.round_1)
         } else if (viewModel.rounds.value!! == 1) {
             binding.ivRound?.setImageResource(R.drawable.round_2)
         } else if (viewModel.rounds.value!! == 2) {
             binding.ivRound?.setImageResource(R.drawable.round_3)
         }

         if (viewModel.rounds.value!! > 0) {
             runViewWinnerOrLoser()

             handler.postDelayed({
                binding.ivRound?.visibility = View.VISIBLE


                 binding.ivRound?.visibility = View.INVISIBLE

                 handler.postDelayed({
                    binding.ivFight?.visibility = View.VISIBLE

                     binding.ivFight?.visibility = View.INVISIBLE

                     handler.postDelayed({
                     visibleAttacks()
                    },2000)
                 }, 2000)
             }, 3000)
         } else {
             binding.ivRound?.visibility = View.VISIBLE

             handler.postDelayed({
                 binding.ivRound?.visibility = View.INVISIBLE

                 binding.ivFight?.visibility = View.VISIBLE

                 handler.postDelayed({
                     binding.ivFight?.visibility = View.INVISIBLE

                     visibleAttacks()
                 }, 2000)
             }, 2000)
         }
     }

    fun runViewWinnerOrLoser() {

        if (viewModel.lifePointsPlayer.value!! <= 0) {
            binding.ivResult?.setImageResource(R.drawable.loser)
        } else if (viewModel.lifePointsPlayer.value!! > 0) {
            binding.ivResult?.setImageResource(R.drawable.winner)
        }


        binding.ivResult?.visibility = View.VISIBLE

        handler.postDelayed(
            { binding.ivResult?.visibility = View.INVISIBLE }, 3000)
    }

    override fun onDestroyView() {

        handler.removeCallbacksAndMessages(null)
        super.onDestroyView()
    }

    fun setColorForRounds(it: Int) {

        if (it == 1) {
            if (viewModel.lifePointsPlayer.value!! > 0) {
                binding.mcRound1Player?.setCardBackgroundColor(Color.rgb(255, 100, 0))
                binding.mcRound1Enemy?.setCardBackgroundColor(Color.DKGRAY)
            } else if (viewModel.lifePointsEnemy.value!! > 0){
                binding.mcRound1Player?.setCardBackgroundColor(Color.DKGRAY)
                binding.mcRound1Enemy?.setCardBackgroundColor(Color.rgb(255,100,0))
            }
        } else if (it == 2) {
            if (viewModel.lifePointsPlayer.value!! > 0) {
                binding.mcRound2Player?.setCardBackgroundColor(Color.rgb(255, 100, 0))
                binding.mcRound2Enemy?.setCardBackgroundColor(Color.DKGRAY)
            } else if (viewModel.lifePointsEnemy.value!! > 0) {
                binding.mcRound2Player?.setCardBackgroundColor(Color.DKGRAY)
                binding.mcRound2Enemy?.setCardBackgroundColor(Color.rgb(255,100,0))
            }
        } else if (it == 3) {
            if (viewModel.lifePointsPlayer.value!! > 0) {
                binding.mcRound3Player?.setCardBackgroundColor(Color.rgb(255, 100, 0))
                binding.mcRound3Enemy?.setCardBackgroundColor(Color.DKGRAY)
            } else if (viewModel.lifePointsEnemy.value!! > 0) {
                binding.mcRound3Player?.setCardBackgroundColor(Color.DKGRAY)
                binding.mcRound3Enemy?.setCardBackgroundColor(Color.rgb(255, 100, 0))
            }
        }
    }
}

