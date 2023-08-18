package com.example.abschlussaufgabe.ui


import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.abschlussaufgabe.FightViewModel
import com.example.abschlussaufgabe.MainViewModel
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
    private val fightViewModel: FightViewModel by activityViewModels()

    private lateinit var binding: FragmentFightBinding

    private lateinit var player: CharacterForFight
    private lateinit var enemy: CharacterForFight

    private val handler = Handler()

    // Variablen für die Farben
    private val white = Color.WHITE
    private val primary = Color.rgb(255, 105, 0)
    private val darkGray = Color.DKGRAY

    private var email = ""



    override fun onStart() {
        super.onStart()

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        context?.let { viewModel.playSound(it, R.raw.song_theme) }

        viewModel.imageBackground.value?.let { viewModel.hideImages(it) }
        viewModel.materialCard.value?.let { viewModel.hideMaterialCard(it) }

        binding.ivImage2Player?.visibility = View.INVISIBLE
        binding.ivImage2Enemy?.visibility = View.INVISIBLE

        invisibleAttacks()

        handler.removeCallbacks(fightViewModel.runnable)
        fightViewModel.resetPointsForNewGame()
        fightViewModel.resetToDefaultRounds()

        fightViewModel.selectRounds.observe(viewLifecycleOwner) {
            if (it == "1") {
                binding.mcRound1Player?.visibility = View.INVISIBLE
                binding.mcRound2Player?.visibility = View.INVISIBLE
                binding.mcRound3Player?.visibility = View.INVISIBLE
                binding.mcRound1Enemy?.visibility = View.INVISIBLE
                binding.mcRound2Enemy?.visibility = View.INVISIBLE
                binding.mcRound3Enemy?.visibility = View.INVISIBLE
            } else if (it == "3") {
                binding.mcRound1PlayerForOneRound?.visibility = View.INVISIBLE
                binding.mcRound1EnemyForOneRound?.visibility = View.INVISIBLE
            }
        }

        resetColorForRound()

        fightViewModel.selectTimer.observe(viewLifecycleOwner) {
            if (it == "kein Zeitlimit") {
                binding.tvTimer?.visibility = View.INVISIBLE
                binding.ivTimer?.setImageResource(R.drawable.no_time)
                fightViewModel.stopTimer()
            } else {
                binding.ivTimer?.visibility = View.INVISIBLE
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            email = it.getString("email").toString()
        }
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

        player = fightViewModel.player.value!!
        enemy = fightViewModel.enemy.value!!

        val user = viewModel.profile.value!!.find { it.email == email }
        binding.tvNamePlayer?.text = user!!.userName


        viewModel.userNameEnemy.observe(viewLifecycleOwner) {
            binding.tvNameEnemy?.text = it
        }

        fightViewModel.location.observe(viewLifecycleOwner) {
            binding.ivBackgroundFight?.setImageResource(it.image)
        }

        fightViewModel.player.observe(viewLifecycleOwner) {
            binding.ivCharacterImagePlayer?.setImageResource(it.imageFace)
            binding.ivImage1Player?.setImageResource(it.image)
            binding.ivImageDouble1Player?.setImageResource(it.image)
            binding.ivImageDouble2Player?.setImageResource(it.image)
            binding.tvCharacterNamePlayer?.text = it.name
            binding.lifePointsBarViewPlayer?.setMaxLifePoints(it.lifePointsStart) // Setze hier den Maximalwert
            binding.lifePointsBarViewPlayer?.setCurrentLifePoints(it.lifePoints) // Setze die aktuellen Lebenspunkte
            binding.chakraPointsBarViewPlayer?.setMaxChakraPoints(it.chakraPointsStart)
            binding.chakraPointsBarViewPlayer?.setCurrentChakraPoints(it.chakraPoints)

            binding.rvDefensePlayer?.adapter = DefensePlayerAdapter(it.defense, fightViewModel)
            binding.rvToolsPlayer?.adapter = ToolPlayerAdapter(it.tools, fightViewModel)
            binding.rvUniqueTraitsPlayer?.adapter = TraitPlayerAdapter(it.uniqueTraits, fightViewModel)
            binding.rvJutsuPlayer?.adapter = JutsuPlayerAdapter(it.jutsus, fightViewModel)
        }

        fightViewModel.enemy.observe(viewLifecycleOwner) {
            binding.ivCharacterImageEnemy?.setImageResource(it.imageFace)
            binding.ivImage1Enemy?.setImageResource(it.image)
            binding.ivImageDouble1Enemy?.setImageResource(it.image)
            binding.ivImageDouble2Enemy?.setImageResource(it.image)
            binding.tvCharacterNameEnemy?.text = it.name
            binding.lifePointsBarViewEnemy?.setMaxLifePoints(it.lifePointsStart)
            binding.lifePointsBarViewEnemy?.setCurrentLifePoints(it.lifePoints)
            binding.chakraPointsBarViewEnemy?.setMaxChakraPoints(it.chakraPointsStart)
            binding.chakraPointsBarViewEnemy?.setCurrentChakraPoints(it.chakraPoints)
        }


        fightViewModel.roundBegan.observe(viewLifecycleOwner) {
            if (it) {
                if (fightViewModel.selectTimer.value != "kein Zeitlimit") {
                    fightViewModel.startTimer()
                }
                handler.postDelayed(fightViewModel.runnable, 1000)
            }
        }

        fightViewModel.remainingTime.observe(viewLifecycleOwner) {
            binding.tvTimer?.text = it.toString()
        }

        fightViewModel.attackPlayer.observe(viewLifecycleOwner) {
            binding.ivImage2Player?.setImageResource(it.image)
            actionOfSelectionPlayer(it)
            fightViewModel.playRoundPlayer()
            if (player.lifePoints <= 0 || enemy.lifePoints <= 0 || fightViewModel.remainingTime.value == 0) {
                invisibleAttacks()
                fightViewModel.endRound()
            }
        }

        fightViewModel.attackEnemy.observe(viewLifecycleOwner) {
            if (fightViewModel.roundBegan.value == true) {
                binding.ivImage2Enemy?.setImageResource(it.image)
                if (player.lifePoints > 0 && enemy.lifePoints > 0 || fightViewModel.remainingTime.value!! > 0) {
                    fightViewModel.playRoundEnemy()
                    actionOfSelectionEnemy(it)
                } else {
                    fightViewModel.endRound()
                }
            }
        }

        fightViewModel.roundsWonPlayer.observe(viewLifecycleOwner) {
            if (fightViewModel.selectRounds.value == "3") {
                if (it >= 2 && fightViewModel.rounds.value!! >= 2) {
                    fightViewModel.setResult(true)
                } else if (fightViewModel.rounds.value!! >= 2 && it < 2) {
                    fightViewModel.setResult(false)
                } else if (fightViewModel.rounds.value!! >= 2 && it == 0) {
                    fightViewModel.setResult(false)
                }
            } else if (fightViewModel.selectRounds.value == "1") {
                if (it == 1 && fightViewModel.rounds.value == 1) {
                    fightViewModel.setResult(true)
                } else {
                    fightViewModel.setResult(false)
                }
            }
        }

        fightViewModel.rounds.observe(viewLifecycleOwner) {
            if (!fightViewModel.gameEnd.value!!) {
                setColorForRounds(it)
                runViewsRound()
                fightViewModel.resetPointsForNewRound()
            }
        }

        fightViewModel.toastMessage.observe(viewLifecycleOwner, Observer { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        })


        // Navigation

        binding.ivBack?.setOnClickListener {
            findNavController().navigate(FightFragmentDirections.actionFightFragmentToCharacterSelectionFragment(viewModel.currentUser.value!!.email.toString()))
        }

        fightViewModel.gameEnd.observe(viewLifecycleOwner) {
            if (it) {
                handler.postDelayed({
                    findNavController().navigate(FightFragmentDirections.actionFightFragmentToResultFragment(viewModel.currentUser.value!!.email.toString()))
                }, 3000)
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
                binding.ivImage2Player?.setImageResource(attack.image)
                binding.ivImage2Player?.visibility = View.VISIBLE

                handler.postDelayed(
                    { binding.ivImage2Player?.visibility = View.INVISIBLE }, duration
                )

            } else if (attack.name == "Shuriken") {
                binding.ivImage2Player?.setImageResource(attack.image)
                binding.ivImage2Player?.visibility = View.VISIBLE
                binding.ivImage2Player?.rotationX = 50F

                handler.postDelayed(
                    { binding.ivImage2Player?.visibility = View.INVISIBLE }, duration
                )
                handler.postDelayed(
                    { binding.ivImage2Player?.rotationX = 0F }, duration
                )
            } else if (attack.name == "Eisensand") {
                binding.ivImage2Player?.setImageResource(attack.image)
                binding.ivImage2Player?.visibility = View.VISIBLE
                binding.ivImage1Player?.visibility = View.INVISIBLE

                handler.postDelayed(
                    { binding.ivImage2Player?.visibility = View.INVISIBLE }, duration
                )
                handler.postDelayed(
                    { binding.ivImage1Player?.visibility = View.VISIBLE }, duration
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

            } else if (attack.name == "Shuriken") {
                binding.ivImage2Enemy?.setImageResource(attack.image)
                binding.ivImage2Enemy?.visibility = View.VISIBLE
                binding.ivImage2Enemy?.rotationX = 50F

                handler.postDelayed(
                    { binding.ivImage2Enemy?.visibility = View.INVISIBLE }, duration
                )
                handler.postDelayed(
                    { binding.ivImage2Enemy?.rotationX = 0F }, duration
                )
            } else if (attack.name == "Eisensand") {
                binding.ivImage2Enemy?.setImageResource(attack.image)
                binding.ivImage2Enemy?.visibility = View.VISIBLE
                binding.ivImage1Enemy?.visibility = View.INVISIBLE

                handler.postDelayed(
                    { binding.ivImage2Enemy?.visibility = View.INVISIBLE }, duration
                )
                handler.postDelayed(
                    { binding.ivImage1Enemy?.visibility = View.VISIBLE }, duration
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
            } else {
                binding.ivImage1Player?.setImageResource(attack.image)

                handler.postDelayed(
                    { binding.ivImage1Player?.setImageResource(player.image)}, duration
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
            } else {
                binding.ivImage1Enemy?.setImageResource(attack.image)

                handler.postDelayed(
                    { binding.ivImage1Enemy?.setImageResource(enemy.image)}, duration
                )
            }
        }
    }

    // setzt die Attacken für den Spieler auf sichtbar
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

    // setzt die Attacken für den Spieler auf unsichtbar
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

    // zeigt nach und nach die Views in welcher Runde man sich befindet und Fight, je nach dem ob man 1 oder 3 Runden spielt
    // danach werden die Attacken sichtbar gemacht und der Timer startet
     fun runViewsRound() {

         if (fightViewModel.rounds.value!! == 0) {
             binding.ivRound?.setImageResource(R.drawable.round_1)
         } else if (fightViewModel.rounds.value!! == 1) {
             binding.ivRound?.setImageResource(R.drawable.round_2)
         } else if (fightViewModel.rounds.value!! == 2) {
             binding.ivRound?.setImageResource(R.drawable.round_3)
         }

         binding.ivRound?.visibility = View.INVISIBLE
         binding.ivFight?.visibility = View.INVISIBLE

         if (fightViewModel.selectRounds.value == "3") {
             if (fightViewModel.rounds.value!! > 0 && player.lifePoints < 2 && enemy.lifePoints < 2) {
                 runViewWinnerOrLoser()

                 handler.postDelayed({
                     binding.ivRound?.visibility = View.VISIBLE

                     handler.postDelayed({
                         binding.ivRound?.visibility = View.INVISIBLE

                         handler.postDelayed({
                             binding.ivFight?.visibility = View.VISIBLE

                             handler.postDelayed({
                                 binding.ivFight?.visibility = View.INVISIBLE

                                 handler.postDelayed({
                                     fightViewModel.startRound(true)
                                     visibleAttacks()
                                 }, 1000)
                             }, 2000)
                         }, 2000)
                     }, 2000)
                 }, 3000)
             } else {
                 binding.ivRound?.visibility = View.VISIBLE

                 handler.postDelayed({
                     binding.ivRound?.visibility = View.INVISIBLE

                     handler.postDelayed({
                         binding.ivFight?.visibility = View.VISIBLE

                         handler.postDelayed({
                             binding.ivFight?.visibility = View.INVISIBLE

                             handler.postDelayed({
                                 fightViewModel.startRound(true)
                                 visibleAttacks()
                             }, 1000)
                         }, 2000)
                     }, 2000)
                 }, 2000)
             }
         } else if (fightViewModel.selectRounds.value == "1") {
             if (fightViewModel.rounds.value!! > 0) {
                 runViewWinnerOrLoser()
                 visibleAttacks()
             } else {
                 binding.ivRound?.visibility = View.VISIBLE

                 handler.postDelayed({
                     binding.ivRound?.visibility = View.INVISIBLE

                     handler.postDelayed({
                         binding.ivFight?.visibility = View.VISIBLE

                         handler.postDelayed({
                             binding.ivFight?.visibility = View.INVISIBLE

                             handler.postDelayed({
                                 fightViewModel.startRound(true)
                                 visibleAttacks()
                             }, 1000)
                         }, 2000)
                     }, 2000)
                 }, 2000)
             }
         }
     }

    private fun runViewWinnerOrLoser() {

        if (fightViewModel.selectTimer.value != "kein Zeitlimit") {
            if (fightViewModel.remainingTime.value == 0) {
                if (player.lifePoints > enemy.lifePoints) {
                    binding.ivResult?.setImageResource(R.drawable.winner)
                } else if (player.lifePoints < enemy.lifePoints) {
                    binding.ivResult?.setImageResource(R.drawable.loser)
                }
            } else {
                if (player.lifePoints == 0) {
                    binding.ivResult?.setImageResource(R.drawable.loser)
                } else if (player.lifePoints > 0) {
                    binding.ivResult?.setImageResource(R.drawable.winner)
                }
            }
        } else {
            if (player.lifePoints == 0) {
                binding.ivResult?.setImageResource(R.drawable.loser)
            } else if (player.lifePoints > 0) {
                binding.ivResult?.setImageResource(R.drawable.winner)
            }
        }

        binding.ivResult?.visibility = View.VISIBLE

        handler.postDelayed(
            { binding.ivResult?.visibility = View.INVISIBLE }, 3000)
    }

    override fun onDestroyView() {

        handler.removeCallbacksAndMessages(null)
        super.onDestroyView()
    }

    private fun setColorForRounds(rounds: Int) {

        if (rounds == 1) {
            if (player.lifePoints > 0) {
                binding.mcRound1Player?.background?.setTint(primary)
                binding.mcRound1PlayerForOneRound?.background?.setTint(primary)
                binding.mcRound1Enemy?.background?.setTint(darkGray)
                binding.mcRound1EnemyForOneRound?.background?.setTint(darkGray)
            } else if (enemy.lifePoints > 0){
                binding.mcRound1Player?.background?.setTint(darkGray)
                binding.mcRound1PlayerForOneRound?.background?.setTint(darkGray)
                binding.mcRound1Enemy?.background?.setTint(primary)
                binding.mcRound1EnemyForOneRound?.background?.setTint(primary)
            }
        } else if (rounds == 2) {
            if (player.lifePoints > 0) {
                binding.mcRound2Player?.background?.setTint(primary)
                binding.mcRound2Enemy?.background?.setTint(darkGray)
            } else if (enemy.lifePoints > 0) {
                binding.mcRound2Player?.background?.setTint(darkGray)
                binding.mcRound2Enemy?.background?.setTint(primary)
            }
        } else if (rounds == 3) {
            if (player.lifePoints > 0) {
                binding.mcRound3Player?.background?.setTint(primary)
                binding.mcRound3Enemy?.background?.setTint(darkGray)
            } else if (enemy.lifePoints > 0) {
                binding.mcRound3Player?.background?.setTint(darkGray)
                binding.mcRound3Enemy?.background?.setTint(primary)
            }
        }
    }

    private fun resetColorForRound() {

        binding.mcRound1Player?.background?.setTint(white)
        binding.mcRound2Player?.background?.setTint(white)
        binding.mcRound3Player?.background?.setTint(white)
        binding.mcRound1PlayerForOneRound?.background?.setTint(white)

        binding.mcRound1Enemy?.background?.setTint(white)
        binding.mcRound2Enemy?.background?.setTint(white)
        binding.mcRound3Enemy?.background?.setTint(white)
        binding.mcRound1EnemyForOneRound?.background?.setTint(white)
    }
}

