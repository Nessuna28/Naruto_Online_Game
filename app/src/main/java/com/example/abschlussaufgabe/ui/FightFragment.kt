package com.example.abschlussaufgabe.ui

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.abschlussaufgabe.R
import com.example.abschlussaufgabe.databinding.FragmentFightBinding


class FightFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var binding: FragmentFightBinding

    private val player = viewModel.player.value!!
    private val enemy = viewModel.enemy.value!!


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

        viewModel.location.observe(viewLifecycleOwner) {
            binding.ivBackgroundFight?.setImageResource(it.image)
        }

        binding.tvNamePlayer?.text = viewModel.profile.value?.userName

        viewModel.player.observe(viewLifecycleOwner) {
            binding.ivCharacterImagePlayer?.setImageResource(it.imageFace)
            binding.ivImage1Player?.setImageResource(it.image)
            binding.ivImage2Player?.setImageResource(it.imageAttack)
            binding.tvCharacterNamePlayer?.text = it.name
            binding.tvLifeValuePlayer?.text = it.lifePoints.toString()
            binding.tvChakraValuePlayer?.text = it.chakraPoints.toString()
            binding.tvDefense1?.text = it.defense[0]
            binding.tvDefense2?.text = it.defense[1]
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
            binding.tvCharacterNameEnemy?.text = it.name
            binding.tvLifeValueEnemy?.text = it.lifePoints.toString()
            binding.tvChakraValueEnemy?.text = it.chakraPoints.toString()
        }

        // Attackenauswahl
        binding.tvDefense1?.setOnClickListener {

                viewModel.setAttackPlayer(player.defense.elementAt(0))
                actionOfSelection()
        }



        binding.tvDefense2?.setOnClickListener {

            viewModel.setAttackPlayer(player.defense.elementAt(1))
            actionOfSelection()
        }

        binding.tvTool1?.setOnClickListener {

            viewModel.setAttackPlayer(player.tools.keys.elementAt(0))
            actionOfSelection()
        }

        binding.tvTool2?.setOnClickListener {

            viewModel.setAttackPlayer(player.tools.keys.elementAt(1))
            actionOfSelection()
        }

        binding.tvUniqueTraits1?.setOnClickListener {

            viewModel.setAttackPlayer(player.uniqueTraits.keys.elementAt(0))
            actionOfSelection()
        }

        binding.tvUniqueTraits2?.setOnClickListener {

            viewModel.setAttackPlayer(player.uniqueTraits.keys.elementAt(1))
            actionOfSelection()
        }

        binding.tvJutsu1?.setOnClickListener {

            viewModel.setAttackPlayer(player.jutsus.keys.elementAt(0))
            actionOfSelection()
        }

        binding.tvJutsu2?.setOnClickListener {

            viewModel.setAttackPlayer(player.jutsus.keys.elementAt(1))
            actionOfSelection()
        }

        binding.tvJutsu3?.setOnClickListener {

            viewModel.setAttackPlayer(player.jutsus.keys.elementAt(2))
            actionOfSelection()
        }

        binding.tvJutsu4?.setOnClickListener {

            viewModel.setAttackPlayer(player.jutsus.keys.elementAt(3))
            actionOfSelection()
        }


        // Navigation
        binding.ivBack?.setOnClickListener {
            findNavController().navigate(FightFragmentDirections.actionFightFragmentToCharacterSelectionFragment())
        }
    }

    fun actionOfSelection() {

        val character = viewModel.player.value

        when(viewModel.attackPlayer.value) {
            player.defense.elementAt(0) -> {
                binding.ivImage1Player?.setImageResource(R.drawable.baumstamm)
                Thread.sleep(4000)
                binding.ivImage1Player?.setImageResource(viewModel.player.value!!.image)
            }
            player.defense.elementAt(1) -> {
                binding.ivImageDouble1Player?.visibility = View.VISIBLE
                binding.ivImageDouble2Player?.visibility = View.VISIBLE
                Thread.sleep(4000)
                binding.ivImageDouble1Player?.visibility = View.INVISIBLE
                binding.ivImageDouble2Player?.visibility = View.INVISIBLE
            }
            player.tools.keys.elementAt(0) -> {
                binding.ivImage1Player?.visibility = View.INVISIBLE
                binding.ivImage2Player?.visibility = View.VISIBLE
                Thread.sleep(4000)
                binding.ivImage1Player?.visibility = View.VISIBLE
                binding.ivImage2Player?.visibility = View.INVISIBLE
            }
            player.tools.keys.elementAt(1) -> {
                binding.ivImage1Player?.visibility = View.INVISIBLE
                binding.ivImage2Player?.visibility = View.VISIBLE
                Thread.sleep(4000)
                binding.ivImage1Player?.visibility = View.VISIBLE
                binding.ivImage2Player?.visibility = View.INVISIBLE
            }
            player.uniqueTraits.keys.elementAt(0) -> {
                binding.ivImage1Player?.visibility = View.INVISIBLE
                binding.ivImage2Player?.visibility = View.VISIBLE
                Thread.sleep(4000)
                binding.ivImage1Player?.visibility = View.VISIBLE
                binding.ivImage2Player?.visibility = View.INVISIBLE
            }
            player.uniqueTraits.keys.elementAt(1) -> {
                binding.ivImage1Player?.visibility = View.INVISIBLE
                binding.ivImage2Player?.visibility = View.VISIBLE
                Thread.sleep(4000)
                binding.ivImage1Player?.visibility = View.VISIBLE
                binding.ivImage2Player?.visibility = View.INVISIBLE
            }
            player.jutsus.keys.elementAt(0) -> {
                binding.ivImage1Player?.visibility = View.INVISIBLE
                binding.ivImage2Player?.visibility = View.VISIBLE
                Thread.sleep(4000)
                binding.ivImage1Player?.visibility = View.VISIBLE
                binding.ivImage2Player?.visibility = View.INVISIBLE
            }
            player.jutsus.keys.elementAt(1) -> {
                binding.ivImage1Player?.visibility = View.INVISIBLE
                binding.ivImage2Player?.visibility = View.VISIBLE
                Thread.sleep(4000)
                binding.ivImage1Player?.visibility = View.VISIBLE
                binding.ivImage2Player?.visibility = View.INVISIBLE
            }
            player.jutsus.keys.elementAt(2) -> {
                binding.ivImage1Player?.visibility = View.INVISIBLE
                binding.ivImage2Player?.visibility = View.VISIBLE
                Thread.sleep(4000)
                binding.ivImage1Player?.visibility = View.VISIBLE
                binding.ivImage2Player?.visibility = View.INVISIBLE
            }
            player.jutsus.keys.elementAt(3) -> {
                binding.ivImage1Player?.visibility = View.INVISIBLE
                binding.ivImage2Player?.visibility = View.VISIBLE
                Thread.sleep(4000)
                binding.ivImage1Player?.visibility = View.VISIBLE
                binding.ivImage2Player?.visibility = View.INVISIBLE
            }
        }
    }
}