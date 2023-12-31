package com.example.abschlussaufgabe.ui


import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import android.widget.ImageView
import android.widget.Toast
import androidx.core.os.postDelayed
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.abschlussaufgabe.AuthViewModel
import com.example.abschlussaufgabe.FightViewModel
import com.example.abschlussaufgabe.FirestoreViewModel
import com.example.abschlussaufgabe.MainViewModel
import com.example.abschlussaufgabe.R
import com.example.abschlussaufgabe.adapter.DefensePlayerAdapter
import com.example.abschlussaufgabe.adapter.JutsuPlayerAdapter
import com.example.abschlussaufgabe.adapter.ToolPlayerAdapter
import com.example.abschlussaufgabe.adapter.TraitPlayerAdapter
import com.example.abschlussaufgabe.data.datamodels.modelForFight.Attack
import com.example.abschlussaufgabe.data.datamodels.modelForFight.CharacterForFight
import com.example.abschlussaufgabe.databinding.FragmentFightBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class FightFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private val fightViewModel: FightViewModel by activityViewModels()
    private val storeViewModel: FirestoreViewModel by activityViewModels()
    private val authViewModel: AuthViewModel by activityViewModels()

    private lateinit var binding: FragmentFightBinding

    private lateinit var player: CharacterForFight
    private lateinit var enemy: CharacterForFight

    private val handler = Handler()

    // Variablen für die Farben
    private val white = Color.WHITE
    private val primary = Color.rgb(255, 105, 0)
    private val darkGray = Color.DKGRAY




    override fun onStart() {
        super.onStart()

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        fightViewModel.volume.observe(viewLifecycleOwner) {
            context?.let { fightViewModel.playSound(it, R.raw.song_theme) }
        }

        viewModel.imageBackground.value?.let { viewModel.hideImages(it) }
        viewModel.materialCard.value?.let { viewModel.hideMaterialCard(it) }

        binding.ivImageToolPlayer?.visibility = View.INVISIBLE
        binding.ivImageToolEnemy?.visibility = View.INVISIBLE

        invisibleAttacks()

        handler.removeCallbacks(fightViewModel.runnable)

        fightViewModel.selectFight.observe(viewLifecycleOwner) {
            if (it == R.string.single.toString()) {
                binding.mcTeammate1?.visibility = View.INVISIBLE
                binding.mcTeammate2?.visibility = View.INVISIBLE
                binding.ivTeammatePlayer?.visibility = View.INVISIBLE
                binding.mcTeammate1Enemy?.visibility = View.INVISIBLE
                binding.mcTeammate2Enemy?.visibility = View.INVISIBLE
                binding.ivTeammateEnemy?.visibility = View.INVISIBLE
            } else if (it == R.string.team.toString()) {
                binding.mcTeammate1?.visibility = View.VISIBLE
                binding.mcTeammate2?.visibility = View.VISIBLE
                binding.mcTeammate1Enemy?.visibility = View.VISIBLE
                binding.mcTeammate2Enemy?.visibility = View.VISIBLE
            }
        }

        fightViewModel.selectRounds.observe(viewLifecycleOwner) {
            if (it == "1") {
                binding.mcRound1Player?.visibility = View.VISIBLE
                binding.mcRound2Player?.visibility = View.INVISIBLE
                binding.mcRound1Enemy?.visibility = View.VISIBLE
                binding.mcRound2Enemy?.visibility = View.INVISIBLE
            } else if (it == "3") {
                binding.mcRound1Player?.visibility = View.VISIBLE
                binding.mcRound2Player?.visibility = View.VISIBLE
                binding.mcRound1Enemy?.visibility = View.VISIBLE
                binding.mcRound2Enemy?.visibility = View.VISIBLE
            }
        }

        fightViewModel.selectTimer.observe(viewLifecycleOwner) {
            if (it == "kein Zeitlimit") {
                binding.tvTimer?.visibility = View.INVISIBLE
                binding.ivTimer?.setImageResource(R.drawable.no_time)
                fightViewModel.stopTimer()
            } else {
                binding.ivTimer?.visibility = View.INVISIBLE
            }
        }

        resetColorForRound()
        fightViewModel.setRemainingTimeToStart()
        fightViewModel.resetPointsForNewGame()
        fightViewModel.resetToDefaultRounds()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fight, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        player = fightViewModel.player.value!!
        enemy = fightViewModel.enemy.value!!


        if (authViewModel.currentUser.value != null) {
            binding.tvNamePlayer?.text = storeViewModel.currentProfile.value!!.userName
        } else {
            binding.tvNamePlayer?.setText(R.string.guest)
        }

        viewModel.userNameEnemy.observe(viewLifecycleOwner) {
            binding.tvNameEnemy?.text = it
        }

        fightViewModel.location.observe(viewLifecycleOwner) {
            binding.ivBackgroundFight?.setImageResource(it.image)
        }

        fightViewModel.player.observe(viewLifecycleOwner) {
            binding.ivCharacterImagePlayer?.setImageResource(it.imageFace)
            binding.ivImagePlayer?.setImageResource(it.imagePlayer)
            binding.ivImageDouble1Player?.setImageResource(it.imagePlayer)
            binding.ivImageDouble2Player?.setImageResource(it.imagePlayer)
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
            binding.ivImageEnemy?.setImageResource(it.imageEnemy)
            binding.ivImageDouble1Enemy?.setImageResource(it.imageEnemy)
            binding.ivImageDouble2Enemy?.setImageResource(it.imageEnemy)
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

        // je nach Attacke wird das Bild angezeigt
        // je nach dem was für eine Attacke ausgewählt wird werden verschiedene Aktionen ausgeführt
        // wenn einer keine Lebenspunkte mehr hat oder die Zeit abgelaufen ist werden die Views der Attacken ausgeblendt
        // und die Runde beendet
        fightViewModel.attackPlayer.observe(viewLifecycleOwner) {
            binding.ivImageToolPlayer?.setImageResource(it.imagePlayer)
            actionOfSelectionPlayer(it)
            fightViewModel.playRoundPlayer()
            if (player.lifePoints <= 0 || enemy.lifePoints <= 0 || fightViewModel.remainingTime.value == 0) {
                invisibleAttacks()
                fightViewModel.endRound()
            }
        }

        // wenn die Runde beginnt wird je nach Attacke das Bild angezeigt
        // so lange beide Spieler noch Leben haben und die Zeit noch nicht abgelaufen ist werden Attacken ausgeführt
        // wenn die Bedingungen nicht mehr erfüllt sind wird die Runde beendet
        fightViewModel.attackEnemy.observe(viewLifecycleOwner) {
            if (fightViewModel.roundBegan.value == true) {
                binding.ivImageToolEnemy?.setImageResource(it.imageEnemy)
                if (player.lifePoints > 0 && enemy.lifePoints > 0 || fightViewModel.remainingTime.value!! > 0) {
                    fightViewModel.playRoundEnemy()
                    actionOfSelectionEnemy(it)
                } else {
                    fightViewModel.endRound()
                }
            }
        }

        // je nach gewonnen Runden wird das Ergebnis auf gewonnen oder verloren gesetzt
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

        // wenn die Rundenanzahl sich ändert und das Spiel noch nicht beendet ist werden die Farben für die gewonnen Runden gesetzt und
        // die jeweiligen Views angezeigt und die Punkte für eine neue Runde zurückgesetzt
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
            val builder = MaterialAlertDialogBuilder(requireContext())
            builder.setTitle("Spiel verlassen")
                .setMessage(
                    """Möchtest du das Spiel wirklich beenden?""".trimMargin()
                )
                .setPositiveButton("Ja") { dialog, which ->
                    fightViewModel.stopSound()
                    findNavController().navigate(R.id.selectionGameFragment)
                }
                .setNegativeButton("Nein") { dialog, which ->
                    dialog.dismiss()
                }
                .show()
        }

        fightViewModel.gameEnd.observe(viewLifecycleOwner) {
            if (it) {
                handler.postDelayed({
                    findNavController().navigate(FightFragmentDirections.actionFightFragmentToResultFragment())
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
            in person.defense -> changeViewsForDefense(check, it)
            in person.tools -> changeViewsForTools(check, it)
            in person.uniqueTraits -> changeViewsForAttack(check, it)
            in person.jutsus -> changeViewsForAttack(check, it)
        }
    }

    fun actionOfSelectionEnemy(it: Attack) {

        val check = false
        val person = enemy


        when (it) {
            in person.defense -> changeViewsForDefense(check, it)
            in person.tools -> changeViewsForTools(check, it)
            in person.uniqueTraits -> changeViewsForAttack(check, it)
            in person.jutsus -> changeViewsForAttack(check, it)
        }
    }


    // wechselt ein Bild für eine bestimmte Zeit oder
    // ändert die Visibility der Views oder das Image und bewegt es zum Gegner

    private fun changeViewsForAttack(check: Boolean, attack: Attack) {

        if (check) {
            if (attack.name == "Heilung") {
                binding.ivImageHealPlayer?.visibility = View.VISIBLE

                handler.postDelayed(
                    { binding.ivImageHealPlayer?.visibility = View.INVISIBLE }, 1000)
            } else if (attack.name == "Jutsu des vertrauten Geistes") {
                Thread(Runnable {
                    this.requireActivity().runOnUiThread {
                        binding.ivImageVertrauterGeistPlayer?.setImageResource(attack.imagePlayer)
                    }
                }).start()
                binding.ivImageVertrauterGeistPlayer?.visibility = View.VISIBLE
                imageAnimationTranslate(player, binding.ivImageVertrauterGeistPlayer)

                handler.postDelayed(
                    { binding.ivImageVertrauterGeistPlayer?.visibility = View.INVISIBLE }, 1000
                )
            } else if (attack.name == "Byakugan" || attack.name == "Sharingan" || attack.name == "Jahundertstärke") {
                Thread(Runnable {
                    this.requireActivity().runOnUiThread {
                        binding.ivImagePlayer?.setImageResource(attack.imagePlayer)
                    }
                }).start()

                imageAnimationScale(binding.ivImagePlayer)

                handler.postDelayed(
                    { binding.ivImagePlayer?.setImageResource(player.imagePlayer) }, 1000)
            } else {
                Thread(Runnable {
                    this.requireActivity().runOnUiThread {
                        binding.ivImagePlayer?.setImageResource(attack.imagePlayer)
                    }
                }).start()

                imageAnimationTranslate(player, binding.ivImagePlayer)

                handler.postDelayed(
                    { binding.ivImagePlayer?.setImageResource(player.imagePlayer) }, 1000)
            }
        } else {
            if (attack.name == "Heilung") {
                binding.ivImageHealEnemy?.visibility = View.VISIBLE

                handler.postDelayed(
                    { binding.ivImageHealEnemy?.visibility = View.INVISIBLE }, 1000)
            } else if (attack.name == "Jutsu des vertrauten Geistes") {
                binding.ivImageVertrauterGeistEnemy?.setImageResource(attack.imageEnemy)
                binding.ivImageVertrauterGeistEnemy?.visibility = View.VISIBLE
                imageAnimationTranslate(enemy, binding.ivImageVertrauterGeistEnemy)

                handler.postDelayed(
                    { binding.ivImageVertrauterGeistEnemy?.visibility = View.INVISIBLE }, 1000)
            } else if (attack.name == "Byakugan" || attack.name == "Sharingan" || attack.name == "Jahundertstärke") {
                binding.ivImageEnemy?.setImageResource(attack.imageEnemy)

                imageAnimationScale(binding.ivImageEnemy)

                handler.postDelayed(
                    { binding.ivImageEnemy?.setImageResource(enemy.imageEnemy) }, 1000)
            } else {
                binding.ivImageEnemy?.setImageResource(attack.imageEnemy)

                imageAnimationTranslate(enemy, binding.ivImageEnemy)

                handler.postDelayed(
                    { binding.ivImageEnemy?.setImageResource(enemy.imageEnemy) }, 1000)
            }
        }
    }

    private fun changeViewsForTools(check: Boolean, attack: Attack) {

        if (check) {
            if (attack.name == "Schwert") {
                Thread(Runnable {
                    this.requireActivity().runOnUiThread {
                        binding.ivImagePlayer?.setImageResource(attack.imagePlayer)
                    }
                }).start()

                imageAnimationTranslate(player, binding.ivImagePlayer)

                handler.postDelayed({
                    binding.ivImagePlayer?.setImageResource(player.imagePlayer)
                }, 1000)
            }

            binding.ivImageToolPlayer?.setImageResource(attack.imagePlayer)
            binding.ivImageToolPlayer?.visibility = View.VISIBLE

            imageAnimationTranslate(player, binding.ivImageToolPlayer)

            handler.postDelayed({
                binding.ivImageToolPlayer?.visibility = View.INVISIBLE
                binding.ivImagePlayer?.visibility = View.VISIBLE }, 1000)
        } else {
            if (attack.name == "Schwert") {
                binding.ivImageEnemy?.setImageResource(attack.imageEnemy)
                imageAnimationTranslate(enemy, binding.ivImageEnemy)

                handler.postDelayed({
                    binding.ivImageEnemy?.setImageResource(enemy.imageEnemy)
                }, 1000)
            }

            binding.ivImageToolEnemy?.setImageResource(attack.imageEnemy)
            binding.ivImageToolEnemy?.visibility = View.VISIBLE

            imageAnimationTranslate(enemy, binding.ivImageToolEnemy)

            handler.postDelayed({
                binding.ivImageToolEnemy?.visibility = View.INVISIBLE
                binding.ivImageEnemy?.visibility = View.VISIBLE }, 1000)
        }
    }

    private fun changeViewsForDefense(check: Boolean, attack: Attack) {

        if (check) {
            if (attack.name == "Schattendoppelgänger" || attack.name == "Sanddoppelgänger" || attack.name == "Insektendoppelgänger") {
                binding.ivImageDouble1Player?.visibility = View.VISIBLE
                binding.ivImageDouble2Player?.visibility = View.VISIBLE

                handler.postDelayed(
                    { binding.ivImageDouble1Player?.visibility = View.INVISIBLE }, 1000)

                handler.postDelayed(
                    { binding.ivImageDouble2Player?.visibility = View.INVISIBLE }, 1000)
            } else {
                Thread(Runnable {
                    this.requireActivity().runOnUiThread {
                        binding.ivImagePlayer?.setImageResource(attack.imagePlayer)
                    }
                }).start()

                handler.postDelayed(
                    { binding.ivImagePlayer?.setImageResource(player.imagePlayer)}, 1000)
            }
        } else {
            if (attack.name == "Schattendoppelgänger" || attack.name == "Sanddoppelgänger" || attack.name == "Insektendoppelgänger") {
                binding.ivImageDouble1Enemy?.visibility = View.VISIBLE
                binding.ivImageDouble2Enemy?.visibility = View.VISIBLE

                handler.postDelayed(
                    { binding.ivImageDouble1Enemy?.visibility = View.INVISIBLE }, 1000)

                handler.postDelayed(
                    { binding.ivImageDouble2Enemy?.visibility = View.INVISIBLE }, 1000)
            } else {
                binding.ivImageEnemy?.setImageResource(attack.imageEnemy)

                handler.postDelayed(
                    { binding.ivImageEnemy?.setImageResource(enemy.imageEnemy)}, 1000)
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

    // setzt die Views je nach dem ob der Spieler gewonnen oder verloren hat
    private fun runViewWinnerOrLoser() {

        if (fightViewModel.selectTimer.value != "kein Zeitlimit") {
            if (fightViewModel.remainingTime.value == 0) {
                if (player.lifePoints > enemy.lifePoints) {
                    binding.ivResult?.setImageResource(R.drawable.winner)
                } else if (player.lifePoints < enemy.lifePoints) {
                    binding.ivResult?.setImageResource(R.drawable.loser)
                }
            } else {
                if (player.lifePoints <= 0 && enemy.lifePoints > 0) {
                    binding.ivResult?.setImageResource(R.drawable.loser)
                } else if (player.lifePoints > 0 && enemy.lifePoints <= 0) {
                    binding.ivResult?.setImageResource(R.drawable.winner)
                }
            }
        } else {
            if (player.lifePoints <= 0 && enemy.lifePoints > 0) {
                binding.ivResult?.setImageResource(R.drawable.loser)
            } else if (player.lifePoints > 0 && enemy.lifePoints <= 0) {
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

    // setzt die Farben für die gewonnen Runden der Spieler
    private fun setColorForRounds(rounds: Int) {

        if (rounds == 1) {
            if (player.lifePoints > 0) {
                binding.mcRound1Player?.background?.setTint(primary)
                binding.mcRound1Enemy?.background?.setTint(darkGray)
            } else if (enemy.lifePoints > 0){
                binding.mcRound1Player?.background?.setTint(darkGray)
                binding.mcRound1Enemy?.background?.setTint(primary)
            }
        } else if (rounds == 2) {
            if (player.lifePoints > 0) {
                binding.mcRound2Player?.background?.setTint(primary)
                binding.mcRound2Enemy?.background?.setTint(darkGray)
            } else if (enemy.lifePoints > 0) {
                binding.mcRound2Player?.background?.setTint(darkGray)
                binding.mcRound2Enemy?.background?.setTint(primary)
            }
        }
    }

    // setzt die Farben der gewonnen Runden wieder zurück auf weiß
    private fun resetColorForRound() {

        binding.mcRound1Player?.background?.setTint(white)
        binding.mcRound2Player?.background?.setTint(white)

        binding.mcRound1Enemy?.background?.setTint(white)
        binding.mcRound2Enemy?.background?.setTint(white)
    }

    private fun imageAnimationTranslate(attacker: CharacterForFight, image: ImageView?) {

        if (attacker == player) {
            val animator = ObjectAnimator.ofFloat(image, View.TRANSLATION_X, 750f)
            animator.duration = 500
            animator.repeatCount = 1
            animator.repeatMode = ObjectAnimator.REVERSE
            animator.start()
        } else if (attacker == enemy) {
            val animator = ObjectAnimator.ofFloat(image, View.TRANSLATION_X, -750f)
            animator.duration = 500
            animator.repeatCount = 1
            animator.repeatMode = ObjectAnimator.REVERSE
            animator.start()
        }
    }

    private fun imageAnimationScale(image: ImageView?) {
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 3f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 3f)
        val animator =
            ObjectAnimator.ofPropertyValuesHolder(image, scaleX, scaleY)
        animator.duration = 900
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.interpolator = BounceInterpolator()
        animator.start()
    }
}

