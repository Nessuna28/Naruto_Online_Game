package com.example.abschlussaufgabe.ui

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.abschlussaufgabe.R
import com.example.abschlussaufgabe.databinding.FragmentFightBinding


class FightFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var binding: FragmentFightBinding


    override fun onStart() {
        super.onStart()

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

        viewModel.imageBackground.value?.let { viewModel.hideImages(it) }
        viewModel.materialCard.value?.let { viewModel.hideMaterialCard(it) }

        // TODO: Background setzen

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

        viewModel.player.observe(viewLifecycleOwner) {
            binding.ivCharacterImagePlayer?.setImageResource(it.image)
            binding.ivImage1Player?.setImageResource(it.image)
            binding.ivImage2Player?.setImageResource(it.image2)
            binding.tvCharacterNamePlayer?.text = it.name
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
            binding.ivCharacterImageEnemy?.setImageResource(it.image)
            binding.ivImage1Enemy?.setImageResource(it.image)
            binding.ivImage2Enemy?.setImageResource(it.image2)
            binding.tvCharacterNameEnemy?.text = it.name
        }
    }
}