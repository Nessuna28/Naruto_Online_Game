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
import com.example.abschlussaufgabe.AuthViewModel
import com.example.abschlussaufgabe.FightViewModel
import com.example.abschlussaufgabe.FirestoreViewModel
import com.example.abschlussaufgabe.MainViewModel
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
    private val storeViewModel: FirestoreViewModel by activityViewModels()
    private val authViewModel: AuthViewModel by activityViewModels()

    private lateinit var binding: FragmentCharacterSelectionBinding

    private var checkCharacterPlayer = false
    private var checkTeammate1Player = false
    private var checkTeammate2Player = false

    private var checkCharacterEnemy = false
    private var checkTeammate1Enemy = false
    private var checkTeammate2Enemy = false



    @SuppressLint("ResourceAsColor")
    override fun onStart() {
        super.onStart()

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

        viewModel.materialCard.value?.let { viewModel.showMaterialCard(it) }
        viewModel.imageHome.value?.let { viewModel.showImages(it) }
        viewModel.tvUserName.value?.let { viewModel.showTextView(it) }
        viewModel.cvImageProfile.value?.let { viewModel.showMaterialCard(it) }
        viewModel.imageBackground.value?.let { viewModel.hideImages(it) }
        viewModel.imageTitle.value?.let { viewModel.hideImages(it) }

        val firstCharacter = fightViewModel.characterForFight.value!!.first()
        binding.tvCharacterNamePlayer?.text = firstCharacter.name
        binding.tvCharacterNameEnemy?.text = firstCharacter.name
        binding.ivSelectionPlayer?.setImageResource(firstCharacter.imagePlayer)
        binding.ivSelectionEnemy?.setImageResource(firstCharacter.imageEnemy)

        binding.ivSelectionTeammate1Player?.visibility = View.INVISIBLE
        binding.ivSelectionTeammate1Player?.setImageResource(firstCharacter.imagePlayer)
        binding.ivSelectionTeammate2Player?.visibility = View.INVISIBLE
        binding.ivSelectionTeammate2Player?.setImageResource(firstCharacter.imagePlayer)
        binding.tvTeammate1PlayerName?.visibility = View.INVISIBLE
        binding.tvTeammate1PlayerName?.text = firstCharacter.name
        binding.tvTeammate2PlayerName?.visibility = View.INVISIBLE
        binding.tvTeammate2PlayerName?.text = firstCharacter.name
        binding.ivSelectionTeammate1Enemy?.visibility = View.INVISIBLE
        binding.ivSelectionTeammate1Enemy?.setImageResource(firstCharacter.imageEnemy)
        binding.ivSelectionTeammate2Enemy?.visibility = View.INVISIBLE
        binding.ivSelectionTeammate2Enemy?.setImageResource(firstCharacter.imageEnemy)
        binding.tvTeammate1EnemyName?.visibility = View.INVISIBLE
        binding.tvTeammate1EnemyName?.text = firstCharacter.name
        binding.tvTeammate2EnemyName?.visibility = View.INVISIBLE
        binding.tvTeammate2EnemyName?.text = firstCharacter.name

        resetToDefault()

        viewModel.setUserNameEnemy("Computer")
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_character_selection, container, false)
        return binding.root
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (authViewModel.currentUser.value != null) {
            binding.tvPlayerName?.text = storeViewModel.currentProfile.value!!.userName
        } else {
            binding.tvPlayerName?.setText(R.string.guest)
        }

        viewModel.userNameEnemy.observe(viewLifecycleOwner) {
            binding.tvEnemyName?.text = it
        }

        fightViewModel.characterForFight.observe(viewLifecycleOwner) {
            binding.rvCharactersPlayer?.adapter = SelectionCharacterPlayerAdapter(it, fightViewModel)
            binding.rvCharactersEnemy?.adapter = SelectionCharacterEnemyAdapter(it, fightViewModel)
        }

        fightViewModel.player.observe(viewLifecycleOwner) {
            binding.tvCharacterNamePlayer?.visibility = View.VISIBLE
            binding.tvCharacterNamePlayer?.text = it.name
            binding.ivSelectionPlayer?.visibility = View.VISIBLE
            binding.ivSelectionPlayer?.setImageResource(it.imagePlayer)
            binding.tvTitleJutsusPlayer?.visibility = View.VISIBLE
            binding.rvJutsusPlayer?.visibility = View.VISIBLE
            binding.rvJutsusPlayer?.adapter = JutsuPlayerAdapter(it.jutsus, fightViewModel)
            binding.tvTitleTraitsPlayer?.visibility = View.VISIBLE
            binding.rvTraitsPlayer?.visibility = View.VISIBLE
            binding.rvTraitsPlayer?.adapter = TraitPlayerAdapter(it.uniqueTraits, fightViewModel)
        }

        fightViewModel.teamPlayer.observe(viewLifecycleOwner) {
            if (fightViewModel.selectFight.value == R.string.team.toString()) {
                if (checkCharacterPlayer) {
                    binding.ivSelectionTeammate1Player?.setImageResource(it.teammate1.imagePlayer)
                    binding.ivSelectionTeammate1Player?.visibility = View.VISIBLE
                    binding.tvTeammate1PlayerName?.text = it.teammate1.name
                    binding.tvTeammate1PlayerName?.visibility = View.VISIBLE
                    binding.ivSelectionTeammate2Player?.setImageResource(it.teammate2.imagePlayer)
                    binding.ivSelectionTeammate2Player?.visibility = View.VISIBLE
                    binding.tvTeammate2PlayerName?.text = it.teammate2.name
                    binding.tvTeammate2PlayerName?.visibility = View.VISIBLE
                }
            }
        }

        fightViewModel.enemy.observe(viewLifecycleOwner) {
            binding.tvCharacterNameEnemy?.visibility = View.VISIBLE
            binding.tvCharacterNameEnemy?.text = it.name
            binding.ivSelectionEnemy?.visibility = View.VISIBLE
            binding.ivSelectionEnemy?.setImageResource(it.imageEnemy)
            binding.tvTitleJutsusEnemy?.visibility = View.VISIBLE
            binding.rvJutsusEnemy?.visibility = View.VISIBLE
            binding.rvJutsusEnemy?.adapter = JutsuEnemyAdapter(it.jutsus)
            binding.tvTitleTraitsEnemy?.visibility = View.VISIBLE
            binding.rvTraitsEnemy?.visibility = View.VISIBLE
            binding.rvTraitsEnemy?.adapter = TraitEnemyAdapter(it.uniqueTraits)
        }

        fightViewModel.teamEnemy.observe(viewLifecycleOwner) {
            if (fightViewModel.selectFight.value == R.string.team.toString()) {
                if (checkCharacterEnemy) {
                    binding.ivSelectionTeammate1Enemy?.setImageResource(it.teammate1.imageEnemy)
                    binding.ivSelectionTeammate1Enemy?.visibility = View.VISIBLE
                    binding.tvTeammate1EnemyName?.text = it.teammate1.name
                    binding.tvTeammate1EnemyName?.visibility = View.VISIBLE
                    binding.ivSelectionTeammate2Enemy?.setImageResource(it.teammate2.imageEnemy)
                    binding.ivSelectionTeammate2Enemy?.visibility = View.VISIBLE
                    binding.tvTeammate2EnemyName?.text = it.teammate2.name
                    binding.tvTeammate2EnemyName?.visibility = View.VISIBLE
                }
            }
        }


        // OnClickListerner & Navigation

        binding.btnOkPlayer?.setOnClickListener {
            if (fightViewModel.selectFight.value == R.string.team.toString()) {
                if (!checkCharacterPlayer && !checkTeammate1Player && !checkTeammate2Player) {
                    if (fightViewModel.player.isInitialized) {
                        binding.ivSelectionPlayer?.setImageResource(fightViewModel.player.value!!.imagePose)
                        binding.ivSelectionTeammate1Player?.visibility = View.VISIBLE
                        binding.ivSelectionTeammate2Player?.visibility = View.VISIBLE
                        binding.tvTeammate1PlayerName?.visibility = View.VISIBLE
                        binding.tvTeammate2PlayerName?.visibility = View.VISIBLE
                        checkCharacterPlayer = true
                    }
                } else if (checkCharacterPlayer && !checkTeammate1Player && !checkTeammate2Player) {
                    binding.ivSelectionTeammate1Player?.setImageResource(fightViewModel.teamPlayer.value!!.teammate1.imagePose)
                    checkTeammate1Player = true
                } else if (checkCharacterPlayer && checkTeammate1Player && !checkTeammate2Player) {
                    binding.ivSelectionTeammate2Player?.setImageResource(fightViewModel.teamPlayer.value!!.teammate2.imagePose)
                    checkTeammate2Player = true

                    it.setBackgroundColor(Color.GREEN)
                    fightViewModel.confirmSelectionPlayer(true)
                    binding.rvCharactersPlayer?.isEnabled = false
                    binding.rvCharactersEnemy?.isEnabled = true
                    check()
                }
            } else if (fightViewModel.selectFight.value == R.string.single.toString()) {
                if (fightViewModel.player.isInitialized) {
                    binding.ivSelectionPlayer?.setImageResource(fightViewModel.player.value!!.imagePose)
                    it.setBackgroundColor(Color.GREEN)
                    fightViewModel.confirmSelectionPlayer(true)
                    binding.rvCharactersPlayer?.isEnabled = false
                    binding.rvCharactersEnemy?.isEnabled = true
                    check()
                }
            }
        }

        binding.btnRandomPlayer?.setOnClickListener {
            if (fightViewModel.selectFight.value == R.string.team.toString()) {
                if (!checkCharacterPlayer && !checkTeammate1Player && !checkTeammate2Player) {
                    fightViewModel.randomCharacterForPlayer()
                    checkCharacterPlayer = true
                } else if (checkCharacterPlayer && !checkTeammate1Player && !checkTeammate2Player) {
                    fightViewModel.randomTeamForPlayer()
                    checkTeammate1Player = true
                } else if (checkCharacterPlayer && checkTeammate1Player && !checkTeammate2Player) {
                    fightViewModel.randomTeamForEnemy()
                    checkTeammate2Player = true
                    fightViewModel.confirmSelectionPlayer(true)
                }
            } else {
                fightViewModel.randomCharacterForPlayer()
                fightViewModel.confirmSelectionPlayer(true)
            }
        }


        binding.btnOkEnemy?.setOnClickListener {
            if (fightViewModel.selectFight.value == R.string.team.toString()) {
                if (!checkCharacterEnemy && !checkTeammate1Enemy && !checkTeammate2Enemy) {
                    if (fightViewModel.enemy.isInitialized) {
                        binding.ivSelectionEnemy?.setImageResource(fightViewModel.enemy.value!!.imagePose)
                        binding.ivSelectionTeammate1Enemy?.visibility = View.VISIBLE
                        binding.ivSelectionTeammate2Enemy?.visibility = View.VISIBLE
                        binding.tvTeammate1EnemyName?.visibility = View.VISIBLE
                        binding.tvTeammate2EnemyName?.visibility = View.VISIBLE
                        checkCharacterEnemy = true
                    }
                } else if (checkCharacterEnemy && !checkTeammate1Enemy && !checkTeammate2Enemy) {
                    binding.ivSelectionTeammate1Enemy?.setImageResource(fightViewModel.teamEnemy.value!!.teammate1.imagePose)
                    checkTeammate1Enemy = true
                } else if (checkCharacterEnemy && checkTeammate1Enemy && !checkTeammate2Enemy) {
                    binding.ivSelectionTeammate2Enemy?.setImageResource(fightViewModel.teamEnemy.value!!.teammate2.imagePose)
                    checkTeammate2Enemy = true

                    it.setBackgroundColor(Color.GREEN)
                    fightViewModel.confirmSelectionEnemy(true)
                    check()
                }
            } else if (fightViewModel.selectFight.value == R.string.single.toString()) {
                if (fightViewModel.enemy.isInitialized) {
                    binding.ivSelectionEnemy?.setImageResource(fightViewModel.enemy.value!!.imagePose)
                    it.setBackgroundColor(Color.GREEN)
                    fightViewModel.confirmSelectionEnemy(true)
                    check()
                }
            }
        }

        binding.btnRandomEnemy?.setOnClickListener {
            if (fightViewModel.selectFight.value == R.string.team.toString()) {
                if (!checkCharacterEnemy && !checkTeammate1Enemy && !checkTeammate2Enemy) {
                    fightViewModel.randomCharacterForEnemy()
                    checkCharacterEnemy = true
                } else if (checkCharacterEnemy && !checkTeammate1Enemy && !checkTeammate2Enemy) {
                    fightViewModel.randomTeamForEnemy()
                    checkTeammate1Enemy = true
                } else if (checkCharacterEnemy && checkTeammate1Enemy && !checkTeammate2Enemy) {
                    fightViewModel.randomTeamForEnemy()
                    checkTeammate2Enemy = true
                    fightViewModel.confirmSelectionEnemy(true)
                }
            } else {
                fightViewModel.randomCharacterForEnemy()
                fightViewModel.confirmSelectionEnemy(true)
            }
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

        // Navigation

        binding.ivBack?.setOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.imageHome.value?.setOnClickListener {
            fightViewModel.stopSound()
            findNavController().navigate(CharacterSelectionFragmentDirections.actionCharacterSelectionFragmentToHomeFragment())
        }

        viewModel.cvImageProfile.value!!.setOnClickListener {
            fightViewModel.stopSound()
            findNavController().navigate(CharacterSelectionFragmentDirections.actionCharacterSelectionFragmentToProfileFragment())
        }

        viewModel.tvUserName.value!!.setOnClickListener {
            fightViewModel.stopSound()
            findNavController().navigate(CharacterSelectionFragmentDirections.actionCharacterSelectionFragmentToProfileFragment())
        }

        viewModel.imageSettings.value!!.setOnClickListener {
            fightViewModel.stopSound()
            findNavController().navigate(CharacterSelectionFragmentDirections.actionCharacterSelectionFragmentToSettingsFragment())
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